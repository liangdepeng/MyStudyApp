package com.example.weightapp;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * 小组件
 * Author: liangdp
 */
public class AppWidgetUtils {

    private static String TAG = "AppWidgetUtils";
    public static final String JUMP_CLASS1 = "com.example.weightapp.JumpTempActivity";
    public static final String JUMP_CLASS2 = "com.example.weightapp.JumpTempActivity2";


    // 判断是否支持请求添加小组件 ,并不代表用户自己不可以手动添加，仅仅判断android 8.0之后是否支持授权添加
    public static boolean isRequestPinAppWidgetSupported(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                AppWidgetManager appWidgetManager = context.getSystemService(AppWidgetManager.class);
                // 检查系统是否支持请求添加 widget
                boolean supported = appWidgetManager.isRequestPinAppWidgetSupported();
                Log.e(TAG, "isRequestPinAppWidgetSupported: " + supported);
                return supported;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 请求添加小组件
    public static void requestPinAppWidget(Context context, ComponentName componentName, Bundle extras, PendingIntent callback) {
        if (!isRequestPinAppWidgetSupported(context))
            return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppWidgetManager appWidgetManager = ContextCompat.getSystemService(context, AppWidgetManager.class);
                appWidgetManager.requestPinAppWidget(componentName, extras, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 通知更新
    public static void notifyDataUpdate(Context context, Class<?> widgetClass) {
        try {
            // Class javaClass = Class.forName("xxx.WidgetDemoProvider");
            final Intent intent = new Intent(context, widgetClass);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, widgetClass));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 判断小组件是否已安装
    public static boolean isWidgetInstalled(Context context, Class<?> widgetClass) {
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, widgetClass));
            return appWidgetIds != null && appWidgetIds.length > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // 不适用小组件
    public static boolean isSupportDevices() {
        if ("vivo".equalsIgnoreCase(Build.MANUFACTURER)) {
            return false;
        }
        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER) && ("MI 6".equalsIgnoreCase(Build.MODEL)
                || "MI6".equalsIgnoreCase(Build.MODEL) || "MI 5".equalsIgnoreCase(Build.MODEL)
                || "MI5".equalsIgnoreCase(Build.MODEL) || "MI 4".equalsIgnoreCase(Build.MODEL)
                || "MI4".equalsIgnoreCase(Build.MODEL) || "MI 3".equalsIgnoreCase(Build.MODEL)
                || "MI3".equalsIgnoreCase(Build.MODEL) || "MI 2".equalsIgnoreCase(Build.MODEL)
                || "MI2".equalsIgnoreCase(Build.MODEL) || "MI 1".equalsIgnoreCase(Build.MODEL)
                || "MI1".equalsIgnoreCase(Build.MODEL))) {
            return false;
        }
        return true;
    }

//    public static void showTipDialog(Activity activity, String title, final String content, Runnable okRunnable) {
//        HallCustomDialog dialog = new HallCustomDialog.Builder(activity)
//                .setGravity(Gravity.CENTER)
//                .setDialogWidth(PxUtils.dip2px(288f))
//                .setDimAmount(0.5f)
//                .setBackCancelable(false)
//                .setOutSideCanCancel(false)
//                .setLayoutResId(R.layout.dialog_global_warning_layout)
//                .setViewBindCallback(new HallCustomDialog.IDialogViewBindCallback() {
//                    @Override
//                    public void onBindView(View rootView, HallCustomDialog dialog) {
//                        DialogGlobalWarningLayoutBinding layoutBinding = DialogGlobalWarningLayoutBinding.bind(rootView);
//                        layoutBinding.titleTv.setText(title);
//                        layoutBinding.contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
//                        layoutBinding.contentTv.setText(content);
//                        layoutBinding.okTv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (okRunnable != null) {
//                                    okRunnable.run();
//                                }
//                                dialog.dismiss();
//                            }
//                        });
//                        layoutBinding.closeIv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                DialogUtil.dialogDismiss(DialogBean.DialogType.TCY_WARNING_WARN);
//                            }
//                        });
//                    }
//                }).build();
//        dialog.show();
//    }


    public static String getMiUiSystemVer() {
        String line = "";
        BufferedReader input = null;
        try {
            try {
                Process p = Runtime.getRuntime().exec("getprop " + "ro.miui.ui.version.name");
                input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
                line = input.readLine();
                input.close();
            } catch (IOException ex) {
                return null;
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    public static boolean isCanCreateShortcut() {
        // 判断是否小米手机
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            return isXiaomiCanCreateShortcut();
        } else if (Build.MANUFACTURER.equalsIgnoreCase("vivo")) {
            return isVivoCreateShortcut();
        } else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            return isOppoCanCreateShortcut();
        }
        return false;
    }

    public static boolean isXiaomiCanCreateShortcut() {// 创建桌面快捷方式
        Context context = MyApp.getAppContext();
        // 判断小米手机是否有桌面图标权限
        AppOpsManager mAppOps = (AppOpsManager) MyApp.getAppContext().getSystemService(Context.APP_OPS_SERVICE);
        String pkgName = context.getApplicationContext().getPackageName();
        int uid = context.getApplicationInfo().uid;
        try {
            Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getDeclaredMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            int result = (int) checkOpNoThrowMethod.invoke(mAppOps, 10017, uid, pkgName);
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isVivoCreateShortcut() {
        Context context = MyApp.getAppContext();
        // 判断vivo手机是否有桌面图标权限
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return false;
        }
        Cursor query = null;
        try {
            Uri uri = Uri.parse("content://com.bbk.launcher2.settings/favorites");
            query = contentResolver.query(uri, null, null, null, null);
            if (query == null) {
                return false;
            }
            String pkg = context.getApplicationContext().getPackageName();
            while (query.moveToNext()) {
                String value = query.getString(query.getColumnIndex("value"));
                if (!TextUtils.isEmpty(value) && value.contains(pkg + ", 1")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (query != null)
                query.close();
        }
    }


    public static boolean isOppoCanCreateShortcut() {
        Context context = MyApp.getAppContext();
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return false;
        }
        Cursor query = null;
        try {
            Uri uri = Uri.parse("content://settings/secure/launcher_shortcut_permission_settings");
            query = contentResolver.query(uri, null, null, null, null);
            if (query == null) {
                return false;
            }
            String pkg = context.getApplicationContext().getPackageName();
            while (query.moveToNext()) {
                String value = query.getString(query.getColumnIndex("value"));
                if (!TextUtils.isEmpty(value) && value.contains(pkg + ", 1")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // return isRequestPinShortcutSupported(context);
             return false;
        } finally {
            if (query != null)
                query.close();
        }
    }

    public static void commonApplyPermission() {
        try {
            Context context = MyApp.getAppContext();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void toXiaomiApplyPermission() {
        try {
            Context context = MyApp.getAppContext();
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.putExtra("extra_pkgname", context.getPackageName());
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "xiaomi 跳转失败 toXiaomiApplyPermission: " + e.getMessage());
            commonApplyPermission();
        }
    }


    public static void toHuaweiApplyPermission() {
        Context context = MyApp.getAppContext();
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", context.getApplicationInfo().packageName);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toVivoApplyPermission() {
        Context context = MyApp.getAppContext();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packagename", context.getPackageName());
        intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
        if (!findMatchIntent(context, intent)) {
            intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toOppoApplyPermission() {
        Context context = MyApp.getAppContext();
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", context.getPackageName());
            intent.setClassName("com.oppo.launcher", "com.oppo.launcher.shortcut.ShortcutSettingsActivity");
            if (!findMatchIntent(context, intent)) {
                intent.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean findMatchIntent(Context context, Intent intent) {//匹配
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}

//    public static boolean isRequestPinShortcutSupported(@NonNull Context context) {
////        if (Build.VERSION.SDK_INT >= 26) {
////            return context.getSystemService(ShortcutManager.class).isRequestPinShortcutSupported();
////        }
//
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INSTALL_SHORTCUT)
//                != PackageManager.PERMISSION_GRANTED) {
//            return false;
//        }
////        for (ResolveInfo info : context.getPackageManager().queryBroadcastReceivers(
////                new Intent(Intent.ACTION_CREATE_SHORTCUT), 0)) {
////            String permission = info.activityInfo.permission;
////            if (TextUtils.isEmpty(permission) || Manifest.permission.INSTALL_SHORTCUT.equals(permission)) {
////                return true;
////            }
////        }
//        return true;
//    }




// xiaomi
//private static final Intent o(Context context) {
//    String h10 = h("ro.miui.ui.version.name");
//    if (h10 == null || h10.length() == 0) {
//        return f(context);
//    }
//    try {
//        i nt parseInt = Integer.parseInt(h10.substring(1));
//        if (parseInt >= 9) {
//            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity"));
//            intent.putExtra("extra_pkgname", context.getPackageName());
//            return intent;
//        } else if (parseInt >= 7) {
//            Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
//            intent2.putExtra("extra_pkgname", context.getPackageName());
//            return intent2;
//        } else {
//            return f(context);
//        }
//    } catch (NumberFormatException e10) {
//        e10.printStackTrace();
//        return f(context);
//    }
//}

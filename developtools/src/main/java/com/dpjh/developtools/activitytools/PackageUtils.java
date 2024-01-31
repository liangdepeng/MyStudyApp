package com.dpjh.developtools.activitytools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.dpjh.developtools.R;

public class PackageUtils {

    public static void testGetAppInfoByPackageName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        try {
            // 获取应用信息
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String appName = (String) packageManager.getApplicationLabel(applicationInfo);
            String versionName = getVersionName(context, packageName);
            int versionCode = getVersionCode(context, packageName);

            // 打印应用信息
            Log.d("AppInfoUtils", "App Name: " + appName);
            Log.d("AppInfoUtils", "Package Name: " + packageName);
            Log.d("AppInfoUtils", "Version Name: " + versionName);
            Log.d("AppInfoUtils", "Version Code: " + versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("AppInfoUtils", "Package not found: " + packageName);
        }
    }

    public static String getAppName(Context context,String packageName){
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getVersionName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static int getVersionCode(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                return (int) packageInfo.getLongVersionCode();
//            } else {
                return packageInfo.versionCode;
//            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Drawable getAppIcon(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            return packageManager.getApplicationIcon(applicationInfo);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return ContextCompat.getDrawable(context,R.mipmap.ic_launcher);
        }
    }
}

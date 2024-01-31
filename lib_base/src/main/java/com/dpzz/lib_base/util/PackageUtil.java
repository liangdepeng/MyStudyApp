package com.dpzz.lib_base.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class PackageUtil {


    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    public static String getVersionName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 安装apk
     *
     * @param path
     */
    public static void installApk(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion >= 23 && Build.VERSION.SDK_INT > 23) {
                intent.setDataAndType(FileProvider.getUriForFile(
                        context, "com.dpzz.mine.fileprovider",
                        new File(path)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                intent.setDataAndType(Uri.parse("file://" + path),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 卸载apk
     */
    public static void uninstallApk(Context context, String packagename) {
        Uri uninstall = Uri.parse("package:" + packagename);
        Intent intent = new Intent(Intent.ACTION_DELETE, uninstall);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

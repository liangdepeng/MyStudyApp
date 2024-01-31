package com.dpzz.weatherpart;

import android.app.Application;
import android.content.Context;

import com.dpzz.lib_base.util.SPUtil;
import com.qweather.sdk.view.HeConfig;
import com.tencent.bugly.crashreport.CrashReport;

public class WeatherApp extends Application {

    private static Context applicationContext;
    public static boolean isCanInitSdk = false;
    private static boolean isInitSdkFinished = false;
    private static boolean isInitingSdk = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        SPUtil.init(applicationContext);
        isCanInitSdk = SPUtil.getBoolean(SPUtil.KEY_IS_SHOW_PRIVACY_DIALOG,false);
        initSdk();
    }

    public static void initSdk() {
        if (isCanInitSdk && !isInitingSdk && !isInitSdkFinished) {
            isInitingSdk = true;
            initBugly();
            initHef();
            isInitingSdk = false;
            isInitSdkFinished = true;
        }
    }

    private static void initBugly() {
        CrashReport.initCrashReport(applicationContext, "6749d5f063", true);
    }

    private static void initHef() {
        HeConfig.init("HE2303241602361021", "c994e4e0208d4ee99d09b99297db9a2a");
        HeConfig.switchToDevService();
    }

}

package com.example.weightapp;

import android.app.Application;
import android.content.Context;

/**
 * Date: 2025/4/23 11:29
 * Author: liangdp
 */
public class MyApp extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}

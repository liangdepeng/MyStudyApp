package com.dpjh.developtools;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}

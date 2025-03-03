package com.example.weightapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class WidgetManager {
    public static void requestPinAppWidget(Context context,Class<?> componentClazz) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetProvider = new ComponentName(context, componentClazz);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                Intent intent = new Intent();
                intent.setAction("com.cn.appwidget.action.APPWIDGET_UPDATE");
                PendingIntent successCallback = PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                appWidgetManager.requestPinAppWidget(widgetProvider, null, successCallback);
            }
        }
    }
}

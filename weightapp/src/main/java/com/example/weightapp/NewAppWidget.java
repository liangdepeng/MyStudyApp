package com.example.weightapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

      //  CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "4x4");
        views.setTextViewText(R.id.time, getshowTime());

        SharedPreferences preferences = context.getSharedPreferences("widget_config", Context.MODE_PRIVATE);
        String jumpClass = preferences.getString("widget_jump_class", "");
        if (TextUtils.isEmpty(jumpClass)){
            jumpClass = AppWidgetUtils.JUMP_CLASS1;
            preferences.edit().putString("widget_jump_class", jumpClass).apply();
        }

        // code 每次要不一样
        int code = (int) (Math.random() * 10000);

        Intent intent = new Intent();

        // 针对点击小组件需要启动app并且跳转指定页面的情况，需要跳板页面，在该页面判断需要跳转启动页还是指定页面
        // 如果在跳板Activity 中转启动app之后，出现小组件点击事件失效的情况，从widget启动后更换跳板activity即可，准备两个一样的页面
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jumpClass);
        } catch (Exception e) {
            clazz = JumpTempActivity.class;
        }
        intent.setClass(context, clazz);
        intent.putExtra("code", code);
        intent.putExtra("widget22", "NewAppWidget");
        intent.putExtra("jump_class", jumpClass);

        Log.e("widgetcus"," clazz jump class -> "+jumpClass );

        views.setOnClickPendingIntent(R.id.appwidget_text, PendingIntent.getActivity(context, code, intent, PendingIntent.FLAG_CANCEL_CURRENT));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static CharSequence getshowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // 输入创建第一个 Widget 时的相关功能

        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = 10*1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

    }

    @Override
    public void onDisabled(Context context) {
        // 输入禁用最后一个小组件时的相关功能

        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
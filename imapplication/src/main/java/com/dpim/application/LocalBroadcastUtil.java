package com.dpim.application;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpim.application.base.BaseApp;

public class LocalBroadcastUtil {

    private final static LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaseApp.appCtx);

    public static void sendBroadcast(Intent intent) {
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void registerReceiver(BroadcastReceiver receiver, IntentFilter filter){
        localBroadcastManager.registerReceiver(receiver, filter);
    }

    public static void unregisterReceiver(BroadcastReceiver receiver){
        localBroadcastManager.unregisterReceiver(receiver);
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }

    public static String ACTION_LOGIN_SUCCESS = "ACTION_LOGIN_SUCCESS";
    public static String ACTION_LOGIN_ERROR = "ACTION_LOGIN_ERROR";
}

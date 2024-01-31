package com.dpzz.lib_base.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    public static String KEY_SDK_CAN_INIT ="KEY_SDK_CAN_INIT";
    public static String KEY_IS_SHOW_PRIVACY_DIALOG ="KEY_IS_SHOW_PRIVACY_DIALOG";

    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName() + "_sp", Context.MODE_PRIVATE);
    }

    public static void put(String key, Object value) {
        checkSdkInit();
        if (value instanceof Integer) {
            sharedPreferences.edit().putInt(key, ((int) value)).apply();
        } else if (value instanceof Float) {
            sharedPreferences.edit().putFloat(key, ((float) value)).apply();
        } else if (value instanceof Long) {
            sharedPreferences.edit().putLong(key, ((long) value)).apply();
        } else if (value instanceof Boolean) {
            sharedPreferences.edit().putBoolean(key, ((boolean) value)).apply();
        } else if (value instanceof String) {
            sharedPreferences.edit().putString(key, ((String) value)).apply();
        } else {
            sharedPreferences.edit().putString(key, value == null ? null : value.toString()).apply();
        }
    }

    public static int getInt(String key, int defaultValue) {
        checkSdkInit();
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        checkSdkInit();
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        checkSdkInit();
        return sharedPreferences.getString(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        checkSdkInit();
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        checkSdkInit();
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    private static void checkSdkInit(){
        if (sharedPreferences == null)
            throw new RuntimeException("sharedPreferences not init");
    }
}

package com.dpim.application.base;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    private static SharedPreferences sp;

    public static void put(String key, String value) {
        getSp().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getSp().getString(key, "");
    }

    private synchronized static SharedPreferences getSp() {
        if (sp == null)
            sp = BaseApp.appCtx.getSharedPreferences(BaseApp.appCtx.getPackageName(), Context.MODE_PRIVATE);
        return sp;
    }

    public static class Keys {
        public static String currentUserId = "currentUserId";
    }
}

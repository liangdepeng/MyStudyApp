package com.dpzz.lib_base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    public static void show(String msg) {
        if (context == null)
            throw new RuntimeException("ToastUtil context is null or not init");
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

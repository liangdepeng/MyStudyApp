package com.dpzz.lib_base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void init(Context mContext) {
        context = mContext;
    }

    public static void show(String msg) {
        if (context == null)
            throw new RuntimeException("ToastUtil context is null or not init");
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            handler.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
        }
    }
}

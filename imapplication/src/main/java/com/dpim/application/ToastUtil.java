package com.dpim.application;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dpim.application.base.BaseApp;

public class ToastUtil {

    private final static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(String msg) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(msg);
        } else {
            handler.post(() -> showToast(msg));
        }
    }

    private static void showToast(String msg) {
        Toast.makeText(BaseApp.appCtx, msg, Toast.LENGTH_SHORT).show();
    }
}

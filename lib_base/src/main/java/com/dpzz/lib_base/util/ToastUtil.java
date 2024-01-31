package com.dpzz.lib_base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static final Handler handler = new Handler(Looper.getMainLooper());

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

    public static void show2(String msg) {
        if (context == null)
            throw new RuntimeException("ToastUtil context is null or not init");
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            getToast(msg).show();
        } else {
            handler.post(() -> getToast(msg).show());
        }
    }

    private static Toast getToast(String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,200);
        FrameLayout parent = new FrameLayout(context);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#66000000"));
        drawable.setCornerRadius(PxUtils.dip2px(context,10f));
        parent.setBackground(drawable);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText(msg);
        textView.setTextSize(14f);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(20,10,20,10);
        parent.addView(textView);
        toast.setView(parent);
        return toast;
    }
}

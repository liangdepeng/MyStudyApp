package com.dpjh.developtools.activitytools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpjh.developtools.BaseApp;
import com.dpjh.developtools.R;

public class FloatWindowManager {

    private final Context appContext;
    public static final int OVERLAY_PERMISSION_REQUEST_CODE = 23332;
    private WindowManager windowManager;
    private ImageView appIconIv;
    private TextView appNameTv;
    private TextView pkgNameTv;
    private TextView activityNameTv;
    private View contentView;
    private WindowManager.LayoutParams params;
    private boolean isAdd = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    private FloatWindowManager(Context applicationContext) {
        this.appContext = applicationContext;
    }

    private static final class Instance {
        private static final FloatWindowManager manager = new FloatWindowManager(BaseApp.applicationContext);
    }

    public static FloatWindowManager getInstance() {
        return Instance.manager;
    }

    public boolean isHasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(appContext);
        } else {
            return true;
        }
    }

    public void requestPermission(Activity context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + appContext.getPackageName()));
            context.startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean isNotMainThread() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    public boolean isShow(){
        return isAdd;
    }

    public void showWindow() {

        if (isNotMainThread()) {
            handler.post(this::showWindow);
            return;
        }

        if (isAdd) {
            return;
        }

        if (windowManager != null && contentView != null && params != null) {
            windowManager.addView(contentView, params);
            isAdd = true;
            return;
        }

        windowManager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        // 创建悬浮窗布局参数
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 20;
        params.y = 100;

        contentView = LayoutInflater.from(appContext).inflate(R.layout.current_activity_window_float_layout, null);
        appIconIv = contentView.findViewById(R.id.icon);
        appNameTv = contentView.findViewById(R.id.app_name_tv);
        pkgNameTv = contentView.findViewById(R.id.pkg_name_tv);
        activityNameTv = contentView.findViewById(R.id.activity_name_tv);

        appIconIv.setImageResource(R.mipmap.ic_launcher);
        appNameTv.setText(appContext.getString(R.string.app_name));
        pkgNameTv.setText(appContext.getPackageName());
        activityNameTv.setText("com.dpjh.developtools.activitytools.ActivityToolsPage");

        windowManager.addView(contentView, params);
        isAdd = true;

        contentView.setOnTouchListener(new View.OnTouchListener() {

            private int initX;
            private int initY;
            private float touchX;
            private float touchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = params.x;
                        initY = params.y;
                        touchX = event.getRawX();
                        touchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = (int) (event.getRawX() - touchX);
                        int deltaY = (int) (event.getRawY() - touchY);
                        params.x = initX + deltaX;
                        params.y = initY + deltaY;
                        windowManager.updateViewLayout(contentView, params);
                        return true;
                }
                return false;
            }
        });
    }

    public void updateCurrentData(String appName, String pkgName, String activityName, Drawable appIcon) {
        if (isNotMainThread()) {
            handler.post(() -> updateCurrentData(appName, pkgName, activityName, appIcon));
            return;
        }
        if (isAdd) {
            appIconIv.setImageDrawable(appIcon);
            appNameTv.setText(appName);
            pkgNameTv.setText(pkgName);
            activityNameTv.setText(activityName);
        }
    }


    public void hideWindow() {
        if (isNotMainThread()) {
            handler.post(this::hideWindow);
            return;
        }
        try {
            if (!isAdd)
                return;
            windowManager.removeView(contentView);
            isAdd = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (isNotMainThread()) {
            handler.post(this::release);
            return;
        }
        try {
            hideWindow();
            contentView = null;
            windowManager = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

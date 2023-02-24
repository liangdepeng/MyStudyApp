package com.dpzz.lib_base.base;

import android.app.Application;

import com.dpzz.lib_base.util.ToastUtil;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
    }
}

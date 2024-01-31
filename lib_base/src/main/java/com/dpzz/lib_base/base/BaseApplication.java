package com.dpzz.lib_base.base;

import android.app.Application;

import com.dpzz.lib_base.GlobalContext;
import com.dpzz.lib_base.util.SPUtil;
import com.dpzz.lib_base.util.ToastUtil;
import com.hjq.toast.Toaster;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toaster.init(this);
        ToastUtil.init(this);
        GlobalContext.mContext = this;
        SPUtil.init(this);
    }
}

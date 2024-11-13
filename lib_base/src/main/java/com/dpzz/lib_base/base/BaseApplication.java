package com.dpzz.lib_base.base;

import android.app.Application;

import com.dpzz.lib_base.GlobalContext;
import com.dpzz.lib_base.util.SPUtil;
import com.dpzz.lib_base.util.ToastUtil;
import com.hjq.toast.Toaster;
import com.tencent.bugly.crashreport.CrashReport;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext.mContext = this;
        Toaster.init(this);
        ToastUtil.init(this);
        SPUtil.init(this);
    }

    private static void initBugly() {
       // CrashReport.initCrashReport(GlobalContext.mContext, "6749d5f063", true);
    }
}

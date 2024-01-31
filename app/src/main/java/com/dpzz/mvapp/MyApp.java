package com.dpzz.mvapp;


import com.crash.crash_lib.CrashHandleUtil;
import com.dpzz.lib_base.base.BaseApplication;

public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandleUtil.init(this,CrashHandleUtil.CrashShowStyle.STYLE_DIALOG_IF_CAN_SHOW);
    }

}

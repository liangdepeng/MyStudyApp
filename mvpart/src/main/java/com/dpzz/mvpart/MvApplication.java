package com.dpzz.mvpart;

import com.crash.crash_lib.CrashHaldleApplication;

public class MvApplication extends CrashHaldleApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setTipShowStyle(CrashShowStyle.STYLE_DIALOG_IF_CAN_SHOW);
    }
}

package com.dpzz.lib_base.util;

import com.dpzz.lib_base.GlobalContext;

public class ScreenUtil {

    public static int getWidth() {
        checkContextIsNull();
        return GlobalContext.mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        checkContextIsNull();
        return GlobalContext.mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static void checkContextIsNull(){
        if (GlobalContext.mContext ==null)
            throw new RuntimeException("GlobalContext.mContext  NOT INIT");
    }
}

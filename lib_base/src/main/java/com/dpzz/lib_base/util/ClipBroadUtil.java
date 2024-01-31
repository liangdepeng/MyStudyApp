package com.dpzz.lib_base.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.dpzz.lib_base.GlobalContext;

public class ClipBroadUtil {

    public static void copyTxt(CharSequence text){
        ClipboardManager clipboardManager = (ClipboardManager) GlobalContext.mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("JIHmj", text);
        clipboardManager.setPrimaryClip(clipData);
    }
}

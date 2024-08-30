package com.dpzz.weatherpart;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * xxxx
 * Date: 2024/8/12 15:32
 * Author: liangdp
 */
public class KeyBoardUtil {

    /**
     * 缩掉输入法
     */
    public static void dismissInputMethod(EditText editText) {
        if (editText == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) editText
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /**
     * 弹出输入法
     */
    public static void showInputMethod(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}

package com.dpzz.mvpart;

import android.content.Context;
import android.content.Intent;

import com.dpzz.lib_base.jump.MvPartApi;

public class MvPartApiImpl implements MvPartApi {

    @Override
    public void jumpToMvHomePage(Context context) {
        Intent intent = new Intent(context, MvMainActivity.class);
        context.startActivity(intent);
    }
}

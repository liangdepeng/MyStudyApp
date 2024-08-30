package com.dpim.application.base;

import android.app.Application;
import android.content.Context;

import com.dpim.application.im.HomeActivity;

import io.rong.imkit.IMCenter;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.InitOption;

public class BaseApp extends Application {

    public static Context appCtx;

    @Override
    public void onCreate() {
        super.onCreate();
        appCtx = getApplicationContext();
        initSdk();
    }

    private void initSdk() {
        String appKey = "8luwapkv8huwl";
        InitOption option = new InitOption.Builder().enablePush(true).build();
        IMCenter.init(this,appKey,option);

        RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationListActivity, HomeActivity.class);
    }

}

package com.dpim.application.im;

import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpim.application.CurrentUserManager;
import com.dpim.application.LocalBroadcastUtil;
import com.dpim.application.base.BaseActivity;
import com.dpim.application.base.BaseApp;
import com.dpim.application.base.SPUtils;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;

public enum RYManager {

    INSTANCE;

    public void connectToServer(String userId) {
        String token = SPUtils.getString(userId);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String t) {
                CurrentUserManager.getInstance().setUserId(userId);
                CurrentUserManager.getInstance().setToken(token);
                RouteUtils.routeToConversationListActivity(BaseActivity.getTopActivity(), "123");

                LocalBroadcastUtil.sendBroadcast(new Intent(LocalBroadcastUtil.ACTION_LOGIN_SUCCESS));
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode e) {
                Toast.makeText(BaseApp.appCtx, e.name(), Toast.LENGTH_SHORT).show();

                LocalBroadcastUtil.sendBroadcast(new Intent(LocalBroadcastUtil.ACTION_LOGIN_ERROR));
            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {

            }
        });
    }

    public void disconnect(){
        RongIM.getInstance().disconnect();
    }

}

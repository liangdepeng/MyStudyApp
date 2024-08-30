package com.dpim.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.dpim.application.base.BaseActivity;
import com.dpim.application.base.SPUtils;
import com.dpim.application.databinding.ActivityLoginBinding;
import com.dpim.application.http.HttpCallback;
import com.dpim.application.http.HttpHelper;
import com.dpim.application.im.RYManager;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    public static int CODE_REGISTER_REQUEST = 2332;
    public static int CODE_REGISTER_RESULT = 2333;

    private final BroadcastReceiver loginSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dismissLoading();
            finish();
        }
    };

    private final BroadcastReceiver loginErrorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dismissLoading();
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastUtil.unregisterReceiver(loginSuccessReceiver);
        LocalBroadcastUtil.unregisterReceiver(loginErrorReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LocalBroadcastUtil.registerReceiver(loginSuccessReceiver, new IntentFilter(LocalBroadcastUtil.ACTION_LOGIN_SUCCESS));

        LocalBroadcastUtil.registerReceiver(loginErrorReceiver, new IntentFilter(LocalBroadcastUtil.ACTION_LOGIN_ERROR));

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), CODE_REGISTER_REQUEST);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = binding.idEt.getText().toString();
                if (TextUtils.isEmpty(userId)) {
                    ToastUtil.show("userId is null");
                } else {
                    showLoading("登录中...");
                    String tk = SPUtils.getString(userId);
                    if (TextUtils.isEmpty(tk)){
                        HttpHelper.getInstance().requestQueryInfo(userId, new HttpCallback() {
                            @Override
                            public void onSuccess(Object response, String msg) {
                                JSONObject jsonObject = (JSONObject) response;
                                String userName = jsonObject.optString("userName");
                                String userPortrait = jsonObject.optString("userPortrait");
                                UserInfoBean bean = new UserInfoBean();
                                bean.setUserName(userName);
                                bean.setUserId(userId);
                                bean.setUserLogoUrl(userPortrait);
                                getTokenAndLogin(bean);
                            }

                            @Override
                            public void onFailed(String msg) {
                                dismissLoading();
                                ToastUtil.show(msg);
                            }
                        });
                        return;
                    }
                    RYManager.INSTANCE.connectToServer(userId);
                }
            }
        });

        String lastUserId = SPUtils.getString(SPUtils.Keys.currentUserId);
        if (!TextUtils.isEmpty(lastUserId)) {
            String token = SPUtils.getString(lastUserId);
            if (!TextUtils.isEmpty(token)) {
                showLoading("登录中...");
                RYManager.INSTANCE.connectToServer(lastUserId);
            }
        }
    }

    private void getTokenAndLogin(UserInfoBean bean) {
        HttpHelper.getInstance().requestRegister(bean, new HttpCallback() {
            @Override
            public void onSuccess(Object response, String msg) {
                dismissLoading();
                RYManager.INSTANCE.connectToServer(bean.getUserId());
            }

            @Override
            public void onFailed(String msg) {
                dismissLoading();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REGISTER_REQUEST) {
            if (data != null) {
                String userId = data.getStringExtra("userId");
                binding.idEt.setText(userId);
                ToastUtil.show("请使用 " + userId + " 继续登录");
            }
        }
    }
}

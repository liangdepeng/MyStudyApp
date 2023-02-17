package com.dpzz.mvpart;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dpzz.lib_base.BaseActivity;
import com.dpzz.lib_base.CountdownUtil;
import com.dpzz.lib_base.ToastUtil;
import com.dpzz.mvpart.databinding.ActivityMvSplashBinding;

public class MvLauncherActivity extends BaseActivity<ActivityMvSplashBinding> {

    private final String countdownTag = "splash_countdown";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtil.init(getApplicationContext());
    }

    @Override
    public void initData() {
        CountdownUtil.start(95, new CountdownUtil.CallbackAdapter() {
            @Override
            public void onValueUpdate(int value) {
                mViewBinding.helloTxt.setText(value + " 秒后进入主页");
            }

            @Override
            public void onEnd(Animator animation) {
                startActivity(new Intent(MvLauncherActivity.this, MvMainActivity.class));
                finish();
            }
        }, countdownTag);
    }

    @Override
    public void initView() {
        mViewBinding.jumpBtn.setOnClickListener(v -> {
            CountdownUtil.cancelIfCountdownIsRunning(countdownTag);
            startActivity(new Intent(MvLauncherActivity.this, MvMainActivity.class));
            finish();
        });

    }
}

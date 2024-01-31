package com.dpzz.mvpart;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dpzz.lib_base.base.BaseActivity;
import com.dpzz.lib_base.util.CountdownUtil;
import com.dpzz.lib_base.util.ToastUtil;
import com.dpzz.mvpart.databinding.ActivityMvSplashBinding;

public class MvLauncherActivity extends BaseActivity<ActivityMvSplashBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtil.init(getApplicationContext());
    }

    @Override
    public void initData() {
        CountdownUtil.start(88, new CountdownUtil.CallbackAdapter() {
            @Override
            public void onValueUpdate(int value) {
                mViewBinding.helloTxt.setText(String.format("%d 秒后进入主页", value));
            }

            @Override
            public void onEnd(Animator animation) {
                startActivity(new Intent(MvLauncherActivity.this, MvMainActivity.class));
                finish();
            }
        }, CountdownUtil.countdownTag1);

      //  AppUpdateManager.Companion.checkAppUpdate(this);
    }


    @Override
    public void initView() {
        mViewBinding.jumpBtn.setOnClickListener(v -> {
            CountdownUtil.cancelIfCountdownIsRunning(CountdownUtil.countdownTag1);
            startActivity(new Intent(MvLauncherActivity.this, MvMainActivity.class));
            finish();
        });
    }
}

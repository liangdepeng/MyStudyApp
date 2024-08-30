package com.dpim.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.dpim.application.base.BaseActivity;
import com.dpim.application.databinding.ActivityRegisterBinding;
import com.dpim.application.http.HttpCallback;
import com.dpim.application.http.HttpHelper;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = binding.idEt.getText().toString();
                String nameStr = binding.nameEt.getText().toString();
                if (TextUtils.isEmpty(idStr)) {
                    ToastUtil.show("id not null");
                    return;
                }
                if (TextUtils.isEmpty(nameStr)) {
                    ToastUtil.show("name not null");
                    return;
                }

                showLoading("...");
                UserInfoBean bean = new UserInfoBean();
                bean.setUserId(idStr);
                bean.setUserName(nameStr);
                HttpHelper.getInstance().requestRegister(bean, new HttpCallback() {
                    @Override
                    public void onSuccess(Object response, String msg) {
                        dismissLoading();
                        Intent intent = new Intent();
                        intent.putExtra("userId",idStr);
                        setResult(LoginActivity.CODE_REGISTER_RESULT,intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg) {
                        dismissLoading();
                    }
                });
            }
        });
    }
}

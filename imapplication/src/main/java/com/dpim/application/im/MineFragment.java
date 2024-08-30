package com.dpim.application.im;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dpim.application.CurrentUserManager;
import com.dpim.application.LoginActivity;
import com.dpim.application.ToastUtil;
import com.dpim.application.UserInfoBean;
import com.dpim.application.base.BaseActivity;
import com.dpim.application.base.BaseFragment;
import com.dpim.application.databinding.FragmentMineBinding;
import com.dpim.application.http.HttpCallback;
import com.dpim.application.http.HttpHelper;

import org.json.JSONObject;

public class MineFragment extends BaseFragment {

    private FragmentMineBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RYManager.INSTANCE.disconnect();
                CurrentUserManager.getInstance().clear();
                FriendManager.getInstance().clear();
                startActivity(new Intent(mContext, LoginActivity.class));
                ((BaseActivity) mContext).finish();
            }
        });

        binding.nameTv.setText(CurrentUserManager.getInstance().getUserName());
        binding.idTv.setText(CurrentUserManager.getInstance().getUserId());

        queryUserInfo();
    }

    private void queryUserInfo() {
        HttpHelper.getInstance().requestQueryInfo(CurrentUserManager.getInstance().getUserId(), new HttpCallback() {
            @Override
            public void onSuccess(Object response, String msg) {
                JSONObject jsonObject = (JSONObject) response;
                String userName = jsonObject.optString("userName");
                String userPortrait = jsonObject.optString("userPortrait");
                CurrentUserManager.getInstance().setUserName(userName);
                CurrentUserManager.getInstance().setUserLogoUrl(userPortrait);
                binding.nameTv.setText(CurrentUserManager.getInstance().getUserName());
                binding.idTv.setText(CurrentUserManager.getInstance().getUserId());
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }
}

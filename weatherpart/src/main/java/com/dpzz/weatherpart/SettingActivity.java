package com.dpzz.weatherpart;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.dpzz.lib_base.base.BaseActivity;
import com.dpzz.lib_base.util.SPUtil;
import com.dpzz.lib_base.util.ToastUtil;
import com.dpzz.weatherpart.databinding.ActivityWeaSettingLayoutBinding;

/**
 * xxxx
 * Date: 2024/11/13 11:45
 * Author: liangdp
 */
public class SettingActivity extends BaseActivity<ActivityWeaSettingLayoutBinding> {

    private int statusCode = 1;

    @Override
    public void initData() {
        statusCode = SPUtil.getInt(Constants.KEY_PAGE_MODE, 1);
    }

    @Override
    public void initView() {
        mViewBinding.backIv.setOnClickListener(v -> finish());
        mViewBinding.detailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.put(Constants.KEY_PAGE_MODE, isChecked? 1:0);
            }
        });
        mViewBinding.detailSwitch.setChecked(statusCode==1);
    }
}

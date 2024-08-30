package com.dpzz.weatherpart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dpzz.lib_base.recyclerview.BaseQuickAdapter;
import com.dpzz.lib_base.recyclerview.BaseViewHolder;
import com.dpzz.weatherpart.databinding.ItemWeatherNowBinding;

/**
 * xxxx
 * Date: 2024/7/25 17:12
 * Author: liangdp
 */
public class NowAdapter extends BaseQuickAdapter<String, ItemWeatherNowBinding> {
    public NowAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected ItemWeatherNowBinding getItemViewBinding(ViewGroup parent) {
        return ItemWeatherNowBinding.inflate(LayoutInflater.from(mContext),parent,false);
    }

    @Override
    protected void onBindMyViewHolder(BaseViewHolder<ItemWeatherNowBinding> holder, String item, int position) {
        holder.mViewBinding.tv.setText(item);
    }
}

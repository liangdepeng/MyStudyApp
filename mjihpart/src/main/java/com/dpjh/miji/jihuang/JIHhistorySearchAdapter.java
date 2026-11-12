package com.dpjh.miji.jihuang;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dpjh.miji.databinding.ItemJhHistorySearchBinding;
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter;
import com.dpzz.lib_base.recyclerview.BaseRecyclerViewAdapter;
import com.dpzz.lib_base.recyclerview.BaseViewHolder;

/**
 * java类作用描述
 * <p>
 * Date: 2025/1/13 15:40
 * Author: liangdp
 */
public class JIHhistorySearchAdapter extends BaseQuickAdapter<String, ItemJhHistorySearchBinding> {

    public JIHhistorySearchAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected ItemJhHistorySearchBinding getItemViewBinding(ViewGroup parent) {
        return ItemJhHistorySearchBinding.inflate(LayoutInflater.from(mContext), parent, false);
    }

    @Override
    protected void onBindMyViewHolder(BaseViewHolder<ItemJhHistorySearchBinding> holder, String item, int position) {
        if (!TextUtils.isEmpty(item)) {
            holder.mViewBinding.contentTv.setText(item);
        }
    }
}

package com.dpzz.lib_base.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

/**
 * 快速设置 RecyclerView Adapter
 * 1、item布局基于Viewbinding
 * 2、可设置点击事件，长按事件
 *
 * @param <T> item 数据Bean
 * @param <VB> Viewbinding 布局
 */
public abstract class BaseQuickAdapter<T, VB extends ViewBinding> extends BaseRecyclerViewAdapter<T, BaseViewHolder<VB>> {

    private OnMyItemClickListener itemClickListener;
    private OnMyItemLongClickListener itemLongClickListener;

    public BaseQuickAdapter(Context mContext) {
        super(mContext);
    }

    public void setItemClickListener(OnMyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnMyItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    protected BaseViewHolder<VB> onCreateMyViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder<VB> viewHolder = new BaseViewHolder<>(getItemViewBinding(parent));
        viewHolder.bindViewClickListener(this,itemClickListener);
        viewHolder.bindViewLongClickListener(this,itemLongClickListener);
        return viewHolder;
    }

    protected abstract VB getItemViewBinding(ViewGroup parent);

}

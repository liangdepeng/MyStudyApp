package com.dpzz.lib_base.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

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
        viewHolder.bindViewClickListener(this, itemClickListener);
        viewHolder.bindViewLongClickListener(this, itemLongClickListener);
        return new BaseViewHolder<>(getItemViewBinding(parent));
    }

    protected abstract VB getItemViewBinding(ViewGroup parent);

}
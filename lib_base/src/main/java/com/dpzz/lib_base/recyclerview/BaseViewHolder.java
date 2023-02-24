package com.dpzz.lib_base.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class BaseViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {

    public VB mViewBinding;

    public BaseViewHolder(@NonNull VB viewBinding) {
        super(viewBinding.getRoot());
        mViewBinding = viewBinding;
    }

    public final void bindViewClickListener(final BaseRecyclerViewAdapter adapter, OnMyItemClickListener itemClickListener) {
        itemView.setOnClickListener(v -> {
            if (itemClickListener == null)
                return;
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION)
                return;
            itemClickListener.onItemClick(adapter, position);
        });
    }

    public final void bindViewLongClickListener(final BaseRecyclerViewAdapter adapter, OnMyItemLongClickListener itemClickListener) {
        itemView.setOnLongClickListener(v -> {
            if (itemClickListener == null)
                return false;
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION)
                return false;
            itemClickListener.onItemLongClick(adapter, position);
            return true;
        });
    }
}

package com.dpzz.lib_base.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

//    private OnMyItemClickListener itemClickListener;
//    private OnMyItemLongClickListener itemLongClickListener;
    protected List<T> mDatas = new ArrayList<>();
    protected Context mContext;

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

//    public void setItemClickListener(OnMyItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
//
//    public void setItemLongClickListener(OnMyItemLongClickListener itemLongClickListener) {
//        this.itemLongClickListener = itemLongClickListener;
//    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<T> list) {
        mDatas.clear();
        if (list != null) {
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<T> list) {
        if (list != null) {
            mDatas.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        VH vh = (VH) new BaseViewHolder<VB>(getItemViewBinding(parent));
//        vh.bindViewClickListener(this,itemClickListener);
//        vh.bindViewLongClickListener(this,itemLongClickListener);
        return onCreateMyViewHolder(parent,viewType);
    }



    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindMyViewHolder(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    protected abstract void onBindMyViewHolder(VH holder, T item, int position);

    protected abstract VH onCreateMyViewHolder(ViewGroup parent, int viewType);
}

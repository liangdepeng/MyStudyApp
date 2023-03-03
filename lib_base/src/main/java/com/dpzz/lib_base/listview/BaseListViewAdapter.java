package com.dpzz.lib_base.listview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListViewAdapter<T, VB extends ViewBinding> extends BaseAdapter {

    protected List<T> mDatas = new ArrayList<>();

    public void setData(List<T> datas) {
        mDatas.clear();
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VB viewBinding;
        if (convertView == null) {
            viewBinding = getItemViewBinding(position, parent);
            convertView = viewBinding.getRoot();
            convertView.setTag(viewBinding);
        } else {
            viewBinding = (VB) convertView.getTag();
        }
        OnBindItemView(viewBinding, getItem(position), position);
        return convertView;
    }

    protected abstract void OnBindItemView(VB viewBinding, T item, int position);

    protected abstract VB getItemViewBinding(int position, ViewGroup parent);

}

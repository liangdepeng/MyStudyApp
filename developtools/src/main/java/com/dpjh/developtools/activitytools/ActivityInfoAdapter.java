package com.dpjh.developtools.activitytools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpjh.developtools.databinding.ItemActivityInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class ActivityInfoAdapter extends RecyclerView.Adapter<ActivityInfoAdapter.ViewHolder> {

    private List<AppInfoBean> appInfoBeans = new ArrayList<>();
    private Context context;

    public ActivityInfoAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<AppInfoBean> datas) {
        appInfoBeans.clear();
        if (datas != null) {
            appInfoBeans.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addData(AppInfoBean data) {
        appInfoBeans.add(0, data);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemActivityInfoBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfoBean bean = appInfoBeans.get(position);
        holder.binding.appNameTv.setText(bean.getAppName());
        holder.binding.contentTv.setText(bean.getCurrentActivity());
        holder.binding.iconIv.setImageDrawable(((Drawable) bean.getIconUrl()));
       // Glide.with(context).load(bean.getIconUrl()).error(R.mipmap.ic_launcher).into(holder.binding.iconIv);
    }

    @Override
    public int getItemCount() {
        return appInfoBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemActivityInfoBinding binding;

        public ViewHolder(ItemActivityInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}

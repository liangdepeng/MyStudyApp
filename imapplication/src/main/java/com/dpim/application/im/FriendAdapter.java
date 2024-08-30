package com.dpim.application.im;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpim.application.UserInfoBean;
import com.dpim.application.databinding.ItemFriendBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private ItemClickListener itemClickListener;
    private final List<UserInfoBean> list = new ArrayList<>();

    public FriendAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<UserInfoBean> datas) {
        list.clear();
        if (datas != null) {
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding binding = ItemFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfoBean bean = list.get(position);
        holder.binding.userIdTv.setText(bean.getUserId());
        holder.binding.userNameTv.setText(bean.getUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFriendBinding binding;

        public ViewHolder(ItemFriendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position < 0 || position > getItemCount() - 1) {
                        return;
                    }
                    UserInfoBean infoBean = list.get(position);
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(infoBean, position);
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(UserInfoBean bean, int position);
    }

}

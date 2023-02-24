package com.dpzz.mvpart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dpzz.lib_base.util.PxUtils;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.bean.RecommendMvBean;
import com.dpzz.mvpart.databinding.ItemRecommendMvBinding;

import java.util.ArrayList;
import java.util.List;

public class RecommendMvAdapter extends RecyclerView.Adapter<RecommendMvAdapter.ViewHolder> {

    private List<RecommendMvBean.MoviesDataBean> data = new ArrayList<>();
    private Context mContext;
    private final int itemWidth;
    private final int itemHeight;


    public RecommendMvAdapter(Context context) {
        this.mContext = context;
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        itemWidth = (int) ((widthPixels - PxUtils.dip2px(mContext, 56f)) / 4f);
        itemHeight = (int) (itemWidth * 1.5f);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<RecommendMvBean.MoviesDataBean> data) {
        this.data.clear();
        if (data != null && data.size() > 0) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendMvBinding mvBinding = ItemRecommendMvBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(mvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendMvBean.MoviesDataBean item = getItem(position);
        holder.binding.mvNameTv.setText(item.movieCn);
        Glide.with(mContext)
                .load(item.imgUrl)
                .transform(new CenterCrop(), new RoundedCorners(20))
                .error(R.drawable.ic_launcher_background)
                .into(holder.binding.imageV1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public RecommendMvBean.MoviesDataBean getItem(int position) {
        return data.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemRecommendMvBinding binding;

        public ViewHolder(@NonNull ItemRecommendMvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            ViewGroup.LayoutParams params = binding.imageV1.getLayoutParams();
            params.width = itemWidth;
            params.height = itemHeight;
            binding.imageV1.setLayoutParams(params);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

package com.dpzz.mvpart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dpzz.lib_base.recyclerview.BaseRecyclerViewAdapter;
import com.dpzz.lib_base.recyclerview.BaseViewHolder;
import com.dpzz.lib_base.util.PxUtils;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.bean.RecommendMvBean;
import com.dpzz.mvpart.databinding.ItemRecommendMvBinding;

public class RecommendAdapter2 extends BaseRecyclerViewAdapter<RecommendMvBean.MoviesDataBean, RecommendAdapter2.ViewHolder> {

    private final int itemWidth;
    private final int itemHeight;

    public RecommendAdapter2(Context context) {
        super(context);
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        itemWidth = (int) ((widthPixels - PxUtils.dip2px(mContext, 56f)) / 4f);
        itemHeight = (int) (itemWidth * 1.5f);
    }

    @Override
    protected void onBindMyViewHolder(ViewHolder holder, RecommendMvBean.MoviesDataBean item, int position) {
        holder.mViewBinding.mvNameTv.setText(item.movieCn);
        Glide.with(mContext)
                .load(item.imgUrl)
                .transform(new CenterCrop(), new RoundedCorners(20))
                .error(R.drawable.ic_launcher_background)
                .into(holder.mViewBinding.imageV1);
    }

    @Override
    protected ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRecommendMvBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    public class ViewHolder extends BaseViewHolder<ItemRecommendMvBinding>{
        public ViewHolder(@NonNull ItemRecommendMvBinding viewBinding) {
            super(viewBinding);
            ViewGroup.LayoutParams params = mViewBinding.imageV1.getLayoutParams();
            params.width = itemWidth;
            params.height = itemHeight;
            mViewBinding.imageV1.setLayoutParams(params);
        }
    }
}

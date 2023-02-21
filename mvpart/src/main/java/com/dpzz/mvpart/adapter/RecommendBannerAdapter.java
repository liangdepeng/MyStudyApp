package com.dpzz.mvpart.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dpzz.lib_base.PxUtils;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.bean.RecommendBannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class RecommendBannerAdapter extends BannerAdapter<RecommendBannerBean.ItemsDataBean, RecommendBannerAdapter.ViewHolder> {

    private Context mContext;

    public RecommendBannerAdapter(Context context, List<RecommendBannerBean.ItemsDataBean> datas) {
        super(datas);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = PxUtils.dip2px(mContext,12);
        params.rightMargin = PxUtils.dip2px(mContext,12);
        imageView.setLayoutParams(params);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindView(ViewHolder holder, RecommendBannerBean.ItemsDataBean data, int position, int size) {
        Glide.with(mContext)
                .load(data.imgApp)
                .transform(new CenterCrop(),new RoundedCorners(20))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(@NonNull ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }

}

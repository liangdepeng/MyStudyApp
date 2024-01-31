package com.dpzz.mvpart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dpzz.lib_base.imageload.ImageLoader;
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter;
import com.dpzz.lib_base.recyclerview.BaseViewHolder;
import com.dpzz.lib_base.util.PxUtils;
import com.dpzz.lib_base.util.ScreenUtil;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.bean.RecommendMvBean;
import com.dpzz.mvpart.databinding.ItemRecommendMvBinding;

public class RecommendAdapter3 extends BaseQuickAdapter<RecommendMvBean.MoviesDataBean, ItemRecommendMvBinding> {

    private final int itemWidth;
    private final int itemHeight;

    public RecommendAdapter3(Context mContext) {
        super(mContext);
        itemWidth = (int) ((ScreenUtil.getWidth() - PxUtils.dip2px(mContext, 56f)) / 4f);
        itemHeight = (int) (itemWidth * 1.5f);
    }

    @Override
    protected BaseViewHolder<ItemRecommendMvBinding> onCreateMyViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder<ItemRecommendMvBinding> viewHolder = super.onCreateMyViewHolder(parent, viewType);
        ViewGroup.LayoutParams params = viewHolder.mViewBinding.imageV1.getLayoutParams();
        params.width = itemWidth;
        params.height = itemHeight;
        viewHolder.mViewBinding.imageV1.setLayoutParams(params);
        return viewHolder;
    }

    @Override
    protected ItemRecommendMvBinding getItemViewBinding(ViewGroup parent) {
        return ItemRecommendMvBinding.inflate(LayoutInflater.from(mContext), parent, false);
    }

    @Override
    protected void onBindMyViewHolder(BaseViewHolder<ItemRecommendMvBinding> holder, RecommendMvBean.MoviesDataBean item, int position) {
        holder.mViewBinding.mvNameTv.setText(item.movieCn);
//        Glide.with(mContext)
//                .load(item.imgUrl)
//                .transform(new CenterCrop(), new RoundedCorners(20))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.mViewBinding.imageV1);
        ImageLoader.getInstance().loadImageWithCorner(mContext,holder.mViewBinding.imageV1,item.imgUrl,
                20,R.drawable.ic_launcher_background);
    }
}

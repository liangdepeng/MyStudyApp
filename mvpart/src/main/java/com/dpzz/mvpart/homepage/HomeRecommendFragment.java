package com.dpzz.mvpart.homepage;

import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpzz.lib_base.base.BaseFragment;
import com.dpzz.lib_base.util.PxUtils;
import com.dpzz.lib_base.util.ToastUtil;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.adapter.RecommendAdapter3;
import com.dpzz.mvpart.adapter.RecommendBannerAdapter;
import com.dpzz.mvpart.bean.RecommendBannerBean;
import com.dpzz.mvpart.bean.RecommendMvBean;
import com.dpzz.mvpart.databinding.FragmentHomeRecommandBinding;
import com.dpzz.mvpart.vm.RecommendVm;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.DepthPageTransformer;

import java.util.List;

public class HomeRecommendFragment extends BaseFragment<FragmentHomeRecommandBinding> {

    private RecommendBannerAdapter bannerAdapter;
    private RecommendVm recommendVm;
    private RecommendAdapter3 recommendMvAdapter;

    public static HomeRecommendFragment newInstance() {
        HomeRecommendFragment recommendFragment = new HomeRecommendFragment();
        // todo params
        return recommendFragment;
    }

    @Override
    public void initContentView() {
        bannerAdapter = new RecommendBannerAdapter(mContext, null);
        mViewBinding.banner.post(() -> {
            ViewGroup.LayoutParams layoutParams = mViewBinding.banner.getLayoutParams();
            layoutParams.height = (mViewBinding.banner.getWidth() - PxUtils.dip2px(mContext, 24f)) / 7 * 3;
            mViewBinding.banner.setLayoutParams(layoutParams);
        });

        mViewBinding.banner.setAdapter(bannerAdapter)
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(mContext))
                .setPageTransformer(new DepthPageTransformer());

        mViewBinding.recommendMvRv.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recommendMvAdapter = new RecommendAdapter3(mContext);
        mViewBinding.recommendMvRv.setAdapter(recommendMvAdapter);

        mViewBinding.mvRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.first_rb) {
                    mViewBinding.firstRb.setTextSize(16f);
                    mViewBinding.secondRb.setTextSize(14f);
                    //... todo
                } else if (checkedId == R.id.second_rb) {
                    mViewBinding.firstRb.setTextSize(14f);
                    mViewBinding.secondRb.setTextSize(16f);
                    // todo ...
                }
            }
        });

        recommendVm = new ViewModelProvider(this).get(RecommendVm.class);

        recommendVm.requestBanner();
        recommendVm.bannerData.observe(this, bannerBean -> {
            if (bannerBean == null) {
                ToastUtil.show("请求失败");
                return;
            }
            if (bannerBean.regionList == null || bannerBean.regionList.size() == 0) {
                return;
            }
            RecommendBannerBean.RegionListDataBean bean = bannerBean.regionList.get(0);
            if (bean == null || bean.items == null || bean.items.size() == 0)
                return;
            bannerAdapter.setDatas(bean.items);
        });

        recommendVm.requestMvRecommend();
        recommendVm.recommendData.observe(this, new Observer<RecommendMvBean>() {
            @Override
            public void onChanged(RecommendMvBean recommendMvBean) {
                if (recommendMvBean == null)
                    return;
                List<RecommendMvBean.MoviesDataBean> hotPlayMovies = recommendMvBean.hotPlayMovies;
                if (hotPlayMovies != null && hotPlayMovies.size() > 0) {
                    recommendMvAdapter.setData(hotPlayMovies);
                }
            }
        });
    }
}

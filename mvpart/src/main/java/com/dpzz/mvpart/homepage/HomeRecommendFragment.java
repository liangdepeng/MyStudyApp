package com.dpzz.mvpart.homepage;

import android.view.ViewGroup;

import com.dpzz.lib_base.BaseFragment;
import com.dpzz.lib_base.PxUtils;
import com.dpzz.lib_base.ToastUtil;
import com.dpzz.lib_base.http.HttpMethod;
import com.dpzz.lib_base.http.HttpResponse;
import com.dpzz.lib_base.http.RequestManager;
import com.dpzz.lib_base.http.RequestParams;
import com.dpzz.lib_base.http.contans.HttpUrl;
import com.dpzz.mvpart.adapter.RecommendBannerAdapter;
import com.dpzz.mvpart.bean.RecommendBannerBean;
import com.dpzz.mvpart.databinding.FragmentHomeRecommandBinding;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.DepthPageTransformer;

public class HomeRecommendFragment extends BaseFragment<FragmentHomeRecommandBinding> implements HttpResponse {

    private RecommendBannerAdapter bannerAdapter;

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
            layoutParams.height = (mViewBinding.banner.getWidth()- PxUtils.dip2px(mContext,24f))/7*3;
            mViewBinding.banner.setLayoutParams(layoutParams);
        });

        mViewBinding.banner.setAdapter(bannerAdapter)
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(mContext))
                .setPageTransformer(new DepthPageTransformer());
        RequestParams requestParams = new RequestParams(HttpUrl.H0ME_BANNER_URL, HttpMethod.GET);
        requestParams.setParseClass(RecommendBannerBean.class);
        RequestManager.getInstance().startRequest(requestParams, this);
    }

    @Override
    public void onSuccess(Object data, RequestParams requestParams) {
        if (data instanceof RecommendBannerBean) {
            RecommendBannerBean bannerBean = (RecommendBannerBean) data;
            if (bannerBean.regionList == null || bannerBean.regionList.size() == 0) {
                return;
            }
            RecommendBannerBean.RegionListDataBean bean = bannerBean.regionList.get(0);
            if (bean == null || bean.items == null || bean.items.size() == 0)
                return;
            // mViewBinding.banner.getAdapter().setDatas(bean.items);
            bannerAdapter.setDatas(bean.items);
        }
    }

    @Override
    public void onFailed(RequestParams requestParams, Exception e) {
        ToastUtil.show("请求失败-" + e.getMessage());
    }
}

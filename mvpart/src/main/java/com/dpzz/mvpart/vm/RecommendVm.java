package com.dpzz.mvpart.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dpzz.lib_base.http.HttpMethod;
import com.dpzz.lib_base.http.HttpResponse;
import com.dpzz.lib_base.http.RequestManager;
import com.dpzz.lib_base.http.RequestParams;
import com.dpzz.lib_base.http.contans.HttpUrl;
import com.dpzz.lib_base.util.ToastUtil;
import com.dpzz.mvpart.bean.RecommendBannerBean;
import com.dpzz.mvpart.bean.RecommendMvBean;

public class RecommendVm extends ViewModel {

    public final MutableLiveData<RecommendBannerBean> bannerData = new MutableLiveData<>();
    public final MutableLiveData<RecommendMvBean> recommendData = new MutableLiveData<>();

    public void requestBanner(){
        RequestParams requestParams = new RequestParams(HttpUrl.H0ME_BANNER_URL, HttpMethod.GET);
        requestParams.setParseClass(RecommendBannerBean.class);
        RequestManager.getInstance().startRequest(requestParams, new HttpResponse() {
            @Override
            public void onSuccess(Object data, RequestParams requestParams) {
                if (data instanceof RecommendBannerBean) {
                    RecommendBannerBean bannerBean = (RecommendBannerBean) data;
                    bannerData.postValue(bannerBean);
                } else {
                    onFailed(requestParams,new Exception("parse failed"));
                }
            }

            @Override
            public void onFailed(RequestParams requestParams, Exception e) {
                bannerData.postValue(null);
            }
        });
    }

    public void requestMvRecommend(){
        RequestParams requestParams = new RequestParams(HttpUrl.H0ME_RECOMMEND_URL, HttpMethod.GET);
        requestParams.setParseClass(RecommendMvBean.class);
        RequestManager.getInstance().startRequest(requestParams, new HttpResponse() {
            @Override
            public void onSuccess(Object data, RequestParams requestParams) {
                if (data instanceof RecommendMvBean){
                    recommendData.postValue(((RecommendMvBean) data));
                }else {
                    onFailed(requestParams,new Exception("parse failed"));
                }
            }

            @Override
            public void onFailed(RequestParams requestParams, Exception e) {
                ToastUtil.show("请求失败");
                recommendData.postValue(null);
            }
        });
    }


}

package com.dpzz.mvpart.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dpzz.lib_base.http.HttpResponse;
import com.dpzz.lib_base.http.RequestManager;
import com.dpzz.lib_base.http.RequestParams;
import com.dpzz.lib_base.http.contans.Constants;
import com.dpzz.lib_base.http.contans.HttpUrl;
import com.dpzz.lib_base.util.ToastUtil;
import com.dpzz.mvpart.bean.MvDetailBean;

public class MvDetailVm extends ViewModel {

    public MutableLiveData<MvDetailBean> detailData = new MutableLiveData<>();

    public void requestMvDetail(int mvId) {
        RequestParams requestParams = new RequestParams(HttpUrl.H0ME_VIDEO_DETAIL_URL);
        requestParams.putParam("movieId", mvId)
                .putParam("locationId", Constants.LOCATION_ID)
                .setParseClass(MvDetailBean.class);
        RequestManager.getInstance().startRequest(requestParams, new HttpResponse<MvDetailBean>() {
//            @Override
//            public void onSuccess(Object data, RequestParams requestParams) {
//                if (data instanceof MvDetailBean){
//                    MvDetailBean detailBean = (MvDetailBean) data;
//                    detailData.setValue(detailBean);
//                }else {
//                    onFailed(requestParams,new Exception("解析失败或者为null"));
//                }
//            }

            @Override
            public void onSuccess(MvDetailBean data, RequestParams requestParams) {
                if (data !=null){
                   // MvDetailBean detailBean = (MvDetailBean) data;
                    detailData.setValue(data);
                }else {
                    onFailed(requestParams,new Exception("解析失败或者为null"));
                }
            }

            @Override
            public void onFailed(RequestParams requestParams, Exception e) {
                ToastUtil.show(e.getMessage());
            }
        });
    }

}

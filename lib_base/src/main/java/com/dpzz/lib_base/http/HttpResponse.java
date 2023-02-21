package com.dpzz.lib_base.http;

public interface HttpResponse {

    void onSuccess(Object data, RequestParams requestParams);

    void onFailed(RequestParams requestParams, Exception e);
}

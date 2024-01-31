package com.dpzz.lib_base.http;

public interface HttpResponse<T> {

    void onSuccess(T data, RequestParams requestParams);

    void onFailed(RequestParams requestParams, Exception e);
}

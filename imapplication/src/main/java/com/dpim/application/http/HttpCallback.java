package com.dpim.application.http;

public interface HttpCallback {
    void onSuccess(Object response,String msg);
    void onFailed(String msg);
}

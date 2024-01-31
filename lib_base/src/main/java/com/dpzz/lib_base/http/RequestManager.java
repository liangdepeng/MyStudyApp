package com.dpzz.lib_base.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.dpzz.lib_base.util.UrlUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager {

    private OkHttpClient client;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final MediaType JSON = MediaType.get("application/json");

    private final static class Instance {
        private final static RequestManager requestManager = new RequestManager();
    }

    private RequestManager() {
        initOkHttp();
    }

    private void initOkHttp() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .callTimeout(10000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.IGNORE_SSL_TRUST_MANAGER_X509)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    public static RequestManager getInstance() {
        return Instance.requestManager;
    }

    public <T> void startRequest(RequestParams requestParams, HttpResponse<T> callback) {
        if (requestParams == null)
            return;
// Type parameter 'T' explicitly extends 'java.lang.Object'
        String url = requestParams.getRequestUrl();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http")) {
            url = UrlUtil.decode(url);
        }

        Request.Builder builder = new Request.Builder();

        if (requestParams.getHttpMethod() == HttpMethod.GET) {
            if (!requestParams.getParamsMap().isEmpty()) {
                StringBuilder urlBuild = new StringBuilder(url);
                urlBuild.append("?");
                for (Map.Entry<String, Object> entry : requestParams.getParamsMap().entrySet()) {
                    urlBuild.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
                url = urlBuild.substring(0, urlBuild.length() - 1);
            }
            builder = builder.url(url).get();
        } else if (requestParams.getHttpMethod() == HttpMethod.POST) {
            //  new FormBody.Builder().build();
            builder = builder.url(url).post(RequestBody.create(requestParams.getParamsJson(), JSON));
        }//......

        // 公共请求头
        // builder.addHeader(key,value);

        HashMap<String, Object> headerMap = requestParams.getHeaderMap();

        if (!headerMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                builder = builder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        Request request = builder.build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(() -> {
                    if (callback != null) {
                        callback.onFailed(requestParams, e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (callback == null)
                    return;
                if (response.body() == null)
                    return;
                String responseStr = response.body().string();
                handler.post(() -> {
                    try {
                        /**
                         * 解析仅适用于 本项目指定结构
                         * {
                         *     “code":0,
                         *     "message":"",
                         *     "data":{
                         *         xxx...
                         *     }
                         * }
                         */
                        if (TextUtils.isEmpty(responseStr)) {
                            callback.onFailed(requestParams, new Exception("返回为空 null"));
                        } else {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = jsonObject.optInt("code", -1);
                            JSONObject obj = jsonObject.optJSONObject("data");
                            String msg = jsonObject.optString("msg", "未知错误");
                            if (code == 0 && obj != null && !TextUtils.isEmpty(obj.toString())) {
                                if (requestParams.getParseClass() == null) {
                                    callback.onSuccess((T) obj, requestParams);
                                } else {
                                    callback.onSuccess(((T) new Gson().fromJson(obj.toString(), requestParams.getParseClass())), requestParams);
                                }
                            } else {
                                callback.onFailed(requestParams, new Exception(msg));
                            }
                        }
                    } catch (Exception e) {
                        callback.onFailed(requestParams, e);
                        e.printStackTrace();
                    }
                });
            }
        });
    }

}

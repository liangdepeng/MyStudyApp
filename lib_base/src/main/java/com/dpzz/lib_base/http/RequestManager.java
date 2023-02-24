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
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.IGNORE_SSL_TRUST_MANAGER_X509)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    public static RequestManager getInstance() {
        return Instance.requestManager;
    }

    public void startRequest(RequestParams requestParams, HttpResponse callback) {
        if (requestParams == null)
            return;

        String url = requestParams.getRequestUrl();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.startsWith("http")) {
            url = UrlUtil.decode(url);
        }

        Request.Builder builder = new Request.Builder()
                .url(url);

        if (requestParams.getHttpMethod() == HttpMethod.GET) {
            builder = builder.get();
        } else if (requestParams.getHttpMethod() == HttpMethod.POST) {
            //  new FormBody.Builder().build();
            builder = builder.post(RequestBody.create(requestParams.getParamsJson(), JSON));
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
                String responseStr = response.body().string();
                handler.post(() -> {
                    if (callback == null)
                        return;
                    try {
                        if (TextUtils.isEmpty(responseStr)) {
                            callback.onFailed(requestParams, new Exception("返回为空 null"));
                        } else {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = jsonObject.optInt("code", -1);
                            JSONObject obj = jsonObject.optJSONObject("data");
                            String msg = jsonObject.optString("msg", "未知错误");
                            if (code == 0 && obj != null && !TextUtils.isEmpty(obj.toString())) {
                                if (requestParams.getParseClass() == null) {
                                    callback.onSuccess(obj, requestParams);
                                } else {
                                    callback.onSuccess(new Gson().fromJson(obj.toString(), requestParams.getParseClass()), requestParams);
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

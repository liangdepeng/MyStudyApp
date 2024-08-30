package com.dpim.application.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dpim.application.ToastUtil;
import com.dpim.application.UserInfoBean;
import com.dpim.application.base.SPUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {

    private final String TAG = "HttpHelper";
    private final OkHttpClient client;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final static class Instance {
        private final static HttpHelper httphelper = new HttpHelper();
    }

    private HttpHelper() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .callTimeout(10000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.IGNORE_SSL_TRUST_MANAGER_X509)
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    public static HttpHelper getInstance() {
        return Instance.httphelper;
    }


    public void requestRegister(UserInfoBean bean, HttpCallback callback) {
        if (bean == null)
            return;

        FormBody formBody = new FormBody.Builder()
                .add("userId", bean.getUserId())
                .add("name", bean.getUserName())
                .add("portraitUri", bean.getUserLogoUrl())
                .build();

        Request request = new Request.Builder()
                .url("https://api.rong-api.com/user/getToken.json")
                .post(formBody)
                .headers(getHeaders())
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ToastUtil.show(e.toString());
                handler.post(() -> {
                    if (callback != null) {
                        callback.onFailed(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject json = new JSONObject(result);
                    int code = json.optInt("code");
                    Log.e(TAG, json.toString());
                    if (code != 200) {
                        ToastUtil.show(json.toString());
                    } else {
                        String userId = json.optString("userId", "");
                        String token = json.optString("token", "");
                        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(token)) {
                            ToastUtil.show(json.toString());
                            return;
                        }
                        SPUtils.put(userId, token);
                        handler.post(() -> {
                            if (callback != null) {
                                callback.onSuccess(token, "login success");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void requestQueryInfo(String userId, HttpCallback callback) {
        if (TextUtils.isEmpty(userId)) {
            return;
        }

        FormBody formBody = new FormBody.Builder()
                .add("userId", userId).build();

        Request request = new Request.Builder()
                .url("https://api.rong-api.com/user/info.json")
                .headers(getHeaders())
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(() -> {
                    if (callback != null) {
                        callback.onFailed(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    handler.post(() -> {
                        if (code != 200) {
                            if (callback != null) {
                                callback.onFailed(result+" 添加失败，请确认userid已被注册");
                            }
                        } else {
                            if (callback != null) {
                                callback.onSuccess(jsonObject, "success");
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Headers getHeaders() {
        int num = new Random(99999999).nextInt();
        long timeMillis = System.currentTimeMillis();
        return new Headers.Builder()
                .add("App-Key", Utils.getKey())
                .add("Nonce", String.valueOf(num))
                .add("Timestamp", String.valueOf(timeMillis))
                .add("Signature", getSha1(Utils.getAppSecret() + num + timeMillis))
                .build();
    }


    private String getSha1(String str) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = str.getBytes();

            digest.update(bytes);
            byte[] hashedBytes = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String hashedData = hexString.toString();
            Log.e("Httpjiami", "加密结果：" + hashedData);
            return hashedData;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Httpjiami", "加密结果：" + e.toString());
        }
        return "";
    }
}

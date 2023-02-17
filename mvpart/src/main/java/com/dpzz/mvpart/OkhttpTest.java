package com.dpzz.mvpart;

import androidx.annotation.NonNull;

import com.dpzz.lib_base.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpTest {


    public static void ok() {


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://front-gateway.mtime.cn/ticket/schedule/showing/movies.api?locationId=974")
                .get()
                .build();

//
//        try {
//            okHttpClient.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //  ToastUtil.show(e.getMessage());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                ToastUtil.show(string);

                call.clone().enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        ToastUtil.show(" 22222222222222 "+string);
                    }
                });
            }
        });
    }
}

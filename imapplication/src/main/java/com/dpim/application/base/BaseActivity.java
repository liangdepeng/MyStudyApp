package com.dpim.application.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private final static List<Activity> activities = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }


    public void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
        }
        if (progressDialog.isShowing()){
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(() -> progressDialog.show());
        }else {
            progressDialog.show();
        }
    }

    public void dismissLoading() {
        handler.postDelayed(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        },500);

    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public static Activity getTopActivity() {
        if (activities.isEmpty())
            return null;
        return activities.get(activities.size() - 1);
    }
}

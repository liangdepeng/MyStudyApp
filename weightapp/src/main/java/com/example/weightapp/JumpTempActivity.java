package com.example.weightapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Date: 2025/4/23 11:09
 * Author: liangdp
 */
public class JumpTempActivity extends AppCompatActivity {

    private String widgetExtra;
    private int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealJumpInfo(this,getIntent());
        finish();
    }

    public static void dealJumpInfo(Activity activity,Intent data){
        String widgetExtra = "";
        int code = -1;
        if (data!=null){
            code = data.getIntExtra("code", -1);
            widgetExtra = data.getStringExtra("widget22");
        }
        if (!MainWeightActivity.isLaunch){
            Intent intent = activity.getPackageManager()
                    .getLaunchIntentForPackage(activity.getPackageName());
            if (intent != null) {
                intent.putExtra("w_code",code);
                intent.putExtra("w_widget",widgetExtra);
                // 设置Intent的标志位，确保能够清除任务栈
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // 启动新的Activity
                activity.startActivity(intent);
            }
        }else {
            Intent intent = new Intent(activity, MainWeightActivity.class);
            intent.putExtra("w_code",code);
            intent.putExtra("w_widget",widgetExtra);
            activity.startActivity(intent);
        }
    }
}

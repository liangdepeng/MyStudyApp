package com.example.weightapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 小组件 test
 * Date: 2025/3/3 15:03
 * Author: liangdp
 */
public class MainWeightActivity extends AppCompatActivity {

    public static boolean isLaunch = false;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_layout);
        isLaunch = true;
        updateJumpClass(getIntent());
        dealMsgWidget(getIntent());

        findViewById(R.id.add_xiaozujian1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetManager.requestPinAppWidget(MainWeightActivity.this, NewAppWidget.class);
            }
        });

        findViewById(R.id.add_xiaozujian2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetManager.requestPinAppWidget(MainWeightActivity.this, NewAppWidget2.class);
            }
        });

        findViewById(R.id.add_xiaozujian3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetManager.requestPinAppWidget(MainWeightActivity.this , NewAppWidget3.class);
            }
        });

        findViewById(R.id.delete_xiaozujian1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetManager.deleteAppWidget(MainWeightActivity.this, NewAppWidget.class);
            }
        });


    }

    private void updateJumpClass(Intent intent) {
        if (intent==null)
            return;
        SharedPreferences preferences = getSharedPreferences("widget_config", Context.MODE_PRIVATE);
        String jumpClass = preferences.getString("widget_jump_class", "");

        String widget22Tag = intent.getStringExtra("w_widget");

        Log.e("widgetcus","启动 class -> "+jumpClass +" widgettag-> "+widget22Tag);


        if (NewAppWidget.class.getSimpleName().equals(widget22Tag)){
            if (AppWidgetUtils.JUMP_CLASS1.equals(jumpClass)){
                preferences.edit().putString("widget_jump_class", AppWidgetUtils.JUMP_CLASS2).apply();
            }else {
                preferences.edit().putString("widget_jump_class", AppWidgetUtils.JUMP_CLASS1).apply();
            }
            String temoclazz = preferences.getString("widget_jump_class", "");
            Log.e("widgetcus","更换后 class -> "+temoclazz );
        }

        AppWidgetUtils.notifyDataUpdate(this, NewAppWidget.class);
    }

    private void dealMsgWidget(Intent intent){
        if (intent==null)
            return;

        String widget22Tag = intent.getStringExtra("w_widget");
        int code = intent.getIntExtra("w_code", -1);

        if (!TextUtils.isEmpty(widget22Tag)){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent targetIntent = new Intent(MainWeightActivity.this, WidgetTargetActivity.class);
                    targetIntent.putExtra("w_code",code);
                    targetIntent.putExtra("w_widget",widget22Tag);
                    startActivity(targetIntent);
                    // Toast.makeText(MainWeightActivity.this, "来自 "+widget22Tag+" 的启动消息  code:"+code, Toast.LENGTH_LONG).show();
                }
            },1000);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealMsgWidget(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            moveTaskToBack(false);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        overridePendingTransition(0,0);
        super.onDestroy();
    }
}

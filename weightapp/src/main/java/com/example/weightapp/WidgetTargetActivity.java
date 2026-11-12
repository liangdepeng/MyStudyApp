package com.example.weightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Date: 2025/4/23 11:04
 * Author: liangdp
 */
public class WidgetTargetActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_target);


        Intent intent = getIntent();

        TextView targetTv = findViewById(R.id.targettv);

        if (intent != null) {
            String params11 = intent.getStringExtra("params11");
            String params22 = intent.getStringExtra("params22");
            String params33 = intent.getStringExtra("params33");

            targetTv.setText(params11 + "   " + params22 + "    " + params33);
        } else {
            targetTv.setText("暂无消息！！！");
        }
    }
}
//        setContentView(R.layout.activity_widget_target_layout);
//        int code = getIntent().getIntExtra("w_code", -1);
//        String widgetExtra = getIntent().getStringExtra("w_widget");
//        tv = findViewById(R.id.text);
//        tv.setText("code:"+code+" widget:"+widgetExtra);
//    }
//}

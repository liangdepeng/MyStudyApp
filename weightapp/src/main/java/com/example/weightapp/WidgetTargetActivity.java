package com.example.weightapp;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_target_layout);
        int code = getIntent().getIntExtra("w_code", -1);
        String widgetExtra = getIntent().getStringExtra("w_widget");
        tv = findViewById(R.id.text);
        tv.setText("code:"+code+" widget:"+widgetExtra);
    }
}

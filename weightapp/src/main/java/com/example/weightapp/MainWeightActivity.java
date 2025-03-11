package com.example.weightapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 小组件 test
 * Date: 2025/3/3 15:03
 * Author: liangdp
 */
public class MainWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_layout);

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
}

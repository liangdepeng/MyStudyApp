package com.dpim.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dpim.application.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
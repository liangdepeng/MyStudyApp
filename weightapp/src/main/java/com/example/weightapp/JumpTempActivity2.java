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
public class JumpTempActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JumpTempActivity.dealJumpInfo(this,getIntent());
        finish();
        overridePendingTransition(0,0);
    }
}

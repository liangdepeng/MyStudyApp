package com.example.weightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

/**
 * java类作用描述
 * <p>
 * Date: 2025/3/21 16:15
 * Author: liangdp
 */
public class Test1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_layout);

        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Test1Activity.this, v, "show_big_image");

                Intent intent = new Intent(Test1Activity.this, Test2Activity.class);
                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }
}

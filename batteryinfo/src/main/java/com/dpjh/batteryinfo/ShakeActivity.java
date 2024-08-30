package com.dpjh.batteryinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_layout);
        //

        ShakingLayout shakeLl = findViewById(R.id.shake_ll);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.itcyr);
        shakeLl.setShakeView(imageView,getResources().getDisplayMetrics().widthPixels-dip2px(this,40f),dip2px(this,200f));
        shakeLl.setLifecycleOwner(this);
    }



    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

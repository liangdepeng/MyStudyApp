package com.example.weightapp;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weightapp.databinding.LayoutDragtestBinding;

/**
 * java类作用描述
 * <p>
 * Date: 2026/3/30 16:31
 * Author: liangdp
 */
public class DragTestActivity extends AppCompatActivity {

    private com.example.weightapp.databinding.LayoutDragtestBinding binding;
    private boolean isMoving = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutDragtestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.contentVv.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION){
                    if (!isMoving){
                        Log.i("DragTestActivity", "onDrag: moving moving moving"+event.toString());
                        isMoving = true;
                    }
                }else {
                    Log.i("DragTestActivity", "onDrag: "+event.toString());
                    isMoving = false;
                }
                return true;
            }

        });

        binding.image1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startDrag(null, new View.DragShadowBuilder(v), v, 0);
                return false;
            }
        });
    }
}

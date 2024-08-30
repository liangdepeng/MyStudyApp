package com.dpzz.lib_base.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dpzz.lib_base.R;

/**
 * xxxx
 * Date: 2024/8/12 15:55
 * Author: liangdp
 */
public class MaxHeightRecyclerView extends RecyclerView {

    private int maxPixelSize;

    public MaxHeightRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public MaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
        if (typedArray != null) {
            maxPixelSize = typedArray.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_maxHeight, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (maxPixelSize > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(maxPixelSize, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}

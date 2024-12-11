package com.dpzz.weatherpart.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.dpzz.lib_base.util.PxUtils;

/**
 * xxxx
 * Date: 2024/11/13 10:16
 * Author: liangdp
 */
public class IndicatorView extends LinearLayout {

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    public void initIndicator(int count, int currentPosition) {
        removeAllViews();
        if (count < 2)
            return;
        for (int i = 0; i < count; i++) {
            View view = new View(getContext());
            view.setBackground(i == currentPosition ? getSelectDrawable() : getNormalDrawable());
            LayoutParams params = new LayoutParams(PxUtils.dip2px(getContext(), 10f), PxUtils.dip2px(getContext(), 10f));
            params.rightMargin = PxUtils.dip2px(getContext(), 10f);
            addView(view, params);
        }
    }

    public void setCurrentPosition(int currentPosition) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setBackground(i == currentPosition ? getSelectDrawable() : getNormalDrawable());
        }
    }

    private Drawable getNormalDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.GRAY);
        return drawable;
    }

    private Drawable getSelectDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.BLUE);
        return drawable;
    }
}

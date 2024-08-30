package com.dpjh.batteryinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class ShakingLayout extends FrameLayout implements SensorEventListener {

    private static final float MOVEMENT_SCALE_FACTOR = 0.1f; // 移动速度调整因子
    private static final float VELOCITY_SCALE_FACTOR = 0.5f; // 碰撞后速度衰减比例
    private static final float MAX_SPEED = 20.0f; // 最大移动速度

    private LifecycleOwner lifecycleOwner;
    private Context context;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float parentViewWidth;
    private float parentViewHeight;
    private boolean isResume = false;

    public ShakingLayout(@NonNull Context context) {
        this(context, null);
    }

    public ShakingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShakingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        intiParams();
    }

    private final LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() {
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_RESUME) {
                isResume = true;
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                isResume = false;
            }
        }
    };

    public void setShakeView(View view, int parentViewWidth, int parentViewHeight) {
        if (getChildCount() > 0)
            return;
        this.parentViewWidth = parentViewWidth;
        this.parentViewHeight = parentViewHeight;
        this.addView(view);
    }

    public void setLifecycleOwner(LifecycleOwner activity) {
        if (activity != null) {
            lifecycleOwner = activity;
            lifecycleOwner.getLifecycle().addObserver(lifecycleEventObserver);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sensorManager.unregisterListener(this);
    }

    private void intiParams() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private float xVelocity = 0;
    private float yVelocity = 0;
    private float currentX = 0;
    private float currentY = 0;


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isResume && event.sensor == accelerometer) {
            float xTempVelocity = -event.values[0];
            float yTempVelocity = event.values[1];

            xVelocity += xTempVelocity * MOVEMENT_SCALE_FACTOR;
            yVelocity += yTempVelocity * MOVEMENT_SCALE_FACTOR;

            // 限制速度
            if (xVelocity > MAX_SPEED) {
                xVelocity = MAX_SPEED;
            } else if (xVelocity < -MAX_SPEED) {
                xVelocity = -MAX_SPEED;
            }
            if (yVelocity > MAX_SPEED) {
                yVelocity = MAX_SPEED;
            } else if (yVelocity < -MAX_SPEED) {
                yVelocity = -MAX_SPEED;
            }

            currentX += xVelocity;
            currentY += yVelocity;

            if (currentX < 0) {
                currentX = 0;
                xVelocity = -xVelocity * VELOCITY_SCALE_FACTOR;
            } else if (parentViewWidth != 0 && currentX > parentViewWidth - getWidth()) {
                currentX = parentViewWidth - getWidth();
                xVelocity = -xVelocity * VELOCITY_SCALE_FACTOR;
            }

            if (currentY < 0) {
                currentY = 0;
                yVelocity = -yVelocity * VELOCITY_SCALE_FACTOR;
            } else if (parentViewHeight != 0 && currentY > parentViewHeight - getHeight()) {
                currentY = parentViewHeight - getHeight();
                yVelocity = -yVelocity * VELOCITY_SCALE_FACTOR;
            }

            setX(currentX);
            setY(currentY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

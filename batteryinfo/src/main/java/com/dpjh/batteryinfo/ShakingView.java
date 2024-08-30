package com.dpjh.batteryinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 *
 * 1）匀变速直线运动
 * 1、速度Vt＝Vo+at 2.位移s＝Vot+at²/2＝V平t= Vt/2t
 * 3.有用推论Vt²-Vo²＝2as
 * 4.平均速度V平＝s/t（定义式）
 * 5.中间时刻速度Vt/2＝V平＝(Vt+Vo)/2
 * 6.中间位置速度Vs/2＝√[(Vo²+Vt²)/2]
 * 7.加速度a＝(Vt-Vo)/t ｛以Vo为正方向,a与Vo同向(加速)a>0；反向则a
 *
 */
public class ShakingView extends View implements SensorEventListener {

    private static final float MOVEMENT_SCALE_FACTOR = 0.1f; // 移动速度调整因子
    private static final float MAX_SPEED = 20.0f; // 最大移动速度

    private float xPos, yPos; // 视图当前位置
    private float xVelocity = 0, yVelocity = 0; // 移动速度
    private Paint paint; // 画笔
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Bitmap bitmap;

    public ShakingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.black));
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.itcyr);

        xPos=(getResources().getDisplayMetrics().widthPixels-dip2px(getContext(),40f))/2f;
        yPos=dip2px(getContext(),200f)*0.5f;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      //  canvas.drawCircle(xPos, yPos, 50, paint);

        if (bitmap!=null){
            canvas.drawBitmap(bitmap,xPos - bitmap.getWidth() / 2f, yPos - bitmap.getHeight() / 2f, null);
        }

        Log.e("sensor777"," x-> "+xPos+" y-> "+yPos);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // SensorManager.SENSOR_DELAY_UI 每秒15次采样
        // SensorManager.SENSOR_DELAY_NORMAL  15次采样
        // SensorManager.SENSOR_DELAY_GAME 50次采样
        // SensorManager.SENSOR_DELAY_FASTEST 500+次采样
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {

            //Log.e("sensor666","加速度 x-> "+event.values[0]+"加速度 y-> "+event.values[0]+"加速度 z-> "+event.values[0]);
            Log.e("sensor666","加速度 x-> "+parseNumber(event.values[0])+"加速度 y-> "+parseNumber(event.values[1])+"加速度 z-> "+parseNumber(event.values[2]));


            float xAccel = -event.values[0];
            float yAccel = event.values[1];

            if (Math.abs(yAccel)<=0.08f){
                yAccel=0.5f;
            }

            // 根据加速度调整速度
            xVelocity += xAccel * MOVEMENT_SCALE_FACTOR;
            yVelocity += yAccel * MOVEMENT_SCALE_FACTOR;
        } else if (event.sensor == gyroscope) {

            //Log.e("sensor666","陀螺仪 x-> "+event.values[0]+"陀螺仪 y-> "+event.values[1]+"陀螺仪 z-> "+event.values[2]);
            //Log.e("sensor666","陀螺仪 x-> "+parseNumber(event.values[0])+"陀螺仪 y-> "+parseNumber(event.values[1])+"陀螺仪 z-> "+parseNumber(event.values[2]));
//
//            float xGyro = event.values[0];
//            float yGyro = event.values[1];
//
//            // 根据陀螺仪的旋转调整速度
//            xVelocity += yGyro * MOVEMENT_SCALE_FACTOR;
//            yVelocity -= xGyro * MOVEMENT_SCALE_FACTOR;
        }

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

        // 更新视图位置
        xPos += xVelocity;
        yPos += yVelocity;

        // 边界检测并处理回弹效果
        if (xPos < 0) {
            xPos = 0;
            xVelocity = -xVelocity * 0.5f; // 回弹效果
        } else if (xPos > getWidth()) {
            xPos = getWidth();
            xVelocity = -xVelocity * 0.5f;
        }
        if (yPos < 0) {
            yPos = 0;
            yVelocity = -yVelocity * 0.5f;
        } else if (yPos > getHeight()) {
            yPos = getHeight();
            yVelocity = -yVelocity * 0.5f;
        }

        // 重绘视图
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 不需要实现
    }


    public String parseNumber(float num){
        BigDecimal decimal = new BigDecimal(num);
        BigDecimal scale = decimal.setScale(2, RoundingMode.DOWN);
        return scale.toString();
    }
}



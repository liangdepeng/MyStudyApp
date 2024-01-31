package com.dpzz.weatherpart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

//在上面的代码中，我们实现了以下功能：
//通过在 xml 布局文件中引入自定义 View，支持在 xml 中对 View 的属性进行设置。
//支持设置自定义 View 的背景颜色、坐标轴线颜色、白天温度折线颜色、夜间温度折线颜色、坐标轴线宽度、温度折线宽度、字体大小等属性。
//支持清空温度数据。
//支持动态添加白天或夜间温度值。
//支持拖拽白天温度折线图上的温度值。
//通过重写 onMeasure 方法，实现自定义 View 的测量。
//通过重写 onTouchEvent 方法，实现拖拽功能。
//此外，为了使代码更加简洁，我们使用了静态温度数据代替传入数据进行绘制，如果需要从外部传入数据的话，可以考虑通过自定义属性的方式来实现。
public class TemperatureLineChartView extends View {

    // 温度数据
    private static int[] DAY_TEMPERATURES = {23, 24, 25, 26, 28, 29, 30, 29, 28, 27, 26, 24};
    private static int[] NIGHT_TEMPERATURES = {18, 19, 20, 20, 21, 22, 23, 22, 21, 20, 19, 18};

    // 背景颜色
    private int mBackgroundColor = Color.WHITE;
    // 坐标轴线颜色
    private int mAxisLineColor = Color.BLACK;
    // 白天温度折线颜色
    private int mDayLineColor = Color.RED;
    // 夜间温度折线颜色
    private int mNightLineColor = Color.BLUE;
    // 坐标轴线宽度
    private int mAxisLineWidth = 5;
    // 温度折线宽度
    private int mLineStrokeWidth = 10;
    // 字体大小
    private int mTextSize = 40;

    // 坐标轴线画笔
    private Paint mAxisLinePaint;
    // 白天温度折线画笔
    private Paint mDayLinePaint;
    // 夜间温度折线画笔
    private Paint mNightLinePaint;
    // 字体画笔
    private Paint mTextPaint;

    // 白天温度数据点
    private Point[] mDayPoints;
    // 夜间温度数据点
    private Point[] mNightPoints;

    // 是否可以拖动
    private boolean mIsDraggable = false;
    // 当前被拖动的温度数据
    private int mDraggingTemperature = 0;

    // 构造方法
    public TemperatureLineChartView(Context context) {
        this(context, null);
    }

    public TemperatureLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initData();
    }

    // 初始化画笔
    private void initPaint() {
        mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(mAxisLineColor);
        mAxisLinePaint.setStyle(Paint.Style.STROKE);
        mAxisLinePaint.setStrokeWidth(mAxisLineWidth);
        mAxisLinePaint.setAntiAlias(true);

        mDayLinePaint = new Paint();
        mDayLinePaint.setColor(mDayLineColor);
        mDayLinePaint.setStyle(Paint.Style.STROKE);
        mDayLinePaint.setStrokeWidth(mLineStrokeWidth);
        mDayLinePaint.setAntiAlias(true);

        mNightLinePaint = new Paint();
        mNightLinePaint.setColor(mNightLineColor);
        mNightLinePaint.setStyle(Paint.Style.STROKE);
        mNightLinePaint.setStrokeWidth(mLineStrokeWidth);
        mNightLinePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mAxisLineColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    // 初始化数据
    private void initData() {
        mDayPoints = new Point[DAY_TEMPERATURES.length];
        mNightPoints = new Point[NIGHT_TEMPERATURES.length];
    }

    // 设置背景颜色
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        invalidate();
    }

    // 设置坐标轴线颜色
    public void setAxisLineColor(int color) {
        mAxisLineColor = color;
        mAxisLinePaint.setColor(mAxisLineColor);
        mTextPaint.setColor(mAxisLineColor);
        invalidate();
    }

    // 设置白天温度折线颜色
    public void setDayLineColor(int color) {
        mDayLineColor = color;
        mDayLinePaint.setColor(mDayLineColor);
        invalidate();
    }

    // 设置夜间温度折线颜色
    public void setNightLineColor(int color) {
        mNightLineColor = color;
        mNightLinePaint.setColor(mNightLineColor);
        invalidate();
    }

    // 设置坐标轴线宽度
    public void setAxisLineWidth(int width) {
        mAxisLineWidth = width;
        mAxisLinePaint.setStrokeWidth(mAxisLineWidth);
        invalidate();
    }

    // 设置温度折线宽度
    public void setLineStrokeWidth(int width) {
        mLineStrokeWidth = width;
        mDayLinePaint.setStrokeWidth(mLineStrokeWidth);
        mNightLinePaint.setStrokeWidth(mLineStrokeWidth);
        invalidate();
    }

    // 设置字体大小
    public void setTextSize(int size) {
        mTextSize = size;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    // 处理传入的温度数据，计算温度数据对应坐标系中的点
    private Point[] calculateTemperaturePoints(int[] temperatures, int yOffset) {
        Point[] points = new Point[temperatures.length];
        int interval = getWidth() / (temperatures.length - 1);

        for (int i = 0; i < temperatures.length; i++) {
            int x = i * interval;
            int y = (int) ((1 - (temperatures[i] - yOffset) * 1.0 / (getHeight() * 0.6 - yOffset)) * getHeight() * 0.6);
            points[i] = new Point(x, y);
        }

        return points;
    }

    // 清空温度数据
    public void clearTemperatureData() {
        initData();
        invalidate();
    }

    // 设置是否可以拖动
    public void setIsDraggable(boolean isDraggable) {
        mIsDraggable = isDraggable;
    }

    // 动态添加白天温度值
    public void addDayTemperature(int temperature) {
        int[] newTemperatures = Arrays.copyOf(DAY_TEMPERATURES, DAY_TEMPERATURES.length + 1);
        newTemperatures[newTemperatures.length - 1] = temperature;
        DAY_TEMPERATURES = newTemperatures;
        mDayPoints = calculateTemperaturePoints(DAY_TEMPERATURES, 16);
        invalidate();
    }

    // 动态添加夜间温度值
    public void addNightTemperature(int temperature) {
        int[] newTemperatures = Arrays.copyOf(NIGHT_TEMPERATURES, NIGHT_TEMPERATURES.length + 1);
        newTemperatures[newTemperatures.length - 1] = temperature;
        NIGHT_TEMPERATURES = newTemperatures;
        mNightPoints = calculateTemperaturePoints(NIGHT_TEMPERATURES, 14);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景
        canvas.drawColor(mBackgroundColor);

        // 绘制坐标轴
        canvas.drawLine(getPaddingLeft(), getHeight() * 0.7f, getWidth() - getPaddingRight(), getHeight() * 0.7f, mAxisLinePaint);
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getHeight() * 0.7f, mAxisLinePaint);

        // 绘制白天温度折线
        for (int i = 0; i < mDayPoints.length - 1; i++) {
            canvas.drawLine(mDayPoints[i].x, mDayPoints[i].y, mDayPoints[i + 1].x, mDayPoints[i + 1].y, mDayLinePaint);
        }

        // 绘制夜间温度折线
        for (int i = 0; i < mNightPoints.length - 1; i++) {
            canvas.drawLine(mNightPoints[i].x, mNightPoints[i].y, mNightPoints[i + 1].x, mNightPoints[i + 1].y, mNightLinePaint);
        }

        // 绘制温度数据
        for (int i = 0; i < mDayPoints.length; i++) {
            canvas.drawText(DAY_TEMPERATURES[i] + "°C", mDayPoints[i].x, mDayPoints[i].y - (mTextSize + 10), mTextPaint);
        }

        for (int i = 0; i < mNightPoints.length; i++) {
            canvas.drawText(NIGHT_TEMPERATURES[i] + "°C", mNightPoints[i].x, mNightPoints[i].y + (mTextSize + 10), mTextPaint);
        }

        // 绘制拖拽圆点
        if (mIsDraggable && mDraggingTemperature != 0) {
            int index = 0;
            for (int i = 0; i < DAY_TEMPERATURES.length; i++) {
                if (mDraggingTemperature == DAY_TEMPERATURES[i]) {
                    index = i;
                    break;
                }
            }

            int x = mDayPoints[index].x;
            int y = mDayPoints[index].y;
            canvas.drawCircle(x, y, mTextSize * 0.7f, mDayLinePaint);
            canvas.drawCircle(x, y, mTextSize * 0.7f, mAxisLinePaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = (int) (w * 0.8);
        setMeasuredDimension(w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsDraggable) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                if (x > getPaddingLeft() && x < getWidth() - getPaddingRight() && y > getPaddingTop() && y < getHeight() * 0.7f) {
                    int interval = getWidth() / (DAY_TEMPERATURES.length - 1);
                    int index = (int) ((x - getPaddingLeft()) / interval);

                    if (index >= DAY_TEMPERATURES.length) {
                        index = DAY_TEMPERATURES.length - 1;
                    }

                    mDraggingTemperature = DAY_TEMPERATURES[index];
                    invalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDraggingTemperature = 0;
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }
}

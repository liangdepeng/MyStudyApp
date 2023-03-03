package com.dpzz.lib_video;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dpzz.lib_base.base.BaseActivity;
import com.dpzz.lib_video.databinding.ActivityVideoBinding;

import xyz.doikki.videocontroller.StandardVideoController;

public class VideoActivity extends BaseActivity<ActivityVideoBinding> {

    public final static String VIDEO_URL = "VIDEO_URL";
    public final static String IMAGE_URL = "IMAGE_URL";
    private String videoUrl;
    private String imageUrl;

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            videoUrl = intent.getStringExtra(VIDEO_URL);
            imageUrl = intent.getStringExtra(IMAGE_URL);
        }
        if (TextUtils.isEmpty(videoUrl)) {
            Toast.makeText(this, "视频链接为空，请退出重试", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void initView() {
        Glide.with(this).load(imageUrl).into(mViewBinding.mvImage);
        // mViewBinding.videoView; "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        mViewBinding.videoView.setUrl(videoUrl); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        mViewBinding.videoView.setVideoController(controller); //设置控制器
        mViewBinding.videoView.start(); //开始播放，不调用则不自动播放

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mViewBinding.mvImage.setAlpha(value);
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewBinding.videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewBinding.videoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding.videoView.release();

    }

    @Override
    public void onBackPressed() {
        if (mViewBinding.videoView.onBackPressed())
            return;
        mViewBinding.mvImage.setAlpha(1f);
        super.onBackPressed();
    }
}

package com.dpzz.lib_video;

import com.dpzz.lib_base.base.BaseActivity;
import com.dpzz.lib_video.databinding.ActivityVideoBinding;

import xyz.doikki.videocontroller.StandardVideoController;

public class VideoActivity extends BaseActivity<ActivityVideoBinding> {

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
       // mViewBinding.videoView;
        mViewBinding.videoView.setUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        mViewBinding.videoView.setVideoController(controller); //设置控制器
        mViewBinding.videoView.start(); //开始播放，不调用则不自动播放
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
        super.onBackPressed();
    }
}

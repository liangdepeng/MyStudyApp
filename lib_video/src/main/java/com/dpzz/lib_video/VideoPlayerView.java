package com.dpzz.lib_video;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.dpzz.lib_video.databinding.VideoPlayerLayoutBinding;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 视频播放 VideoView
 * 【【【注意：一定要定死宽高】】】
 */
public class VideoPlayerView extends FrameLayout{

    private Context mContext;
    private VideoPlayerLayoutBinding layoutBinding;
    private StandardVideoController videoController;

    public VideoPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public VideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        layoutBinding = VideoPlayerLayoutBinding.inflate(LayoutInflater.from(mContext), this, true);
        // layoutBinding.videoView

        //   layoutBinding.videoView.setUrl(URL_VOD); //设置视频地址
        videoController = new StandardVideoController(mContext);
        videoController.addDefaultControlComponent("标题", false);
        layoutBinding.videoView.setVideoController(videoController); //设置控制器
        layoutBinding.videoView.addOnStateChangeListener(videoStateListener);
        //    layoutBinding.videoView.start(); //开始播放，不调用则不自动播放
    }

    public void addLifecycle(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (layoutBinding == null)
                    return;
                if (event == Lifecycle.Event.ON_PAUSE) {
                    layoutBinding.videoView.pause();
                } else if (event == Lifecycle.Event.ON_RESUME) {
                    layoutBinding.videoView.resume();
                } else if (event == Lifecycle.Event.ON_DESTROY) {
                    layoutBinding.videoView.release();

                }
            }
        });
    }

    public void startPlay(String url) {
        layoutBinding.videoView.setUrl(url); //设置视频地址
        layoutBinding.videoView.start(); //开始播放，不调用则不自动播放
    }

    private BaseVideoView.OnStateChangeListener videoStateListener = new BaseVideoView.OnStateChangeListener(){

        @Override
        public void onPlayerStateChanged(int playerState) {

        }

        @Override
        public void onPlayStateChanged(int playState) {
            Log.e("VideoPlayerView","播放状态   "+playState+" ");
            switch (playState) {
                case VideoView.STATE_IDLE:
                    // 默认状态
                    break;
                case VideoView.STATE_PREPARING:
                    // 播放准备中
                    break;
                case VideoView.STATE_PREPARED:
                    // 播放准备完成
                    break;
                case VideoView.STATE_PLAYING:
                    // 播放中
                    break;
                case VideoView.STATE_PAUSED:
                    // 播放暂停
                    break;
                case VideoView.STATE_PLAYBACK_COMPLETED:
                    // 播放已完成
                    break;
                case VideoView.STATE_BUFFERING:
                    // 视频加载缓冲中
                    break;
                case VideoView.STATE_BUFFERED:
                    // 缓冲完成
                    break;
                case VideoView.STATE_START_ABORT:
                    // 开始播放中止
                    break;
                case VideoView.STATE_ERROR:
                    // 播放异常
                    break;
                default:
                    break;
            }
        }

    };


}

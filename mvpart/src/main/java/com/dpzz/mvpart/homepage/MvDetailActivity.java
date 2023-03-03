package com.dpzz.mvpart.homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dpzz.lib_base.base.BaseActivity;
import com.dpzz.lib_base.http.contans.Constants;
import com.dpzz.lib_video.VideoActivity;
import com.dpzz.mvpart.R;
import com.dpzz.mvpart.bean.MvDetailBean;
import com.dpzz.mvpart.databinding.ActivityMovieDetailBinding;
import com.dpzz.mvpart.vm.MvDetailVm;

public class MvDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {

    private int videoViewWidth;
    private ValueAnimator animator;
    private int mvId;
    private MvDetailVm mvDetailVm;
    private String videoHighUrl;
    private String mainImageUrl;


    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mvId = intent.getIntExtra(Constants.MV_ID,0);
        }
    }

    @Override
    public void initView() {
        mViewBinding.videoView.addLifecycle(this);
        // mViewBinding.videoView.post(() -> videoViewWidth = mViewBinding.videoView.getWidth());




        mViewBinding.mainImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (animator==null){
             //   initAnimator();
                //  }

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MvDetailActivity.this, mViewBinding.mainImageIv, "mv_transition");
                Intent intent = new Intent(MvDetailActivity.this, VideoActivity.class);
                intent.putExtra(VideoActivity.VIDEO_URL,videoHighUrl);
                intent.putExtra(VideoActivity.IMAGE_URL,mainImageUrl);
                ActivityCompat.startActivity(MvDetailActivity.this,intent,optionsCompat.toBundle());
            }
        });

        mvDetailVm = new ViewModelProvider(this).get(MvDetailVm.class);

        mvDetailVm.requestMvDetail(mvId);
        mvDetailVm.detailData.observe(this, new Observer<MvDetailBean>() {
            @Override
            public void onChanged(MvDetailBean mvDetailBean) {
                setDataWithUI(mvDetailBean);
            }
        });
    }

    private void setDataWithUI(MvDetailBean mvDetailBean) {
        if (mvDetailBean==null)
            return;
        Glide.with(this)
                .load(mvDetailBean.basic.bigImage)
                .error(R.drawable.ic_launcher_background)
                .transform(new RoundedCorners(20))
                .into(mViewBinding.mainImageIv);

        videoHighUrl = mvDetailBean.basic.video.hightUrl;
        mainImageUrl = mvDetailBean.basic.bigImage;
    }

    private void initAnimator() {
        int offsetX = mViewBinding.videoView.getWidth() - mViewBinding.mainImageIv.getWidth();
        int imageIvWidth = mViewBinding.mainImageIv.getWidth();
        animator = ValueAnimator.ofInt(0, offsetX);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mViewBinding.mainImageIv.getLayoutParams();
                params.width = imageIvWidth + animatedValue;
                mViewBinding.mainImageIv.setLayoutParams(params);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mViewBinding.videoView.setVisibility(View.VISIBLE);
                mViewBinding.videoView.startPlay(videoHighUrl);
                mViewBinding.mainImageIv.setVisibility(View.GONE);
            }
        });
        animator.start();
    }
}

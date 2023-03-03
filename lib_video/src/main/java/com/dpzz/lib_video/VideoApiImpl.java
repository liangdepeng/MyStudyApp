package com.dpzz.lib_video;

import android.content.Context;
import android.content.Intent;

import com.dpzz.lib_base.jump.VideoApi;

public class VideoApiImpl implements VideoApi {

    @Override
    public void jumpCommonVideoActivity(Context context, String title, String videoUrl) {
        Intent intent = new Intent(context,VideoActivity.class);
        intent.putExtra(VideoActivity.VIDEO_URL,videoUrl);
        context.startActivity(intent);
    }
}

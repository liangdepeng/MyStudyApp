package com.dpzz.lib_base.jump;

import android.content.Context;
import android.content.Intent;

/**
 * 组件间通信，解耦依赖
 *
 * 页面跳转等
 */
public class JumpUtils {

    private final static String API_VIDEO_CLASS_STR = "com.dpzz.lib_video.VideoApiImpl";
    private final static String API_MV_PART_CLASS_STR = "com.dpzz.mvpart.MvPartApiImpl";

    public static void into(Context context, Class<?> aclass) {
        Intent intent = new Intent(context, aclass);
        context.startActivity(intent);
    }

    private static class VideoApiIns {
        private static final VideoApi INSTANCE = createInstance();

        private static VideoApi createInstance() {
            try {
                Class<?> clazz = Class.forName(API_VIDEO_CLASS_STR);
                return (VideoApi) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static VideoApi getVideoApi() {
        return VideoApiIns.INSTANCE;
    }


    private static class MvPartIns {
        private static final MvPartApi INSTANCE = createInstance();

        private static MvPartApi createInstance() {
            try {
                Class<?> clazz = Class.forName(API_MV_PART_CLASS_STR);
                return (MvPartApi) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static MvPartApi getMvPartApi() {
        return MvPartIns.INSTANCE;
    }

}

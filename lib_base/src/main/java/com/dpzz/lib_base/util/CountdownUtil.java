package com.dpzz.lib_base.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;

public class CountdownUtil {

    public final static String countdownTag1 = "splash_countdown";

    private static final HashMap<String, Animator> animatorHashMap = new HashMap<>(2);

    public static void start(int seconds, Callback callback, String tag) {
        ValueAnimator animator = ValueAnimator.ofInt(seconds, 0).setDuration(seconds * 1000L);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            int value = ((int) animation.getAnimatedValue());
            if (callback != null)
                callback.onValueUpdate(value);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null)
                    callback.onEnd(animation);
                animatorHashMap.remove(tag);
            }
        });
        animator.start();
        animatorHashMap.put(tag, animator);
    }

    public static void cancelIfCountdownIsRunning(String tag) {
        Animator animator = animatorHashMap.get(tag);
        if (animator != null) {
            animator.cancel();
            animatorHashMap.remove(tag);
        }
    }

    public interface Callback {
        void onValueUpdate(int value);

        void onEnd(Animator animation);

        void onStart(Animator animation);
    }

    public static class CallbackAdapter implements Callback {
        @Override
        public void onValueUpdate(int value) {
        }

        @Override
        public void onEnd(Animator animation) {
        }

        @Override
        public void onStart(Animator animation) {
        }
    }

}

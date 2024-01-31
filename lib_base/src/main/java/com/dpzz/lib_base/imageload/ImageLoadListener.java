package com.dpzz.lib_base.imageload;

import android.graphics.drawable.Drawable;

public interface ImageLoadListener {
    boolean onLoadFailed(Exception e);
    boolean onResourceReady(Drawable resource);
}

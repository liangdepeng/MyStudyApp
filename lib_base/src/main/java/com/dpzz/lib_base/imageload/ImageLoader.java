package com.dpzz.lib_base.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.net.URL;

public class ImageLoader {

    private final static class ImageLoadIns {
        private final static ImageLoader instance = new ImageLoader();
    }

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        return ImageLoadIns.instance;
    }

    public void loadImage(ImageView imageView, Object url) {
        if (imageView != null) {
            loadImage(imageView.getContext(), imageView, url);
        }
    }

    public void loadImageWithCorner(Context context, ImageView imageView, Object url, int cornerPixels) {
       loadImageWithCorner(context, imageView, url, cornerPixels,0);
    }

    public void loadImageWithCorner(Context context, ImageView imageView, Object url, int cornerPixels, @DrawableRes int errorResourceId) {
        loadImage(context, imageView, url, 0, errorResourceId, false, cornerPixels, null);
    }

    public void loadImageAsCircle(Context context, ImageView imageView, Object url, @DrawableRes int errorResourceId) {
        loadImage(context, imageView, url, 0, errorResourceId, true, 0, null);
    }

    public void loadImage(Context context, ImageView imageView, Object url) {
        loadImage(context, imageView, url, null);
    }

    public void loadImage(Context context, ImageView imageView, Object url, ImageLoadListener listener) {
        loadImage(context, imageView, url, 0, 0, false, 0, listener);
    }

    public void loadImage(Context context, ImageView imageView, Object url, @DrawableRes int errorResourceId) {
        loadImage(context, imageView, url, 0, errorResourceId, false, 0, null);
    }

    public void loadImage(Context context, ImageView imageView, Object url,
                          @DrawableRes int placeholderResourceId,
                          @DrawableRes int errorResourceId,
                          boolean isAsCircle, int cornerPixels, ImageLoadListener listener) {
        if (imageView == null) {
            return;
        }
        RequestBuilder<Drawable> requestBuilder = getGlideRequestBuilder(context, url);
        requestBuilder = requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (listener != null) {
                    return listener.onLoadFailed(e);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (listener != null) {
                    return listener.onResourceReady(resource);
                }
                return false;
            }
        }).placeholder(placeholderResourceId).error(errorResourceId);
        if (isAsCircle) {
            requestBuilder = requestBuilder.circleCrop();
        }
        if (cornerPixels != 0) {
            if (imageView.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                requestBuilder = requestBuilder.transform(new CenterCrop(), new RoundedCorners(cornerPixels));
            } else {
                requestBuilder = requestBuilder.transform(new RoundedCorners(cornerPixels));
            }
        }
        requestBuilder.into(imageView);
    }

    private RequestBuilder<Drawable> getGlideRequestBuilder(Context context, Object url) {
        RequestBuilder<Drawable> requestBuilder;
        if (url instanceof Uri) {
            requestBuilder = Glide.with(context).load((Uri) url);
        } else if (url instanceof File) {
            requestBuilder = Glide.with(context).load((File) url);
        } else if (url instanceof byte[]) {
            requestBuilder = Glide.with(context).load((byte[]) url);
        } else if (url instanceof Bitmap) {
            requestBuilder = Glide.with(context).load((Bitmap) url);
        } else if (url instanceof String) {
            requestBuilder = Glide.with(context).load((String) url);
        } else if (url instanceof Drawable) {
            requestBuilder = Glide.with(context).load((Drawable) url);
        } else if (url instanceof Integer) {
            requestBuilder = Glide.with(context).load((Integer) url);
        } else if (url instanceof URL) {
            requestBuilder = Glide.with(context).load((URL) url);
        } else {
            requestBuilder = Glide.with(context).load(url);
        }
        return requestBuilder;
    }
}

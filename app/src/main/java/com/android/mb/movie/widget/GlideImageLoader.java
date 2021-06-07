package com.android.mb.movie.widget;

import android.content.Context;
import android.widget.ImageView;

import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.utils.ImageUtils;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageUtils.loadImageUrlDark(imageView,((Advert)path).getCoverUrl());
    }
}

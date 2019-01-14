package com.android.mb.movie.utils;

import android.widget.ImageView;

import com.android.mb.movie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by cgy on 19/1/12.
 */

public class ProjectHelper {

    public static void loadImageUrl(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_avatar_default);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.ic_avatar_default)
                                .placeholder(R.mipmap.ic_avatar_default))
                .load(url)
                .into(imageView);
    }

}
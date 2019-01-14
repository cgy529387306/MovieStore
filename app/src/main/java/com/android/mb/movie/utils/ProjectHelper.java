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
        imageView.setImageResource(R.mipmap.bg_defaults);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.bg_defaults)
                                .placeholder(R.mipmap.bg_defaults))
                .load(url)
                .into(imageView);
    }

}
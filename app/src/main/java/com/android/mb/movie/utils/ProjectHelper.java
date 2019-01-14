package com.android.mb.movie.utils;

import android.widget.ImageView;

import com.android.mb.movie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

}
package com.android.mb.movie.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.android.mb.movie.video.LandLayoutVideo;

/**
 * Created by cgy on 19/4/23.
 */

public class MyLandLayoutVideo extends LandLayoutVideo {
    public MyLandLayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyLandLayoutVideo(Context context) {
        super(context);
    }

    public MyLandLayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (0.563*childWidthSize);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

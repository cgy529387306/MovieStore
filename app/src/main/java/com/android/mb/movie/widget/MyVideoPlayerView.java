package com.android.mb.movie.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by cgy on 19/4/23.
 */

public class MyVideoPlayerView extends StandardGSYVideoPlayer {

    public MyVideoPlayerView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoPlayerView(Context context) {
        super(context);
    }

    public MyVideoPlayerView(Context context, AttributeSet attrs) {
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

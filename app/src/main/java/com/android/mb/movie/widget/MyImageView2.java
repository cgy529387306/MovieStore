package com.android.mb.movie.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by cgy on 19/4/23.
 */

public class MyImageView2 extends android.support.v7.widget.AppCompatImageView {

    public MyImageView2(Context context) {
        super(context);
    }

    public MyImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (1.25*childWidthSize);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

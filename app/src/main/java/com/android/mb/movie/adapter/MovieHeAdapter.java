package com.android.mb.movie.adapter;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieHeAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {

    public MovieHeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video video) {
        helper.setText(R.id.tv_title,video.getName());
        ImageUtils.loadImageUrlDark(helper.getView(R.id.iv_cover),video.getCoverUrl1());
    }


}





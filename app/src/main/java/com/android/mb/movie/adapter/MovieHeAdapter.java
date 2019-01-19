package com.android.mb.movie.adapter;

import android.view.View;
import android.widget.GridView;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.CateVideo;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
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
        ProjectHelper.loadImageUrl(helper.getView(R.id.iv_cover),video.getCoverUrl());
    }


}





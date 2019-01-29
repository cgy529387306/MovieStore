package com.android.mb.movie.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.view.DetailActivity;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class MovieListAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {

    public MovieListAdapter(int layoutResId, @Nullable List<Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_play_count,item.getPlayCount()+"次播放");
        ProjectHelper.loadImageUrl(helper.getView(R.id.iv_cover), item.getCoverUrl());
        FlowTagLayout flowTagLayout = helper.getView(R.id.tagLayout);
    }
}

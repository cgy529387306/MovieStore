package com.android.mb.movie.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.CateVideo;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.DetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieRecycleAdapter extends BaseQuickAdapter<CateVideo, BaseViewHolder> {

    private MovieHeAdapter mAdapter;

    public MovieRecycleAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateVideo item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new MovieHeAdapter(R.layout.item_movie_h,item.getVideos());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoInfo",item.getVideos().get(position));
                NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
            }
        });
    }


}





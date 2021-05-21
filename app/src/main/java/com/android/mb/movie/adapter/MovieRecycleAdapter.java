package com.android.mb.movie.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.AuthorVideo;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.DetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieRecycleAdapter extends BaseQuickAdapter<AuthorVideo, BaseViewHolder> {

    private MovieHeAdapter mAdapter;

    private RecyclerView.RecycledViewPool mSharedPool = new RecyclerView.RecycledViewPool();

    public MovieRecycleAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthorVideo item) {
        helper.setText(R.id.tv_num,item.getVideoCount()+"部电影");
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_desc,item.getIntros());
        ImageUtils.displayAvatar(helper.getView(R.id.iv_avatar),item.getIcon());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager.setInitialPrefetchItemCount(4);//优化嵌套时预加载性能
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRecycledViewPool(mSharedPool);//共享对象池
        mAdapter = new MovieHeAdapter(R.layout.item_movie_h,item.getVideos());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("videoId",item.getVideos().get(position).getId());
                NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
            }
        });
    }


}





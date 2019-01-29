package com.android.mb.movie.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.MovieListAdapter;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.HistoryPresenter;
import com.android.mb.movie.presenter.LikePresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.interfaces.IHistoryView;
import com.android.mb.movie.view.interfaces.ILikeView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class LikeActivity extends BaseMvpActivity<LikePresenter,
        ILikeView> implements ILikeView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("我的喜欢");
    }

    @Override
    protected void bindViews() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getListFormServer();
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected LikePresenter createPresenter() {
        return new LikePresenter();
    }

    @Override
    public void getSuccess(VideoListData result) {
        if (result!=null){
            if (mCurrentPage == 1){
                //首页
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                if (result.isEnd()){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }else{
                if (Helper.isEmpty(result)){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    mAdapter.addData(result.getList());
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getLike(requestMap);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Video video = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoInfo",video);
        NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getListFormServer();
    }
}

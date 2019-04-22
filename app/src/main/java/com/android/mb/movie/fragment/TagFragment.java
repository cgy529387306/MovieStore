package com.android.mb.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.MovieListAdapter;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.TagPresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProgressDialogHelper;
import com.android.mb.movie.view.DetailActivity;
import com.android.mb.movie.view.interfaces.ITagView;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;
import com.android.mb.movie.widget.taglayout.OnTagClickListener;
import com.android.mb.movie.widget.taglayout.OnTagSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cgy on 16/7/18.
 */
public class TagFragment extends BaseMvpFragment<TagPresenter,ITagView> implements ITagView,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {
    private FlowTagLayout mTagHot;
    private TagAdapter<Tag> mHotAdapter;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private String mTags;
    @Override
    protected int getLayoutId() {
        return  R.layout.frg_tag;
    }

    @Override
    protected void bindViews(View view) {
        mTagHot = mRootView.findViewById(R.id.tagLayout);
        mHotAdapter = new TagAdapter<>(getActivity());
        mTagHot.setAdapter(mHotAdapter);
        mTagHot.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mTagHot.clearAllOption();

        mRefreshLayout =  mRootView.findViewById(R.id.refreshLayout);
        mRecyclerView =  mRootView.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext,MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableRefresh(false);
    }

    @Override
    protected void processLogic() {
        mPresenter.getTags();
        onRefresh(null);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mTagHot.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    Tag tag = (Tag) mTagHot.getAdapter().getItem(0);
                    mTags = tag.getId();
                    onRefresh(null);
                }
            }
        });
    }


    @Override
    protected TagPresenter createPresenter() {
        return new TagPresenter();
    }

    @Override
    public void getSuccess(List<Tag> result) {
        mHotAdapter.onlyAddAll(result);
        mTagHot.clearAllOption();
    }

    @Override
    public void querySuccess(VideoListData result) {
        if (result!=null){
            if (result.isEnd()){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (mCurrentPage == 1){
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
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
        requestMap.put("currentPage",1);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("tags", mTags);
        mPresenter.queryVideos(requestMap);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Video video = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoInfo",video);
        NavigationHelper.startActivity(mContext, DetailActivity.class,bundle,false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        showProgressDialog();
        getListFormServer();
    }


}

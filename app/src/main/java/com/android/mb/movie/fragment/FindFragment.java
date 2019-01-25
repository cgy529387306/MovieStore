package com.android.mb.movie.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.FindAdapter;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.FindPresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.SearchActivity;
import com.android.mb.movie.view.interfaces.IFindView;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by cgy on 16/7/18.
 */
public class FindFragment extends BaseMvpFragment<FindPresenter,IFindView> implements IFindView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private FindAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_find;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new FindAdapter(R.layout.item_test, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {
        getListFormServer();
    }

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.iv_search).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(FindAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position)!=null){
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_search){
            NavigationHelper.startActivity(getActivity(), SearchActivity.class,null,false);
        }
    }


    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize",ProjectConstants.PAGE_SIZE);
        mPresenter.getFindData(requestMap);
    }

    public boolean onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected FindPresenter createPresenter() {
        return new FindPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
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
}

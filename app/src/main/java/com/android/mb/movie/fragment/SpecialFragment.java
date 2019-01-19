package com.android.mb.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.CateAdapter;
import com.android.mb.movie.adapter.MovieRecycleAdapter;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.entity.HomeData;
import com.android.mb.movie.presenter.HomePresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IHomeView;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by cgy on 16/7/18.
 */
public class SpecialFragment extends BaseMvpFragment<HomePresenter,IHomeView> implements IHomeView, View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieRecycleAdapter mAdapter;
    private GridView mGridCate;
    @Override
    protected int getLayoutId() {
        return  R.layout.common_recycleview;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MovieRecycleAdapter(R.layout.item_movie_recycle, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_special_header, mRecyclerView, false);
        mGridCate = header.findViewById(R.id.gridCate);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic() {
        mPresenter.getHomeData();
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position)!=null){
            ToastHelper.showLongToast(mAdapter.getItem(position).getCateName());
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }



    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getHomeData();
    }

    @Override
    public void getHomeData(HomeData homeData) {
        if (homeData!=null && homeData.getVideoList()!=null){
            mRefreshLayout.finishRefresh();
            mAdapter.setNewData(homeData.getVideoList());
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            if (mGridCate!=null && Helper.isNotEmpty(homeData.getCateList())){
                mGridCate.setAdapter(new CateAdapter(getActivity(),homeData.getCateList()));
            }
        }
    }

}
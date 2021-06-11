package com.android.mb.movie.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.CateAdapter;
import com.android.mb.movie.adapter.MovieGridAdapter;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.entity.CateVideo;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.HomeData;
import com.android.mb.movie.presenter.HomePresenter;
import com.android.mb.movie.rxbus.RxBus;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.view.HistoryActivity;
import com.android.mb.movie.view.LoginActivity;
import com.android.mb.movie.view.SearchActivity;
import com.android.mb.movie.view.VideoListActivity;
import com.android.mb.movie.view.interfaces.IHomeView;
import com.android.mb.movie.widget.GlideImageLoader;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class MainFragment extends BaseMvpFragment<HomePresenter,IHomeView> implements IHomeView, OnBannerListener,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    private Banner mBanner;
    private GridView mGridCate;
    private List<Advert> mAdvertList;
    @Override
    protected int getLayoutId() {
        return  R.layout.frg_main;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new MovieGridAdapter(R.layout.item_movie_grid, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_movie_header, mRecyclerView, false);
        mBanner = header.findViewById(R.id.bannerView);
        mGridCate = header.findViewById(R.id.gridCate);
        mAdapter.addHeaderView(header);
    }

    @Override
    protected void processLogic() {
        mPresenter.getHomeData();
    }

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.tv_search).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_history).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mBanner.setOnBannerListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CateVideo cateVideo = mAdapter.getItem(position);
        if (cateVideo!=null){
            String name = cateVideo.getCateName();
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("cateId",cateVideo.getCateId());
            NavigationHelper.startActivity(getActivity(), VideoListActivity.class,bundle,false);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_history){
            if (CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), HistoryActivity.class,null,false);
            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.tv_search){
            NavigationHelper.startActivity(getActivity(), SearchActivity.class,null,false);
        }
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
            if (mBanner!=null && Helper.isNotEmpty(homeData.getAdvertList())){
                mAdvertList = homeData.getAdvertList();
                mBanner.setImageLoader(new GlideImageLoader());
                mBanner.setImages(mAdvertList);
                mBanner.start();
            }
            if (mGridCate!=null && Helper.isNotEmpty(homeData.getCateList())){
                mGridCate.setAdapter(new CateAdapter(getActivity(),homeData.getCateList()));
            }
        }
    }

    @Override
    public void OnBannerClick(int position) {
        if (Helper.isNotEmpty(mAdvertList) && mAdvertList.size()>position){
            Advert advert = mAdvertList.get(position);
            RxBus.getInstance().send(ProjectConstants.EVENT_VISIT_ADVERT,advert.getId());
            ProjectHelper.getToAdvert(advert,mContext);
        }
    }

}

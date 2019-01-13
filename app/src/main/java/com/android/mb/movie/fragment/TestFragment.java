package com.android.mb.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TestAdapter;
import com.android.mb.movie.base.BaseFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.BannerItem;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.LoginActivity;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class TestFragment extends BaseFragment implements View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener{

    private int mState;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    public static Fragment getInstance(int state) {
        Fragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_main;
    }

    @Override
    protected void bindViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new TestAdapter(R.layout.item_test, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

        //添加Header
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_movie_header, mRecyclerView, false);
        Banner banner = (Banner) header;
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(getImageUrls());
        banner.start();
        mAdapter.addHeaderView(banner);
    }

    private List<BannerItem> getImageUrls(){
        List<BannerItem> dataList = new ArrayList<>();
        dataList.add(new BannerItem("最后的骑士", ProjectConstants.TEST_IMAGE_URL));
        dataList.add(new BannerItem("三生三世十里桃花", ProjectConstants.TEST_IMAGE_URL));
        dataList.add(new BannerItem("豆福传", ProjectConstants.TEST_IMAGE_URL));
        return dataList;
    }

    @Override
    protected void processLogic() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mState = bundle.getInt("state");
        }
        getListFormServer();
    }

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.btn_history).setOnClickListener(this);
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
                    if (GSYVideoManager.instance().getPlayTag().equals(TestAdapter.TAG)
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
            ToastHelper.showLongToast(mAdapter.getItem(position));
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_history){
            NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
        }
    }


    private void getListFormServer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> result = new ArrayList<>();
                if(mCurrentPage==1){
                    mAdapter.getData().clear();
                }
                int i = mAdapter.getItemCount();
                int j = mAdapter.getItemCount()+ProjectConstants.PAGE_SIZE;
                for(;i<j;i++){
                    result.add("页面 "+mState+",第"+i+"项目");
                }
                if (mCurrentPage == 1){
                    //首页
                    mRefreshLayout.finishRefresh();
                    mAdapter.setNewData(result);
                    mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                    if (result.size()< ProjectConstants.PAGE_SIZE){
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }else{
                    if (Helper.isEmpty(result)){
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }else{
                        mAdapter.addData(result);
                        mRefreshLayout.finishLoadMore();
                    }
                }
            }
        },1500);
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
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getListFormServer();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ProjectHelper.loadImageUrl(imageView,((BannerItem)path).imageUrl);
        }
    }
}

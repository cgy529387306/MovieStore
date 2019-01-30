package com.android.mb.movie.view;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mb.movie.R;
import com.android.mb.movie.adapter.CommentAdapter;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.presenter.DetailPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.video.LandLayoutVideo;
import com.android.mb.movie.video.listener.AppBarStateChangeListener;
import com.android.mb.movie.view.interfaces.IDetailView;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CollapsingToolbarLayout的播放页面
 * 额，有点懒，细节上没处理
 */
public class DetailActivity extends BaseMvpActivity<DetailPresenter,IDetailView> implements IDetailView , View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private boolean mIsPlay;
    private boolean mIsPause;
    private boolean mIsSmall;

    private OrientationUtils mOrientationUtils;
    private LandLayoutVideo mDetailPlayer;
    private AppBarLayout mAppBarLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarStateChangeListener.State curState;
    private Video mVideoInfo;
    private TextView mTvPlayTimes;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private int mCurrentPage = 1;
    private ImageView mBtnFavor,mBtnDownload,mBtnShare;
    private EditText mEtContent;

    @Override
    public void onBackPressed() {

        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        mIsPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        mAppBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);
        super.onResume();
        mIsPause = false;
    }

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    protected void onDestroy() {
        AppHelper.hideSoftInputFromWindow(mEtContent);
        if (mIsPlay) {
            getCurPlay().release();
        }
        if (mOrientationUtils != null)
            mOrientationUtils.releaseListener();
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (mIsPlay && !mIsPause) {
            mDetailPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils, true, true);
        }
    }


    @Override
    protected void loadIntent() {
        mVideoInfo = (Video) getIntent().getSerializableExtra("videoInfo");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mDetailPlayer = findViewById(R.id.detail_player);
        mCoordinatorLayout = findViewById(R.id.root_layout);
        toolbar.setNavigationIcon(R.mipmap.ic_video_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarLayout = findViewById(R.id.toolbar_layout);
        mToolbarLayout.setTitle(mVideoInfo.getName());
        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableRefresh(false);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommentAdapter(R.layout.item_comment, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
        mBtnFavor = findViewById(R.id.btn_favor);
        mBtnDownload = findViewById(R.id.btn_download);
        mBtnShare = findViewById(R.id.btn_share);
        mEtContent = findViewById(R.id.et_content);
        mTvPlayTimes = findViewById(R.id.tv_times);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
        getComments();
    }

    @Override
    protected void setListener() {
//        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mBtnFavor.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
    }

    private void initData(){
        //增加封面
        ImageView imageView = new ImageView(this);
        ProjectHelper.loadImageUrl(imageView,mVideoInfo.getCoverUrl());
        resolveNormalVideoUI();
        mTvPlayTimes.setText(String.format(getString(R.string.play_times_pre), mVideoInfo.getPlayCount()));
        //外部辅助的旋转，帮助全屏
        mOrientationUtils = new OrientationUtils(this, mDetailPlayer);
        //初始化不打开外部的旋转
        mOrientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(mVideoInfo.getVideoUrl())
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
                .setVideoAllCallBack(new GSYSampleCallBack() {

                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        mOrientationUtils.setEnable(true);
                        mIsPlay = true;
                        submitWatch();
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (mOrientationUtils != null) {
                            mOrientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (mOrientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            mOrientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .build(mDetailPlayer);

        mDetailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                mOrientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mDetailPlayer.startWindowFullscreen(DetailActivity.this, true, true);
            }
        });

        mDetailPlayer.setLinkScroll(true);
    }

    private void resolveNormalVideoUI() {
        //增加title
        mDetailPlayer.getTitleTextView().setVisibility(View.GONE);
        mDetailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (mDetailPlayer.getFullWindowPlayer() != null) {
            return mDetailPlayer.getFullWindowPlayer();
        }
        return mDetailPlayer;
    }

    AppBarStateChangeListener appBarStateChangeListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State
        state) {
            if (state == AppBarStateChangeListener.State.EXPANDED) {
                //展开状态
                curState = state;
                mToolbarLayout.setTitle("");
            } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                //折叠状态
                //如果是小窗口就不需要处理
                mToolbarLayout.setTitle(mVideoInfo.getName());
                if (!mIsSmall && mIsPlay) {
                    mIsSmall = true;
                    int size = CommonUtil.dip2px(DetailActivity.this, 150);
                    mDetailPlayer.showSmallVideo(new Point(size, size), true, true);
                    mOrientationUtils.setEnable(false);
                }
                curState = state;
            } else {
                if (curState == AppBarStateChangeListener.State.COLLAPSED) {
                    //由折叠变为中间状态
                    mToolbarLayout.setTitle("");
                    if (mIsSmall) {
                        mIsSmall = false;
                        mOrientationUtils.setEnable(true);
                        //必须
                        mDetailPlayer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDetailPlayer.hideSmallVideo();
                            }
                        }, 50);
                    }
                }
                curState = state;
                //中间状态
            }
        }
    };


    @Override
    public void comment(Object result) {
        mEtContent.setText("");
        AppHelper.hideSoftInputFromWindow(mEtContent);
        ToastHelper.showToast("评论成功");
        getComments();
    }

    @Override
    public void praise(Object result) {
        ToastHelper.showToast("收藏成功");
    }

    @Override
    public void watch(Object result) {

    }

    @Override
    public void getVideoComments(CommentListData result) {
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

    private void submitComment(String content){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoId", mVideoInfo.getId());
        requestMap.put("content", content);
        mPresenter.comment(requestMap);
    }

    private void submitPraise(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoId", mVideoInfo.getId());
        mPresenter.praise(requestMap);
    }

    private void submitWatch(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoId", mVideoInfo.getId());
        mPresenter.watch(requestMap);
    }

    private void getComments(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoId", mVideoInfo.getId());
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getVideoComments(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getComments();

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getComments();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_favor){
            submitPraise();
        }else if (id == R.id.btn_share){
            //TODO

        }else if (id == R.id.tv_confirm){
            String content = mEtContent.getText().toString().trim();
            if (Helper.isEmpty(content)){
               ToastHelper.showToast("请输入评论内容");
            }else{
                submitComment(content);
            }
        }
    }

}

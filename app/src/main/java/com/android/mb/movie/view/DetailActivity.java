package com.android.mb.movie.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.CommentAdapter;
import com.android.mb.movie.adapter.MovieListAdapter;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.AdData;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoData;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.fragment.AdvertDialogFragment;
import com.android.mb.movie.presenter.DetailPresenter;
import com.android.mb.movie.rxbus.Events;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.PreferencesHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.video.LandLayoutVideo;
import com.android.mb.movie.video.listener.AppBarStateChangeListener;
import com.android.mb.movie.view.interfaces.IDetailView;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.android.mb.movie.widget.MyImageView1;
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
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
    private LandLayoutVideo mAdvertPlayer;
    private AppBarLayout mAppBarLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarStateChangeListener.State curState;
    private Video mVideoInfo;
    private String mVideoId;
    private TextView mTvPlayTimes;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView,mMovieRecyclerView;
    private CommentAdapter mAdapter;
    private int mCurrentPage = 1;
    private ImageView mBtnFavor,mBtnDownload,mBtnShare;
    private EditText mEtContent;
    private ImageView mIvAdvert;
    private MovieListAdapter mMovieListAdapter;
    private TextView mTvSkip, mTvTitle;
    private MyCountDownTimer mCountDownTimer;
    private Toolbar mToolBar;

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
        mVideoId = getIntent().getStringExtra("videoId");
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
        mToolBar = findViewById(R.id.toolbar);

        mDetailPlayer = findViewById(R.id.detail_player);
        mAdvertPlayer = findViewById(R.id.advert_player);
        mCoordinatorLayout = findViewById(R.id.root_layout);
        mToolBar.setNavigationIcon(R.mipmap.ic_video_back);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarLayout = findViewById(R.id.toolbar_layout);
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
        mTvPlayTimes.setFocusable(true);
        mTvPlayTimes.setFocusableInTouchMode(true);
        mTvPlayTimes.requestFocus();
        mTvSkip = findViewById(R.id.tv_skip);
        mTvTitle = findViewById(R.id.tv_name);

        View header = LayoutInflater.from(mContext).inflate(R.layout.header_detail, mRecyclerView, false);
        mIvAdvert = header.findViewById(R.id.iv_advert);
        mMovieRecyclerView = header.findViewById(R.id.rv_recomment);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.addHeaderView(header);

        mMovieListAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
        mMovieListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Video video = mMovieListAdapter.getItem(position);
                mVideoId = video.getId();
                getVideoDetail();
                getAdvert();
                getRecommendVideo();
            }
        });
        mMovieRecyclerView.setAdapter(mMovieListAdapter);
        regiestEvent(ProjectConstants.EVENT_BACK, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (Helper.isNotEmpty(mVideoId)){
            getVideoDetail();
        }else if (Helper.isNotEmpty(mVideoInfo)){
            initData();
        }
//        getComments();
        getAdvert();
        getRecommendVideo();
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
        if (mVideoInfo==null){
            return;
        }
        mBtnFavor.setImageResource(mVideoInfo.getIsPraise() ? R.mipmap.favor_press:R.mipmap.favor_nopress);

        //增加封面
        MyImageView1 imageView = new MyImageView1(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        ImageUtils.loadImageUrlDark(imageView,mVideoInfo.getCoverUrl1());
        resolveNormalVideoUI();
        mTvTitle.setText(mVideoInfo.getName());
        mTvPlayTimes.setText(String.format(getString(R.string.play_times_pre), mVideoInfo.getPlayCount()));
        //外部辅助的旋转，帮助全屏
        mOrientationUtils = new OrientationUtils(this, mDetailPlayer);
        //初始化不打开外部的旋转
        mOrientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setShrinkImageRes(R.drawable.video_shrink)
                .setEnlargeImageRes(R.drawable.video_enlarge)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(mVideoInfo.getVideoUrl())
                .setCacheWithPlay(true)
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
                        int remainCount = PreferencesHelper.getInstance().getInt(ProjectConstants.KEY_REMAIN_COUNT,0);
                        if(remainCount <= 0){
                            GSYVideoManager.instance().stop();
                            ToastHelper.showToast("今日观影次数已经用完，邀请好友可增加观影次数");
                            NavigationHelper.startActivity((Activity) mContext, InviteActivity.class,null,false);
                        }else{
                            submitWatch();
                        }
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
                mDetailPlayer.startWindowFullscreen(DetailActivity.this, false, true);
            }
        });

        mDetailPlayer.setLinkScroll(true);
        mDetailPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
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
                mToolbarLayout.setTitle(mVideoInfo==null?"":mVideoInfo.getName());
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
    public void getVideoDetail(VideoData result) {
        if (result!=null && result.getVideo()!=null){
            mVideoInfo = result.getVideo();
            initData();
        }
    }

    @Override
    public void comment(Object result) {
        mEtContent.setText("");
        AppHelper.hideSoftInputFromWindow(mEtContent);
        ToastHelper.showToast("评论成功");
        getComments();
    }

    @Override
    public void praise(Object result) {
        ToastHelper.showToast("点赞成功");
        sendMsg(ProjectConstants.EVENT_GET_EXTRA_DATA,null);
        getVideoDetail();
    }

    @Override
    public void watch(Object result) {
        sendMsg(ProjectConstants.EVENT_GET_EXTRA_DATA,null);
    }

    @Override
    public void getVideoComments(CommentListData result) {
        if (result!=null){
            if (result.isEnd()){
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            if (mCurrentPage == 1){
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
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

    private void getVideoDetail(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoId", mVideoId);
        mPresenter.getVideoDetail(requestMap);
    }

    private void submitPraise(){
        if (mVideoInfo!=null){
            if (mVideoInfo.getIsPraise()) {
                delLike();
            }else {
                Map<String,Object> requestMap = new HashMap<>();
                requestMap.put("videoId", mVideoInfo.getId());
                mPresenter.praise(requestMap);
            }
        }
    }

    public void delLike() {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("videoIds", mVideoInfo.getId());
        Observable observable = ScheduleMethods.getInstance().delLike(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object result) {
                ToastHelper.showToast("取消点赞成功");
                sendMsg(ProjectConstants.EVENT_GET_EXTRA_DATA,null);
                getVideoDetail();
            }
        });
    }

    private void submitWatch(){
        if (mVideoInfo!=null){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("videoId", mVideoInfo.getId());
            mPresenter.watch(requestMap);
        }
    }

    private void getComments(){
        Map<String,Object> requestMap = new HashMap<>();
        if (Helper.isNotEmpty(mVideoId)){
            requestMap.put("videoId", mVideoId);
        }else if (Helper.isNotEmpty(mVideoInfo)){
            requestMap.put("videoId", mVideoInfo.getId());
        }
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
            if (CurrentUser.getInstance().isLogin()){
                submitPraise();
            }else{
                NavigationHelper.startActivity(mContext, LoginActivity.class,null,false);
            }
        }else if (id == R.id.btn_share){
//            doShare(mVideoInfo);
            NavigationHelper.startActivity(mContext, InviteActivity.class,null,false);
        }else if (id == R.id.tv_confirm){
            if (CurrentUser.getInstance().isLogin()){
                String content = mEtContent.getText().toString().trim();
                if (Helper.isEmpty(content)){
                    ToastHelper.showToast("请输入评论内容");
                }else{
                    submitComment(content);
                }
            }else{
                NavigationHelper.startActivity(mContext, LoginActivity.class,null,false);
            }
        }
    }

    private void doShare(Video video){
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, video.getName()+":https://www.baidu.com");//添加分享内容
        startActivity(share_intent);
    }

    private void getAdvert(){
        Observable observable = ScheduleMethods.getInstance().getAdvert();
        toSubscribe(observable,  new Subscriber<AdData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onNext(AdData result) {
                if (Helper.isNotEmpty(result.getVideoDetailAdvert())){
                    Advert advert = result.getVideoDetailAdvert().get(0);
                    ImageUtils.loadImageUrlLight(mIvAdvert,advert.getCoverUrl());
                    mIvAdvert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ProjectHelper.getToAdvert(advert,mContext);
                        }
                    });
                }
                if (Helper.isNotEmpty(result.getVideoPrePlayAdvert())){
                    Advert advert = result.getVideoPrePlayAdvert().get(0);
                    mCountDownTimer = new MyCountDownTimer(advert.getSeconds()*1000, 1000);
                    mCountDownTimer.start();
                    playAdvert(advert.getCoverUrl());
                }
            }
        });
    }

    private void playAdvert(String videoUrl){
        mTvSkip.setVisibility(View.VISIBLE);
        mAdvertPlayer.setVisibility(View.VISIBLE);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setIsTouchWiget(true)
                .setIsTouchWigetFull(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(videoUrl)
                .setCacheWithPlay(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        mAdvertPlayer.setVisibility(View.GONE);
                        mTvSkip.setVisibility(View.GONE);
                        mDetailPlayer.startPlayLogic();
                    }
                })
                .build(mAdvertPlayer);
        mAdvertPlayer.getBackButton().setVisibility(View.GONE);
        mAdvertPlayer.startPlayLogic();
    }

    private void getRecommendVideo(){
        Observable observable = ScheduleMethods.getInstance().getRecommendVideo();
        toSubscribe(observable,  new Subscriber<List<Video>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onNext(List<Video> result) {
                if (Helper.isNotEmpty(result)){
                    mMovieListAdapter.setNewData(result);
                }
            }
        });
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            mTvSkip.setVisibility(View.GONE);
            mAdvertPlayer.setVisibility(View.GONE);
        }

        public void onTick(long millisUntilFinished) {
            mTvSkip.setText(String.valueOf(millisUntilFinished / 1000));
        }

    }

}

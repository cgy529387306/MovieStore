package com.android.mb.movie.view;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.video.LandLayoutVideo;
import com.android.mb.movie.video.listener.AppBarStateChangeListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

/**
 * CollapsingToolbarLayout的播放页面
 * 额，有点懒，细节上没处理
 */
public class ScrollingActivity extends AppCompatActivity {

    private boolean mIsPlay;
    private boolean mIsPause;
    private boolean mIsSmall;

    private OrientationUtils mOrientationUtils;
    private LandLayoutVideo mDetailPlayer;
    private AppBarLayout mAppBarLayout;
    private FloatingActionButton mTab;
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarStateChangeListener.State curState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initView();

        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

        //增加封面
        ImageView imageView = new ImageView(this);
        ProjectHelper.loadImageUrl(imageView,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547199470477&di=f9ebd7e75737cb87a785eca308b70a80&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb151f8198618367a9662f4f52d738bd4b31ce52b.jpg");
        resolveNormalVideoUI();

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
                .setUrl(url)
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
                        mCoordinatorLayout.removeView(mTab);
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
                mDetailPlayer.startWindowFullscreen(ScrollingActivity.this, true, true);
            }
        });

        mDetailPlayer.setLinkScroll(true);
    }

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
    protected void onDestroy() {
        super.onDestroy();
        if (mIsPlay) {
            getCurPlay().release();
        }
        if (mOrientationUtils != null)
            mOrientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (mIsPlay && !mIsPause) {
            mDetailPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils, true, true);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDetailPlayer = (LandLayoutVideo) findViewById(R.id.detail_player);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_layout);

        setSupportActionBar(toolbar);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mToolbarLayout.setTitle(getTitle());

        mTab = (FloatingActionButton) findViewById(R.id.fab);
        mTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailPlayer.startPlayLogic();
                mCoordinatorLayout.removeView(mTab);
            }
        });

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);
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
                mToolbarLayout.setTitle("Title");
                if (!mIsSmall && mIsPlay) {
                    mIsSmall = true;
                    int size = CommonUtil.dip2px(ScrollingActivity.this, 150);
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



}

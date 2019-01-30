package com.android.mb.movie.adapter;

import android.view.View;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.ProjectHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class FindAdapter extends BaseQuickAdapter<Video, BaseViewHolder>{
    public final static String TAG = "RecyclerViewList";

    private GSYVideoOptionBuilder mVideoOptionBuilder;

    private OnOperateListener mOperateListener;

    public void setOperateListener(OnOperateListener operateListener) {
        mOperateListener = operateListener;
    }

    public interface OnOperateListener{
        void onPlayListener(Video video);

        void onFavor(Video video);

        void onDownload(Video video);

        void onShare(Video video);
    }

    public FindAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        mVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setOnClickListener(R.id.btn_favor,new MyOnClickListener(item));
        helper.setOnClickListener(R.id.btn_download,new MyOnClickListener(item));
        helper.setOnClickListener(R.id.btn_share,new MyOnClickListener(item));
        helper.setText(R.id.tv_times,String.format(mContext.getString(R.string.play_times_pre), item.getPlayCount()));
        StandardGSYVideoPlayer videoPlayer = helper.getView(R.id.video_player);
        ImageView imageView = new ImageView(mContext);
        ProjectHelper.loadImageUrl(imageView, item.getCoverUrl());
        mVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(item.getVideoUrl())
                .setVideoTitle(item.getName())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(helper.getAdapterPosition())
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!videoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }
                        if (mOperateListener != null){
                            mOperateListener.onPlayListener(item);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(false);
                        videoPlayer.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                    }
                }).build(videoPlayer);


        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(mContext, true, true);
            }
        });
    }


    class MyOnClickListener implements View.OnClickListener{
        private Video mVideo;

        public MyOnClickListener(Video video) {
            mVideo = video;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_favor){
                if (mOperateListener != null){
                    mOperateListener.onFavor(mVideo);
                }
            }else if (id == R.id.btn_download){
                if (mOperateListener != null){
                    mOperateListener.onDownload(mVideo);
                }
            }else if (id == R.id.btn_share){
                if (mOperateListener != null){
                    mOperateListener.onShare(mVideo);
                }
            }
        }
    }



}





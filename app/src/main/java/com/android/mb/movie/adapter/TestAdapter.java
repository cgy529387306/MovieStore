package com.android.mb.movie.adapter;

import android.view.View;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public final static String TAG = "RecyclerViewList";

    private GSYVideoOptionBuilder mVideoOptionBuilder;

    public TestAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        mVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        StandardGSYVideoPlayer videoPlayer = helper.getView(R.id.video_player);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        loadCover(imageView,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547199470477&di=f9ebd7e75737cb87a785eca308b70a80&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb151f8198618367a9662f4f52d738bd4b31ce52b.jpg");
        mVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl("http://39.96.68.92:8080/upload/111.mp4")
                .setVideoTitle(item)
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

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
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

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_avatar_default);
        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.ic_avatar_default)
                                .placeholder(R.mipmap.ic_avatar_default))
                .load(url)
                .into(imageView);
    }


}





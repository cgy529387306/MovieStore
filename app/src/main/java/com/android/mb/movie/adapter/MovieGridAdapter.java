package com.android.mb.movie.adapter;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.utils.ProjectHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MovieGridAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_type,item);
        GridView gridView = helper.getView(R.id.gridMovie);
        gridView.setAdapter(new MovieAdapter(mContext,getTestData()));
    }

    public List<String> getTestData(){
        List<String> dataList = new ArrayList<>();
        for (int i=0; i<6; i++){
            dataList.add("速度与激情第"+i+"集");
        }
        return dataList;
    }


}





package com.android.mb.movie.adapter;

import android.widget.GridView;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.CateVideo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieGridAdapter extends BaseQuickAdapter<CateVideo, BaseViewHolder> {

    public MovieGridAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateVideo item) {
        helper.setText(R.id.tv_type,item.getCateName());
        GridView gridView = helper.getView(R.id.gridMovie);
        gridView.setAdapter(new MovieAdapter(mContext,item.getVideos()));
    }


}





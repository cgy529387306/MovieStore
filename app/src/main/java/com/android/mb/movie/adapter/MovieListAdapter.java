package com.android.mb.movie.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class MovieListAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {

    private boolean mIsCanEdit = false;

    public MovieListAdapter(int layoutResId, @Nullable List<Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_praise_count,String.format(mContext.getString(R.string.praise_times_pre), item.getPraiseCount()));
        helper.setText(R.id.tv_play_count,String.format(mContext.getString(R.string.play_times_pre), item.getPlayCount()));
        CheckBox checkBox = helper.getView(R.id.checkBox);
        checkBox.setVisibility(mIsCanEdit? View.VISIBLE:View.GONE);
        ImageUtils.loadImageUrlLight(helper.getView(R.id.iv_cover), item.getCoverUrl1());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelect(isChecked);
            }
        });

    }

    public void setCanEdit(boolean canEdit) {
        mIsCanEdit = canEdit;
        notifyDataSetChanged();
    }

}

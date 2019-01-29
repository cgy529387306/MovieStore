package com.android.mb.movie.adapter;

import android.support.annotation.Nullable;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Comment;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.tv_name,item.getUserId());
        helper.setText(R.id.tv_time, Helper.long2DateString(item.getCreateTime()));
        helper.setText(R.id.tv_content,item.getContent());
    }
}

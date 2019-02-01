package com.android.mb.movie.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.Category;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.VideoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class CateAdapter extends BaseAdapter{
    private Context mContext;
    private List<Category> mData = new ArrayList<Category>();
    public CateAdapter(Context context, List<Category> htList) {
        mContext = context;
        mData = htList;
    }

    public int getCount() {
        return mData==null?0:mData.size();
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public Category getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cate, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Category category = mData.get(position);
        viewHolder.tvTitle.setText(category.getCateName());
        ImageUtils.loadCircleImageUrl(viewHolder.ivCover,category.getCoverUrl());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = category.getCateName();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("cateId",category.getCateId());
                NavigationHelper.startActivity((Activity) mContext, VideoListActivity.class,bundle,false);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivCover;
        private TextView tvTitle;
    }

}

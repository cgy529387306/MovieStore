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
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class MovieAdapter extends BaseAdapter{
    private Context mContext;
    public List<Video> mData = new ArrayList<Video>();
    public MovieAdapter(Context context, List<Video> htList) {
        mContext = context;
        mData = htList;
    }

    public int getCount() {
        return mData==null?0:mData.size();
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public Video getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Video video = mData.get(position);
        viewHolder.tvTitle.setText(video.getName());
        ImageUtils.loadImageUrlDark(viewHolder.ivCover,video.getCoverUrl1());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("videoId",video.getId());
                NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
    }

}

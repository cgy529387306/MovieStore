package com.android.mb.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.db.Search;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.widget.taglayout.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;


    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        T t = mDataList.get(position);
        if (t instanceof String){
            String tag = (String) t;
            textView.setText(tag);
        }
        if (t instanceof Tag){
            Tag tag = (Tag) t;
            textView.setText(tag.getName());
        }
        if (t instanceof Search){
            Search search = (Search) t;
            textView.setText(search.getKeyWord());
        }
        return view;
    }

    public void onlyAddAll(List<T> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> dataList) {
        mDataList.clear();
        onlyAddAll(dataList);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }
}

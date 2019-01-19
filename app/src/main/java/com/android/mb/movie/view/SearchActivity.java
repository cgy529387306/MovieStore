package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseActivity;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener{


    private FlowTagLayout mTagHistory;
    private FlowTagLayout mTagHot;
    private TagAdapter<String> mHistoryAdapter;
    private TagAdapter<String> mHotAdapter;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTagHistory = (FlowTagLayout) findViewById(R.id.tagHistory);
        mTagHot = (FlowTagLayout) findViewById(R.id.tagHot);
        mHistoryAdapter = new TagAdapter<>(this);
        mTagHistory.setAdapter(mHistoryAdapter);

        mHotAdapter = new TagAdapter<>(this);
        mTagHot.setAdapter(mHotAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initHistoryData();
        initHotData();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel){
            finish();
        }else if (id == R.id.iv_delete){

        }
    }


    private void initHistoryData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("android");
        dataSource.add("安卓");
        dataSource.add("SDK源码");
        dataSource.add("IOS");
        dataSource.add("iPhone");
        dataSource.add("游戏");
        dataSource.add("fragment");
        dataSource.add("viewcontroller");
        dataSource.add("cocoachina");
        dataSource.add("移动研发工程师");
        dataSource.add("移动互联网");
        dataSource.add("高薪+期权");
        mHistoryAdapter.onlyAddAll(dataSource);
    }

    private void initHotData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("花边色");
        dataSource.add("深蓝色");
        dataSource.add("白色");
        dataSource.add("玫瑰红色");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("屎黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        mHotAdapter.onlyAddAll(dataSource);
    }



}

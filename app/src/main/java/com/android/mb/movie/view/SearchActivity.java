package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.db.GreenDaoManager;
import com.android.mb.movie.db.Search;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.greendao.SearchDao;
import com.android.mb.movie.presenter.SearchPresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.ISearchView;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchActivity extends BaseMvpActivity<SearchPresenter,ISearchView> implements ISearchView,View.OnClickListener{

    private EditText mEtSearch;
    private FlowTagLayout mTagHistory;
    private FlowTagLayout mTagHot;
    private TagAdapter<Search> mHistoryAdapter;
    private TagAdapter<String> mHotAdapter;
    private SearchDao mSearchDao;
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
        mSearchDao = GreenDaoManager.getInstance().getNewSession().getSearchDao();
        mEtSearch = findViewById(R.id.et_search);
        mTagHistory = findViewById(R.id.tagHistory);
        mTagHot = findViewById(R.id.tagHot);
        mHistoryAdapter = new TagAdapter<>(this);
        mTagHistory.setAdapter(mHistoryAdapter);

        mHotAdapter = new TagAdapter<>(this);
        mTagHot.setAdapter(mHotAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getKeyword();
        initHotData();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_delete).setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (Helper.isEmpty(mEtSearch.getText().toString())){
                        ToastHelper.showToast("请输入搜索内容");
                    }else {
                        addKeyword();
                        getKeyword();
                        getListFormServer();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel){
            finish();
        }else if (id == R.id.iv_delete){
            deleteKeyword();
        }
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

    private void getKeyword(){
        List<Search> searchList = mSearchDao.loadAll();
        Collections.reverse(searchList); // 倒序排列
        mHistoryAdapter.clearAndAddAll(searchList);
    }

    private void addKeyword(){
        String keyword = mEtSearch.getText().toString();
        List<Search> result = mSearchDao.queryBuilder().where(SearchDao.Properties.KeyWord.eq(keyword)).list();
        if (Helper.isEmpty(result)){
            Search search = new Search(null,keyword);
            mSearchDao.insert(search);
            List<Search> searchList = mSearchDao.loadAll();
            if (searchList!=null && searchList.size()>10){
                mSearchDao.delete(searchList.get(0));
            }
        }
    }

    private void deleteKeyword(){
        mSearchDao.deleteAll();
        getKeyword();
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void getSuccess(VideoListData result) {

    }

    private void getListFormServer(){
        String keyword = mEtSearch.getText().toString();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",1);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("keyword", keyword);
        mPresenter.queryVideos(requestMap);
    }
}

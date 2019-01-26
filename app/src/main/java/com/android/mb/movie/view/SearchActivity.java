package com.android.mb.movie.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.MovieListAdapter;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.db.GreenDaoManager;
import com.android.mb.movie.db.Search;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.greendao.SearchDao;
import com.android.mb.movie.presenter.SearchPresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.ISearchView;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;
import com.android.mb.movie.widget.taglayout.OnTagSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchActivity extends BaseMvpActivity<SearchPresenter,
        ISearchView> implements ISearchView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private EditText mEtSearch;
    private FlowTagLayout mTagHistory;
    private FlowTagLayout mTagHot;
    private TagAdapter<Search> mHistoryAdapter;
    private TagAdapter<String> mHotAdapter;
    private SearchDao mSearchDao;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayout mLlContent;
    private LinearLayoutManager mLinearLayoutManager;

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
        mLlContent = findViewById(R.id.ll_content);
        mEtSearch = findViewById(R.id.et_search);
        mTagHistory = findViewById(R.id.tagHistory);
        mTagHot = findViewById(R.id.tagHot);
        mTagHistory.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mTagHot.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);

        mHistoryAdapter = new TagAdapter<>(this);
        mTagHistory.setAdapter(mHistoryAdapter);

        mHotAdapter = new TagAdapter<>(this);
        mTagHot.setAdapter(mHotAdapter);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
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
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                String keyword = mEtSearch.getText().toString();
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (Helper.isEmpty(keyword)){
                        ToastHelper.showToast("请输入搜索内容");
                    }else {
                        addKeyword();
                        getKeyword();
                        mCurrentPage =1;
                        getListFormServer();
                    }
                    return true;
                }
                return false;
            }
        });
        mTagHistory.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (Helper.isNotEmpty(selectedList)){
                    int position = selectedList.get(0);
                    Search search = (Search) mTagHistory.getAdapter().getItem(position);
                    if (search!=null && Helper.isNotEmpty(search.getKeyWord())){
                        mEtSearch.setText(search.getKeyWord());
                        mEtSearch.setSelection(search.getKeyWord().length());
                        mCurrentPage = 1;
                        getListFormServer();
                    }
                }
            }
        });
        mTagHot.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (Helper.isNotEmpty(selectedList)){
                    int position = selectedList.get(0);
                    String keyword = (String) mTagHistory.getAdapter().getItem(position);
                    if (Helper.isNotEmpty(keyword)){
                        mEtSearch.setText(keyword);
                        mEtSearch.setSelection(keyword.length());
                        mCurrentPage = 1;
                        getListFormServer();
                    }
                }
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = mEtSearch.getText().toString();
                if (Helper.isEmpty(keyword)){
                    mLlContent.setVisibility(View.VISIBLE);
                    mRefreshLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        if (result!=null){
            if (mCurrentPage == 1){
                //首页
                mRefreshLayout.finishRefresh();
                mAdapter.setNewData(result.getList());
                mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                if (result.isEnd()){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }else{
                if (Helper.isEmpty(result)){
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    mAdapter.addData(result.getList());
                    mRefreshLayout.finishLoadMore();
                }
            }
        }
    }

    private void getListFormServer(){
        showProgressDialog();
        mLlContent.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
        String keyword = mEtSearch.getText().toString();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("currentPage",1);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        requestMap.put("keyword", keyword);
        mPresenter.queryVideos(requestMap);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Video video = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoInfo",video);
        NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getListFormServer();
    }
}

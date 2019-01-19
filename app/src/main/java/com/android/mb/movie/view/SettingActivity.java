package com.android.mb.movie.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.RegisterPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IRegisterView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class SettingActivity extends BaseMvpActivity<RegisterPresenter,IRegisterView> implements IRegisterView, View.OnClickListener{

    private TextView mTvCache;
    private TextView mTvVersion;
    private TextView mTvDate;
    private ImageView mIvAccountManager;
    private ImageView mIvAgreement;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mIvAccountManager = findViewById(R.id.iv_account_manager);
        mTvCache = findViewById(R.id.tv_cache);
        mTvVersion = findViewById(R.id.tv_version);
        mTvDate = findViewById(R.id.tv_date);
        mIvAccountManager = findViewById(R.id.iv_account_manager);
        mIvAgreement = findViewById(R.id.iv_agreement);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mIvAccountManager.setOnClickListener(this);
        mIvAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }else if (id == R.id.iv_account_manager){
            NavigationHelper.startActivity(this, AccountManagerActivity.class,null,false);
        }else if (id == R.id.iv_agreement){
        }
    }


    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void registerSuccess(UserBean result) {
        if (result!=null){
        }
    }

    @Override
    public void getSuccess(UserBean result) {

    }

}

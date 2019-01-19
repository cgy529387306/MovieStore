package com.android.mb.movie.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.RegisterPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IRegisterView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class AccountManagerActivity extends BaseMvpActivity<RegisterPresenter,IRegisterView> implements IRegisterView, View.OnClickListener{

    private TextView mTvHead;
    private TextView mTvNickname;
    private TextView mTvSex;
    private TextView mTvPhone;
    private TextView mTvUpdatePwd;
    private TextView mTvExit;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvHead = findViewById(R.id.tv_head);
        mTvNickname = findViewById(R.id.tv_nickname);
        mTvSex = findViewById(R.id.tv_sex);
        mTvPhone = findViewById(R.id.tv_phone);
        mTvUpdatePwd = findViewById(R.id.tv_updatePwd);
        mTvExit = findViewById(R.id.tv_exit);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mTvHead.setOnClickListener(this);
        mTvNickname.setOnClickListener(this);
        mTvSex.setOnClickListener(this);
        mTvPhone.setOnClickListener(this);
        mTvUpdatePwd.setOnClickListener(this);
        mTvExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }else if (id == R.id.tv_head){
        }else if (id == R.id.tv_nickname){
        }else if (id == R.id.tv_sex){
        }else if (id == R.id.tv_phone){
        }else if (id == R.id.tv_updatePwd){
        }else if (id == R.id.tv_exit){
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

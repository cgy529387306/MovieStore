package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseActivity;
import com.android.mb.movie.utils.NavigationHelper;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class AccountActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvNickname;
    private TextView mTvSex;
    private TextView mTvPhone;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initTitle() {
        setTitleText("账号管理");
    }

    @Override
    protected void bindViews() {
        mTvNickname = findViewById(R.id.tv_nickname);
        mTvSex = findViewById(R.id.tv_sex);
        mTvPhone = findViewById(R.id.tv_phone);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_head).setOnClickListener(this);
        findViewById(R.id.btn_nickname).setOnClickListener(this);
        findViewById(R.id.btn_sex).setOnClickListener(this);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_change_pwd).setOnClickListener(this);
        findViewById(R.id.tv_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_head){
        }else if (id == R.id.btn_nickname){
        }else if (id == R.id.btn_sex){
        }else if (id == R.id.btn_phone){
        }else if (id == R.id.btn_change_pwd){
        }else if (id == R.id.tv_exit){
        }
    }



}

package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Avatar;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.AccountPresenter;
import com.android.mb.movie.presenter.InvitePresenter;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IAccountView;
import com.android.mb.movie.view.interfaces.IInviteView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class InviteActivity extends BaseMvpActivity<InvitePresenter,IInviteView> implements IInviteView, View.OnClickListener{

    private TextView mTvInviteCode;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvInviteCode = findViewById(R.id.tv_invite_code);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getPromoCode();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back_black).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back_black){
            finish();
        }
    }



    @Override
    protected InvitePresenter createPresenter() {
        return new InvitePresenter();
    }

    @Override
    public void getSuccess(String result) {
        if (Helper.isNotEmpty(result)){
            mTvInviteCode.setText(result);
        }
    }
}

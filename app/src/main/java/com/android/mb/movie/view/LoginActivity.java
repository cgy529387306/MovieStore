package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.LoginData;
import com.android.mb.movie.presenter.LoginPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.ILoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter,ILoginView> implements ILoginView, View.OnClickListener{

    private TextView mTvLogin;
    private EditText mEtAccount;
    private EditText mEtPwd;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvLogin = findViewById(R.id.tv_login);
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_pwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_login){
            doLogin();
        }
    }

    private void doLogin(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        if (Helper.isEmpty(account)){
            showToastMessage("请输入用户名");
            return;
        }
        if (Helper.isEmpty(pwd)){
            showToastMessage("请输入密码");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("account",account);
        requestMap.put("password",pwd);
        mPresenter.userLogin(requestMap);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(LoginData result) {
        if (result!=null && result.getUserinfo()!=null){
            AppHelper.hideSoftInputFromWindow(mEtAccount);
            CurrentUser.getInstance().login(result.getUserinfo(),true);
            showToastMessage("登录成功");
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }




}

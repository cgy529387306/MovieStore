package com.android.mb.movie.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.LoginPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
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
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mEtAccount.addTextChangedListener(myTextWatcher);
        mEtPwd.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_login){
            doLogin();
        }else if (id == R.id.btn_back){
            finish();
        }else if (id == R.id.tv_forget_pwd){
            NavigationHelper.startActivity(mContext,ForgetPwdActivity.class,null,false);
        }else if (id == R.id.tv_register){
            NavigationHelper.startActivity(mContext,RegisterActivity.class,null,false);
        }
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String account = mEtAccount.getText().toString().trim();
            String password = mEtPwd.getText().toString().trim();
            if (Helper.isNotEmpty(account) && Helper.isNotEmpty(password)){
                mTvLogin.setEnabled(true);
                mTvLogin.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown));
            }else{
                mTvLogin.setEnabled(false);
                mTvLogin.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown_light));
            }
        }
    };

    private void doLogin(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
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
    public void loginSuccess(UserBean result) {
        if (result!=null ){
            AppHelper.hideSoftInputFromWindow(mEtAccount);
            CurrentUser.getInstance().login(result);
            showToastMessage("登录成功");
            sendMsg(ProjectConstants.EVENT_UPDATE_USER_INFO,null);
            finish();
        }
    }


}

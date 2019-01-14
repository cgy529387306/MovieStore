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
import com.android.mb.movie.entity.LoginData;
import com.android.mb.movie.presenter.LoginPresenter;
import com.android.mb.movie.presenter.RegisterPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.ILoginView;
import com.android.mb.movie.view.interfaces.IRegisterView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter,IRegisterView> implements IRegisterView, View.OnClickListener{

    private TextView mTvRegister;
    private EditText mEtAccount;
    private EditText mEtPwd;
    private EditText mEtCode;
    private Button mBtnGetCode;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvRegister = findViewById(R.id.tv_register);
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtCode = findViewById(R.id.et_code);
        mBtnGetCode = findViewById(R.id.btn_get_code);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mBtnGetCode.setOnClickListener(this);
        mEtAccount.addTextChangedListener(myTextWatcher);
        mEtPwd.addTextChangedListener(myTextWatcher);
        mEtCode.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_register){
            doRegister();
        }else if (id == R.id.btn_back){
            finish();
        }else if (id == R.id.tv_login){
            finish();
        }else if (id == R.id.btn_get_code){
            doGetCode();
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
            String code = mEtPwd.getText().toString().trim();
            if (Helper.isNotEmpty(account) && Helper.isNotEmpty(password) && Helper.isNotEmpty(code)){
                mTvRegister.setEnabled(true);
                mTvRegister.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown));
            }else{
                mTvRegister.setEnabled(false);
                mTvRegister.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown_light));
            }
        }
    };

    private void doGetCode(){
        String account = mEtAccount.getText().toString().trim();
        if (Helper.isEmpty(account) || !ProjectHelper.isMobiPhoneNum(account)) {
            ToastHelper.showToast("请输入正确的手机号码");
        }else {
            new TimeCount(60000, 1000).start();
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("phone",account);
            requestMap.put("type",0);
            mPresenter.getCode(requestMap);
        }
    }


    private void doRegister(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        if (Helper.isEmpty(account)){
            showToastMessage("请输入用户名");
            return;
        }
        if (Helper.isEmpty(pwd)){
            showToastMessage("请输入密码");
            return;
        }
        if (Helper.isEmpty(code)){
            showToastMessage("请输入验证码");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("account",account);
        requestMap.put("password",pwd);
        requestMap.put("code",code);
        mPresenter.userRegister(requestMap);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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


    @Override
    public void registerSuccess(LoginData result) {
        if (result!=null && result.getUserinfo()!=null){
            AppHelper.hideSoftInputFromWindow(mEtAccount);
            CurrentUser.getInstance().login(result.getUserinfo(),true);
            showToastMessage("注册成功");
        }
    }

    @Override
    public void getSuccess(LoginData result) {

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mBtnGetCode.setText("获取验证码");
            mBtnGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mBtnGetCode.setClickable(false);
            mBtnGetCode.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }
    }
}

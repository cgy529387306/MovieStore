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
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.ChangePwdPresenter;
import com.android.mb.movie.presenter.ForgetPwdPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IChangePwdView;
import com.android.mb.movie.view.interfaces.IForgetPwdView;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by cgy on 2018\8\20 0020.
 */

public class ChangePwdActivity extends BaseMvpActivity<ChangePwdPresenter,IChangePwdView> implements IChangePwdView, View.OnClickListener{

    private TextView mTvRegister;
    private EditText mEtOldPwd;
    private EditText mEtNewPwd;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvRegister = findViewById(R.id.tv_register);
        mEtOldPwd = findViewById(R.id.et_old_pwd);
        mEtNewPwd = findViewById(R.id.et_new_pwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mEtOldPwd.addTextChangedListener(myTextWatcher);
        mEtNewPwd.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_register){
            doRegister();
        }else if (id == R.id.btn_back){
            finish();
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
            String oldPwd = mEtOldPwd.getText().toString().trim();
            String newPwd = mEtNewPwd.getText().toString().trim();
            if (Helper.isNotEmpty(oldPwd) && Helper.isNotEmpty(newPwd)){
                mTvRegister.setEnabled(true);
                mTvRegister.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown));
            }else{
                mTvRegister.setEnabled(false);
                mTvRegister.setBackgroundColor(mContext.getResources().getColor(R.color.base_brown_light));
            }
        }
    };



    private void doRegister(){
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString().trim();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("oldPassword",oldPwd);
        requestMap.put("newPassword",newPwd);
        mPresenter.updatePassword(requestMap);
    }

    @Override
    protected ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    public void changeSuccess(Object result) {
        AppHelper.hideSoftInputFromWindow(mEtOldPwd);
        showToastMessage("修改成功");
        finish();
    }

}

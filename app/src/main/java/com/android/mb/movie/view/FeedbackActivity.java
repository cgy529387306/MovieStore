package com.android.mb.movie.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.presenter.ChangePwdPresenter;
import com.android.mb.movie.presenter.FeedbackPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IChangePwdView;
import com.android.mb.movie.view.interfaces.IFeedbackView;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈
 * Created by cgy on 2018\8\20 0020.
 */

public class FeedbackActivity extends BaseMvpActivity<FeedbackPresenter,IFeedbackView> implements IFeedbackView, View.OnClickListener{

    private EditText mEtContent;
    private TextView mTvRest;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initTitle() {
        setTitleText("意见反馈");
    }

    @Override
    protected void bindViews() {
        mEtContent = findViewById(R.id.et_content);
        mTvRest = findViewById(R.id.tv_rest);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        mEtContent.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_confirm){
            doConfirm();
        }
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable editable) {
            int restCount = 100 - mEtContent.getText().toString().length();
            mTvRest.setText(restCount+"/100");
        }
    };



    private void doConfirm(){
        String content = mEtContent.getText().toString().trim();
        if (Helper.isEmpty(content)){
            ToastHelper.showToast("请输入问题描述");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("content",content);
        mPresenter.feedback(requestMap);
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter();
    }


    @Override
    public void confirmSuccess(Object result) {
        ToastHelper.showToast("反馈成功");
        finish();
    }
}

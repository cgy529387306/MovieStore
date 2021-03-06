package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.interfaces.IForgetPwdPresenter;
import com.android.mb.movie.presenter.interfaces.IRegisterPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IForgetPwdView;
import com.android.mb.movie.view.interfaces.IRegisterView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class ForgetPwdPresenter extends BaseMvpPresenter<IForgetPwdView> implements IForgetPwdPresenter {


    @Override
    public void forgetPassword(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().forgetPassword(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null && !TextUtils.isEmpty(e.getMessage())){
                    mMvpView.showToastMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(Object result) {
                if (mMvpView!=null){
                    mMvpView.changeSuccess(result);
                }
            }
        });
    }

    @Override
    public void getCode(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getCode(requestMap);
        toSubscribe(observable,  new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null && !TextUtils.isEmpty(e.getMessage())){
                    mMvpView.showToastMessage(e.getMessage());
                }
            }

            @Override
            public void onNext(UserBean result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}

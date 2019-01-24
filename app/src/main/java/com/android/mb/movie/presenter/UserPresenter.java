package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.interfaces.ILoginPresenter;
import com.android.mb.movie.presenter.interfaces.IUserPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.ILoginView;
import com.android.mb.movie.view.interfaces.IUserView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class UserPresenter extends BaseMvpPresenter<IUserView> implements IUserPresenter {


    @Override
    public void getUserInfo(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getUserInfo(requestMap);
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

package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.presenter.interfaces.IChangePwdPresenter;
import com.android.mb.movie.presenter.interfaces.IFeedbackPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IChangePwdView;
import com.android.mb.movie.view.interfaces.IFeedbackView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class FeedbackPresenter extends BaseMvpPresenter<IFeedbackView> implements IFeedbackPresenter {

    @Override
    public void feedback(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().feedback(requestMap);
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
                    mMvpView.confirmSuccess(result);
                }
            }
        });
    }
}

package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.presenter.interfaces.IExtraPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IExtraView;

import rx.Observable;
import rx.Subscriber;

public class ExtraPresenter extends BaseMvpPresenter<IExtraView> implements IExtraPresenter{

    @Override
    public void getCountData() {
        Observable observable = ScheduleMethods.getInstance().getCountData();
        toSubscribe(observable,  new Subscriber<CountData>() {
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
            public void onNext(CountData result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }


}

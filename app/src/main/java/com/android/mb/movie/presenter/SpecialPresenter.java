package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.HomeData;
import com.android.mb.movie.entity.SpecialData;
import com.android.mb.movie.presenter.interfaces.IHomePresenter;
import com.android.mb.movie.presenter.interfaces.ISpecialPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IHomeView;
import com.android.mb.movie.view.interfaces.ISpecialView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class SpecialPresenter extends BaseMvpPresenter<ISpecialView> implements ISpecialPresenter {

    @Override
    public void getSpecialData() {
        Observable observable = ScheduleMethods.getInstance().getSpecialData();
        toSubscribe(observable,  new Subscriber<SpecialData>() {
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
            public void onNext(SpecialData result) {
                if (mMvpView!=null){
                    mMvpView.getSpecialData(result);
                }
            }
        });
    }
}

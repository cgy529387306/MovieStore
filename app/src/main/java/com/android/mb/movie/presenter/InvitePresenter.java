package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.presenter.interfaces.IExtraPresenter;
import com.android.mb.movie.presenter.interfaces.IInvitePresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.PreferencesHelper;
import com.android.mb.movie.view.interfaces.IExtraView;
import com.android.mb.movie.view.interfaces.IInviteView;

import rx.Observable;
import rx.Subscriber;

public class InvitePresenter extends BaseMvpPresenter<IInviteView> implements IInvitePresenter{

    @Override
    public void getPromoCode() {
        String key = ProjectConstants.KEY_INVITE_CODE + CurrentUser.getInstance().getUserid();
        String inviteCode = PreferencesHelper.getInstance().getString(key);
        if (mMvpView!=null && Helper.isNotEmpty(inviteCode)){
            mMvpView.getSuccess(inviteCode);
        }
        Observable observable = ScheduleMethods.getInstance().getPromoCode();
        toSubscribe(observable,  new Subscriber<String>() {
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
            public void onNext(String result) {
                if (mMvpView!=null){
                    PreferencesHelper.getInstance().putString(key,result);
                    mMvpView.getSuccess(result);
                }
            }
        });
    }


}

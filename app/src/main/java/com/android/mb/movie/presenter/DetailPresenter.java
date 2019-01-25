package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.interfaces.IDetailPresenter;
import com.android.mb.movie.presenter.interfaces.IFindPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IDetailView;
import com.android.mb.movie.view.interfaces.IFindView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class DetailPresenter extends BaseMvpPresenter<IDetailView> implements IDetailPresenter {


    @Override
    public void comment(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().comment(requestMap);
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
                    mMvpView.comment(result);
                }
            }
        });
    }

    @Override
    public void praise(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().praise(requestMap);
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
                    mMvpView.praise(result);
                }
            }
        });
    }

    @Override
    public void getVideoComments(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getVideoComments(requestMap);
        toSubscribe(observable,  new Subscriber<CommentListData>() {
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
            public void onNext(CommentListData result) {
                if (mMvpView!=null){
                    mMvpView.getVideoComments(result);
                }
            }
        });
    }
}

package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.interfaces.IFindPresenter;
import com.android.mb.movie.presenter.interfaces.ITagPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IFindView;
import com.android.mb.movie.view.interfaces.ITagView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class TagPresenter extends BaseMvpPresenter<ITagView> implements ITagPresenter {


    @Override
    public void getTags() {
        Observable observable = ScheduleMethods.getInstance().getTags();
        toSubscribe(observable,  new Subscriber<List<Tag>>() {
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
            public void onNext(List<Tag> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

    @Override
    public void queryVideos(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().queryVideos(requestMap);
        toSubscribe(observable,  new Subscriber<VideoListData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    if (!TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(VideoListData result) {
                if (mMvpView!=null){
                    mMvpView.querySuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }
}

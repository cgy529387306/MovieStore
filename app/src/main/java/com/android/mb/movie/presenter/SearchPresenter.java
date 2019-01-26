package com.android.mb.movie.presenter;

import android.text.TextUtils;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.presenter.interfaces.IFindPresenter;
import com.android.mb.movie.presenter.interfaces.ISearchPresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IFindView;
import com.android.mb.movie.view.interfaces.ISearchView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {


    @Override
    public void queryVideos(Map<String,Object> requestMap) {
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
                    mMvpView.getSuccess(result);
                    mMvpView.dismissProgressDialog();
                }
            }
        });
    }
}

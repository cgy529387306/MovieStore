package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.VersionBean;

public interface IExtraView extends BaseMvpView{
    void getSuccess(CountData result);

    void getQQSuccess(String result);

    void getVersionSuccess(VersionBean result);
}

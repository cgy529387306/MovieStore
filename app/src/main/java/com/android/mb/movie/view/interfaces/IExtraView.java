package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.CountData;

public interface IExtraView extends BaseMvpView{
    void getSuccess(CountData result);
}

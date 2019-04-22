package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.InviteBean;

public interface IInviteView extends BaseMvpView{
    void getSuccess(InviteBean result);
}

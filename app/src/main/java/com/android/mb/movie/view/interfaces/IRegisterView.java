package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.LoginData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IRegisterView extends BaseMvpView {
    void registerSuccess(LoginData result);

    void getSuccess(LoginData result);
}

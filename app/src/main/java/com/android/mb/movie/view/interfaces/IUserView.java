package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.UserBean;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IUserView extends BaseMvpView {
    void getSuccess(UserBean result);
}

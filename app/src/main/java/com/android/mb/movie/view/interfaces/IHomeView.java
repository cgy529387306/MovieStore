package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.HomeData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IHomeView extends BaseMvpView {
    void getHomeData(HomeData homeData);
}

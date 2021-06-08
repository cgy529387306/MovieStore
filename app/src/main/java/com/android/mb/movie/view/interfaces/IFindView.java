package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoListData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IFindView extends BaseMvpView {
    void getSuccess(VideoListData result);

    void praise(Object result);

    void delLike(Object result);

    void watch(Object result);
}

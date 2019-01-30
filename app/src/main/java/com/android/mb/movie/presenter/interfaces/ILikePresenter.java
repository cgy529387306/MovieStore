package com.android.mb.movie.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ILikePresenter {
    void getLike(Map<String, Object> requestMap);

    void delLike(Map<String, Object> requestMap);
}

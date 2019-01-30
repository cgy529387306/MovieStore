package com.android.mb.movie.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IFindPresenter {
    void getFindData(Map<String,Object> requestMap);

    void praise(Map<String, Object> requestMap);

    void watch(Map<String, Object> requestMap);
}

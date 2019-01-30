package com.android.mb.movie.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IHistoryPresenter {
    void getHistory(Map<String, Object> requestMap);

    void delHistory(Map<String, Object> requestMap);
}

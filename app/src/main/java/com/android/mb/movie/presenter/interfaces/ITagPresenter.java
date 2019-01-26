package com.android.mb.movie.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ITagPresenter {
    void getTags();
    void queryVideos(Map<String, Object> requestMap);
}

package com.android.mb.movie.presenter.interfaces;

import java.io.File;
import java.util.Map;

public interface IAccountPresenter {
    void getUserInfo(Map<String, Object> requestMap);

    void updateInfo(Map<String, Object> requestMap);

    void uploadAvatar(File file);
}

package com.android.mb.movie.presenter.interfaces;

import java.io.File;
import java.util.Map;

public interface IFeedbackPresenter {
    void feedback(File file,Map<String, Object> requestMap);

}

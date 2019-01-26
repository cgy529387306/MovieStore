package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.VideoListData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ITagView extends BaseMvpView {
    void getSuccess(List<Tag> result);

    void querySuccess(VideoListData result);
}

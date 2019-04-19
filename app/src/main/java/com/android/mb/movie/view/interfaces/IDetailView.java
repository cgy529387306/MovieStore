package com.android.mb.movie.view.interfaces;

import com.android.mb.movie.base.BaseMvpView;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.VideoData;
import com.android.mb.movie.entity.VideoListData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IDetailView extends BaseMvpView {

    void getVideoDetail(VideoData result);

    void comment(Object result);

    void praise(Object result);

    void watch(Object result);

    void getVideoComments(CommentListData result);
}

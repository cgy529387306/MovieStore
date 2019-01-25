package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoListData extends BaseListData implements Serializable {

    private List<Video> list;

    public List<Video> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }
}

package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentListData extends BaseListData implements Serializable {

    private List<Comment> list;

    public List<Comment> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}

package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecialData implements Serializable{

    private List<AuthorVideo> authorList;

    private List<Special> specialList;

    public List<AuthorVideo> getAuthorList() {
        if (authorList == null) {
            return new ArrayList<>();
        }
        return authorList;
    }

    public void setAuthorList(List<AuthorVideo> authorList) {
        this.authorList = authorList;
    }

    public List<Special> getSpecialList() {
        if (specialList == null) {
            return new ArrayList<>();
        }
        return specialList;
    }

    public void setSpecialList(List<Special> specialList) {
        this.specialList = specialList;
    }
}

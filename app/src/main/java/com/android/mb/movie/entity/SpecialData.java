package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.List;

public class SpecialData implements Serializable{

    private List<AuthorVideo> authorList;

    private List<Special> specialList;

    public List<AuthorVideo> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<AuthorVideo> authorList) {
        this.authorList = authorList;
    }

    public List<Special> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(List<Special> specialList) {
        this.specialList = specialList;
    }
}

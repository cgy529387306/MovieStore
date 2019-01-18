package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.List;

public class HomeData implements Serializable{

    private List<Advert> advertList;

    private List<CateVideo> videoList;

    private List<Category> cateList;

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public List<CateVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<CateVideo> videoList) {
        this.videoList = videoList;
    }

    public List<Category> getCateList() {
        return cateList;
    }

    public void setCateList(List<Category> cateList) {
        this.cateList = cateList;
    }
}

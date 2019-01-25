package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeData implements Serializable{

    private List<Advert> advertList;

    private List<CateVideo> videoList;

    private List<Category> cateList;

    public List<Advert> getAdvertList() {
        if (advertList == null) {
            return new ArrayList<>();
        }
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public List<CateVideo> getVideoList() {
        if (videoList == null) {
            return new ArrayList<>();
        }
        return videoList;
    }

    public void setVideoList(List<CateVideo> videoList) {
        this.videoList = videoList;
    }

    public List<Category> getCateList() {
        if (cateList == null) {
            return new ArrayList<>();
        }
        return cateList;
    }

    public void setCateList(List<Category> cateList) {
        this.cateList = cateList;
    }
}

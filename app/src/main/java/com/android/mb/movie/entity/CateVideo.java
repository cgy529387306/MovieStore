package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CateVideo implements Serializable{

    private String cateId;

    private String cateName;

    private List<Video> videos;
    private List<Advert> advert;

    public String getCateId() {
        return cateId == null ? "" : cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName == null ? "" : cateName;

    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Video> getVideos() {
        if (videos == null) {
            return new ArrayList<>();
        }
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Advert> getAdvert() {
        if (advert == null) {
            return new ArrayList<>();
        }
        return advert;
    }

    public void setAdvert(List<Advert> advert) {
        this.advert = advert;
    }
}

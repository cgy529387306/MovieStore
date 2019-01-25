package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthorVideo implements Serializable{

    private String icon;
    private String id;
    private String intros;
    private String name;
    private int videoCount;
    private List<Video> videos;

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntros() {
        return intros == null ? "" : intros;
    }

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
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
}

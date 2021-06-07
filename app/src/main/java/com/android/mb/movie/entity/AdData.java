package com.android.mb.movie.entity;

import java.util.ArrayList;
import java.util.List;

public class AdData {
    private List<Advert> videoPrePlayAdvert;
    private List<Advert> videoDetailAdvert;
    private List<Advert> appPopAdvert;
    private List<Advert> appStartAdvert;

    public List<Advert> getVideoPrePlayAdvert() {
        if (videoPrePlayAdvert == null) {
            return new ArrayList<>();
        }
        return videoPrePlayAdvert;
    }

    public void setVideoPrePlayAdvert(List<Advert> videoPrePlayAdvert) {
        this.videoPrePlayAdvert = videoPrePlayAdvert;
    }

    public List<Advert> getVideoDetailAdvert() {
        if (videoDetailAdvert == null) {
            return new ArrayList<>();
        }
        return videoDetailAdvert;
    }

    public void setVideoDetailAdvert(List<Advert> videoDetailAdvert) {
        this.videoDetailAdvert = videoDetailAdvert;
    }

    public List<Advert> getAppPopAdvert() {
        if (appPopAdvert == null) {
            return new ArrayList<>();
        }
        return appPopAdvert;
    }

    public void setAppPopAdvert(List<Advert> appPopAdvert) {
        this.appPopAdvert = appPopAdvert;
    }

    public List<Advert> getAppStartAdvert() {
        if (appStartAdvert == null) {
            return new ArrayList<>();
        }
        return appStartAdvert;
    }

    public void setAppStartAdvert(List<Advert> appStartAdvert) {
        this.appStartAdvert = appStartAdvert;
    }
}

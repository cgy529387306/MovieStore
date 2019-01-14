package com.android.mb.movie.entity;

import java.io.Serializable;

/**
 * Created by cgy on 19/1/13.
 */

public class BannerItem implements Serializable{
    public String imageUrl;
    public String title;

    public BannerItem() {
    }

    public BannerItem(String title, String imageUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
    }
}

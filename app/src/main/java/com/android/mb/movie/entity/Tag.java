package com.android.mb.movie.entity;

import java.io.Serializable;

public class Tag implements Serializable{

    private String id;
    private String name;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

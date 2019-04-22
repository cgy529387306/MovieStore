package com.android.mb.movie.entity;

import com.android.mb.movie.utils.Helper;

import java.io.Serializable;

public class Comment implements Serializable{

    private String content;
    private long createTime;
    private String id;
    private String userId;
    private String videoId;
    private String userNickName;
    private String userAvatarUrl;


    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId == null ? "" : videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserNickName() {
        return userNickName == null ? "" : userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatarUrl() {
        return Helper.isEmpty(userAvatarUrl) ? Helper.isEmpty(userId)? userId:"" : userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }
}

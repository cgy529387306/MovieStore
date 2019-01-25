package com.android.mb.movie.entity;

import com.android.mb.movie.utils.Helper;

public class UserBean {
    private String accesstoken;
    private String userid;
    private String phone;
    private int sex;//0：未知  1：男  2：女
    private String sexText;
    private int userType;
    private boolean manager;
    private long create_time;
    private String avatar_url;
    private String nickname;

    public String getAccesstoken() {
        return accesstoken == null ? "" : accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexText() {
        return sexText == null ? "" : sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getAvatar_url() {
        return avatar_url == null ? "" : avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNickname() {
        return Helper.isEmpty(nickname) ? phone : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

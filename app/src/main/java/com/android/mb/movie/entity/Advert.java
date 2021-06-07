package com.android.mb.movie.entity;

import java.io.Serializable;

/**
 * 广告，包括首页轮播图和中间广告
 *
 */
public class Advert implements Serializable{
	private String id;
	private String coverUrl;
	private String redirectUrl;
	private String desc;
	private int type;
	private String resId;
	private String seconds;

	public String getId() {
		return id == null ? "" : id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoverUrl() {
		return coverUrl == null ? "" : coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl == null ? "" : redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getDesc() {
		return desc == null ? "" : desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getResId() {
		return resId == null ? "" : resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

    public int getSeconds() {
	    try {
	        return Integer.parseInt(seconds);
        }catch (Exception e){
	        e.printStackTrace();
	        return  6;
        }
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }
}

package com.android.mb.movie.entity;

import java.io.Serializable;

/**
 * 广告，包括首页轮播图和中间广告
 *
 */
public class Advert implements Serializable{
	private String aId;
	private String coverUrl;
	private String redirectUrl;
	private String desc;
	public String getAId() {
		return aId;
	}
	public void setAId(String id) {
		aId = id;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

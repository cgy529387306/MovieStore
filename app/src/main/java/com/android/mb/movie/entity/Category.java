package com.android.mb.movie.entity;

import java.io.Serializable;

/**
 * 分类
 *
 */
public class Category implements Serializable{
	private String cateId;
	private String cateName;
	private String coverUrl;

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

	public String getCoverUrl() {
		return coverUrl == null ? "" : coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
}

package com.android.mb.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频
 *
 */
public class Video implements Serializable{
	private String id; // 主键ID
	private String name; //名称
	private String intros; //简介
	private String coverUrl; //封面地址
	private String videoUrl; //视频地址
	private String size; //视频大小
	private String creator; //创建者
	private String cateId; //分类ID
	private float score; //评分
	private int playCount; //播放数
	private int praiseCount; //点赞数
	private String tag; //标签
	private List<Tag> tagList;
	private boolean isSelect;
	private String coverUrl1; //横屏封面地址
	private String coverUrl2; //竖屏封面地址
	private int isPraise;

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

	public String getIntros() {
		return intros == null ? "" : intros;
	}

	public void setIntros(String intros) {
		this.intros = intros;
	}

	public String getCoverUrl() {
		return coverUrl == null ? "" : coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getVideoUrl() {
		return videoUrl == null ? "" : videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSize() {
		return size == null ? "" : size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCreator() {
		return creator == null ? "" : creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCateId() {
		return cateId == null ? "" : cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getPlayCount() {
		return playCount;
	}

	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getTag() {
		return tag == null ? "" : tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Tag> getTagList() {
		if (tagList == null) {
			return new ArrayList<>();
		}
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public String getCoverUrl1() {
		return coverUrl1 == null ? coverUrl2==null?"":coverUrl2 : coverUrl1;
	}

	public void setCoverUrl1(String coverUrl1) {
		this.coverUrl1 = coverUrl1;
	}

	public String getCoverUrl2() {
		return coverUrl2 == null ? coverUrl1==null?"":coverUrl1 : coverUrl2;
	}

	public void setCoverUrl2(String coverUrl2) {
		this.coverUrl2 = coverUrl2;
	}

	public boolean getIsPraise() {
		return isPraise == 1;
	}

	public void setIsPraise(int isPraise) {
		this.isPraise = isPraise;
	}
}

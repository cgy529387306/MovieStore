package com.android.mb.movie.entity;

import java.io.Serializable;

/**
 * 视频
 *
 */
public class Video implements Serializable{
	private String vId; // 主键ID
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
	public String getVId() {
		return vId;
	}
	public void setVId(String id) {
		vId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntros() {
		return intros;
	}
	public void setIntros(String intros) {
		this.intros = intros;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCateId() {
		return cateId;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
}

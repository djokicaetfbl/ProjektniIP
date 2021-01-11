package org.unibl.etf.dto;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String content;
	private Date shareTime;
	private String picture;
	private Integer userId;
	private Integer postId;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(Integer id, String content, Date shareTime, String picture, Integer userId, Integer postId) {
		super();
		this.id = id;
		this.content = content;
		this.shareTime = shareTime;
		this.picture = picture;
		this.userId = userId;
		this.postId = postId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}

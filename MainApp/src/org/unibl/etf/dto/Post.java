package org.unibl.etf.dto;

import java.util.Date;

public class Post {
	
	private int id;
	private String contents;
	private int userId;
	private boolean active;
	private int postGroupIdPostGroup;
	private Date shareTime;
	private boolean emergencyNotification;
	private float geographicLatitude;
	private float geographicLongitude;
	
	
	
	public Post(int id, String contents, int userId, boolean active, int postGroupIdPostGroup, Date shareTime,
			boolean emergencyNotification, float geographicLatitude, float geographicLongitude) {
		super();
		this.id = id;
		this.contents = contents;
		this.userId = userId;
		this.active = active;
		this.postGroupIdPostGroup = postGroupIdPostGroup;
		this.shareTime = shareTime;
		this.emergencyNotification = emergencyNotification;
		this.geographicLatitude = geographicLatitude;
		this.geographicLongitude = geographicLongitude;
	}
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getPostGroupIdPostGroup() {
		return postGroupIdPostGroup;
	}
	public void setPostGroupIdPostGroup(int postGroupIdPostGroup) {
		this.postGroupIdPostGroup = postGroupIdPostGroup;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", contents=" + contents + ", userId=" + userId + ", active=" + active
				+ ", postGroupIdPostGroup=" + postGroupIdPostGroup + "]";
	}
	public Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}
	public boolean isEmergencyNotification() {
		return emergencyNotification;
	}
	public void setEmergencyNotification(boolean emergencyNotification) {
		this.emergencyNotification = emergencyNotification;
	}
	public float getGeographicLatitude() {
		return geographicLatitude;
	}
	public void setGeographicLatitude(float geographicLatitude) {
		this.geographicLatitude = geographicLatitude;
	}
	public float getGeographicLongitude() {
		return geographicLongitude;
	}
	public void setGeographicLongitude(float geographicLongitude) {
		this.geographicLongitude = geographicLongitude;
	}
	
	
	
}

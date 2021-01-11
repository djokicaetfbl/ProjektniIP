package org.unibl.etf.dto;

import java.sql.Timestamp;

public class Token {
	private Integer id;
	private String token;
	private Timestamp creationTime;
	private Timestamp expirationTime;
	private boolean active;
	private Integer userId;
	
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Token(Integer id, String token, Timestamp creationTime, Timestamp expirationTime, boolean active, Integer userId) {
		super();
		this.id = id;
		this.token = token;
		this.creationTime = creationTime;
		this.expirationTime = expirationTime;
		this.active = active;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", creationTime=" + creationTime + ", expirationTime="
				+ expirationTime + ", active=" + active + ", userId=" + userId + "]";
	}
	
	
	
	
}

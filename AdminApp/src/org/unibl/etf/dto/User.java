package org.unibl.etf.dto;

import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userGroupId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private boolean active = false;
	private boolean blocked = false;
	private boolean approved = false;
	private String picture;
	private Integer signInCounter;
	private String country;
	private String region;
	private String city;
	private boolean emergencyNotification = false;
	
	public User(Integer id, Integer userGroupId, String firstName, String lastName, String username, String password,
			String email, Boolean active, Boolean blocked, Boolean approved, String picture, Integer signInCounter,
			String country, String region, String city, Boolean emergencyNotification) {
		super();
		this.id = id;
		this.userGroupId = userGroupId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.active = active;
		this.blocked = blocked;
		this.approved = approved;
		this.picture = picture;
		this.signInCounter = signInCounter;
		this.country = country;
		this.region = region;
		this.city = city;
		this.emergencyNotification = emergencyNotification;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean getApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Integer getSignInCounter() {
		return signInCounter;
	}
	public void setSignInCounter(Integer signInCounter) {
		this.signInCounter = signInCounter;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password="
				+ password + ", email=" + email + ", active=" + active + ", blocked=" + blocked + ", approved="
				+ approved + ", picture=" + picture + ", signInCounter=" + signInCounter + ", country=" + country
				+ ", region=" + region + ", city=" + city + "]";
	}
	public boolean isEmergencyNotification() {
		return emergencyNotification;
	}
	public void setEmergencyNotification(boolean emergencyNotification) {
		this.emergencyNotification = emergencyNotification;
	}
	
	
	
	
}


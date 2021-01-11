package org.unibl.etf.dto;

import java.io.Serializable;
import java.util.Date;

public class CharJs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Date hour;
	private Integer hour;
	private Integer usersPerHour;
	
	public CharJs() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	//public CharJs(Date hour, Integer usersPerHour) {
	public CharJs(Integer hour, Integer usersPerHour) {
		super();
		this.hour = hour;
		this.usersPerHour = usersPerHour;
	}
	
	


	//public Date getHour() {
	public Integer getHour() {
		return hour;
	}



	//public void setHour(Date hour) {
	public void setHour(Integer hour) {
		this.hour = hour;
	}



	public Integer getUsersPerHour() {
		return usersPerHour;
	}



	public void setUsersPerHour(Integer usersPerHour) {
		this.usersPerHour = usersPerHour;
	}



	@Override
	public String toString() {
		return "CharJs [hour=" + hour + ", usersPerHour=" + usersPerHour + "]";
	}
	
}

package org.unibl.etf.dto;

import java.io.Serializable;
import java.util.Date;

import org.unibl.etf.dao.EmergencyCallCategoryDAO;
import org.unibl.etf.dao.EmergencyCallDAO;

public class EmergencyCall implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Date time;
	private String location;
	private String description;
	private String pircutreURL;
	private boolean blocked;
	private boolean fakeCall;
	private Date shareTime;
	private Integer emergencyCategoryId;
	private String ecName = "";
	
	public String getEcName() {
		return ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}
	
	public EmergencyCall() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmergencyCall(Integer id, String name, Date time, String location, String description, String pircutreURL,
			boolean blocked, boolean fakeCall, Date shareTime, Integer emergencyCategoryId) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.location = location;
		this.description = description;
		this.pircutreURL = pircutreURL;
		this.blocked = blocked;
		this.fakeCall = fakeCall;
		this.shareTime = shareTime;
		this.emergencyCategoryId = emergencyCategoryId;
		this.ecName = EmergencyCallCategoryDAO.getCategoryName(this.emergencyCategoryId);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPircutreURL() {
		return pircutreURL;
	}

	public void setPircutreURL(String pircutreURL) {
		this.pircutreURL = pircutreURL;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isFakeCall() {
		return fakeCall;
	}

	public void setFakeCall(boolean fakeCall) {
		this.fakeCall = fakeCall;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public Integer getEmergencyCategoryId() {
		return emergencyCategoryId;
	}

	public void setEmergencyCategoryId(Integer emergencyCategoryId) {
		this.emergencyCategoryId = emergencyCategoryId;
	}
	
	
	
	
}

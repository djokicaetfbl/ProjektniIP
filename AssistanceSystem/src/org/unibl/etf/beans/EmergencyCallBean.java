package org.unibl.etf.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.unibl.etf.dao.EmergencyCallCategoryDAO;
import org.unibl.etf.dao.EmergencyCallDAO;
import org.unibl.etf.dto.EmergencyCall;
import org.unibl.etf.dto.EmergencyCallCategory;

@ManagedBean(name = "emergencyCallBean")
@SessionScoped
public class EmergencyCallBean {
	
	private EmergencyCall emergencyCall = new EmergencyCall();
	private Map<String, Integer> emergencyCallCategoriesHashMap = new HashMap<String, Integer>();
	private ArrayList<EmergencyCallCategory> emergencyCallCategoryArrayList = new ArrayList<EmergencyCallCategory>();
	
	public EmergencyCall getEmergencyCall() {
		return emergencyCall;
	}



	public void setEmergencyCall(EmergencyCall emergencyCall) {
		this.emergencyCall = emergencyCall;
	}



	public Map<String, Integer> getEmergencyCallCategoriesHashMap() {
		return emergencyCallCategoriesHashMap;
	}



	public void setEmergencyCallCategoriesHashMap(Map<String, Integer> emergencyCallCategoriesHashMap) {
		this.emergencyCallCategoriesHashMap = emergencyCallCategoriesHashMap;
	}



	public ArrayList<EmergencyCallCategory> getEmergencyCallCategoryArrayList() {
		return emergencyCallCategoryArrayList;
	}



	public void setEmergencyCallCategoryArrayList(ArrayList<EmergencyCallCategory> emergencyCallCategoryArrayList) {
		this.emergencyCallCategoryArrayList = emergencyCallCategoryArrayList;
	}
	
	@PostConstruct
	public void init() {
		emergencyCallCategoryArrayList = EmergencyCallCategoryDAO.getEmergencyCallCategory();
		
		for(EmergencyCallCategory emergencyCallCategory : emergencyCallCategoryArrayList)
			emergencyCallCategoriesHashMap.put(emergencyCallCategory.getName(), emergencyCallCategory.getId());
	}
	
	public void addEmergencyCall() {
		if(EmergencyCallDAO.add(emergencyCall)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Emergency call successfully added", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return ;
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Emergency call can't be added!", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return ;
		}
	}
	
}

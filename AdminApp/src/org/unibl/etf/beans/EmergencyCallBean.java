package org.unibl.etf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.unibl.etf.dto.EmergencyCall;
import org.unibl.etf.service.EmergencyCallService;

@ManagedBean(name = "emergencyCallBean")
//@SessionScoped
@ViewScoped
public class EmergencyCallBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<EmergencyCall> emergencyCallArrayList = new ArrayList<EmergencyCall>();
	private EmergencyCall emergencyCall = new EmergencyCall();
	
	

	public EmergencyCall getEmergencyCall() {
		return emergencyCall;
	}

	public void setEmergencyCall(EmergencyCall emergencyCall) {
		this.emergencyCall = emergencyCall;
	}

	public ArrayList<EmergencyCall> getEmergencyCallArrayList() {
		return emergencyCallArrayList;
	}

	public void setEmergencyCallArrayList(ArrayList<EmergencyCall> emergencyCallArrayList) {
		this.emergencyCallArrayList = emergencyCallArrayList;
	}
	
	@PostConstruct
	public void init() {
		setEmergencyCallArrayList(EmergencyCallService.getAll());
	}
	
	public void deleteEmergencyCall() {
		boolean result = false;		
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		int id = 0;
		if (reqMap.containsKey("userId")) {
			String z = reqMap.get("userId");
			id = Integer.parseInt(z);
			result = EmergencyCallService.deleteEmergencyCall(id);
		}
		
		if (result) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("Successfully deleted post"));
			removeEmergencyCallFromList(id);
			return;
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("Action is not possible"));
		return;
		}
	}
	
	public void removeEmergencyCallFromList(Integer id) {
		EmergencyCall tmp = null;
		for(EmergencyCall emergencyCall : this.emergencyCallArrayList) {
			if(emergencyCall.getId() == id) {
				tmp = emergencyCall;
			}
		}
		this.emergencyCallArrayList.remove(tmp);
	}
}

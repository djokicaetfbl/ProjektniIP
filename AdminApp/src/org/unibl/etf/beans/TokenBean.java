package org.unibl.etf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.unibl.etf.dao.TokenDAO;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.User;


@ManagedBean(name="tokenBean")
@ViewScoped
//@SessionScoped
public class TokenBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer onlineUsers = 0;
	private Integer approvedUsers = 0;
	
	private ArrayList<User> normalUsersArrayList = new ArrayList<User>();
	private ArrayList<User> usersPasAndBlockUsersArrayList = new ArrayList<User>();
	
	public ArrayList<User> getNormalUsersArrayList() {
		return normalUsersArrayList;
	}

	public void setNormalUsersArrayList(ArrayList<User> normalUsersArrayList) {
		this.normalUsersArrayList = normalUsersArrayList;
	}
	
	public ArrayList<User> getUsersPasAndBlockUsersArrayList() {
		return usersPasAndBlockUsersArrayList;
	}

	public void setUsersPasAndBlockUsersArrayList(ArrayList<User> usersPasAndBlockUsersArrayList) {
		this.usersPasAndBlockUsersArrayList = usersPasAndBlockUsersArrayList;
	}
	
	public Integer getOnlineUsers() {
		return onlineUsers;
	}
	public void setOnlineUsers(Integer onlineUsers) {
		this.onlineUsers = onlineUsers;
	}
	public Integer getApprovedUsers() {
		return approvedUsers;
	}
	public void setApprovedUsers(Integer approvedUsers) {
		this.approvedUsers = approvedUsers;
	}
	
	/*

If the bean has request scope, @PostConstruct will get executed every time. It will be called after the managed bean is instantiated, but before the bean is placed in scope. 
Such a method take no arguments, return void, and may not declare a checked exception to be thrown. Method may be public, protected, private, or package private. 
If the method throws an unchecked exception, the JSF implementation must not put the managed bean into service and no further menthods on that managed bean instance will be called*/
	
	@PostConstruct
	public void init() {
		setApprovedUsers(TokenDAO.getApprovedUsers());
		setOnlineUsers(TokenDAO.getOnlineUsers());
		setNormalUsersArrayList(UserDAO.getAllNotApprovedUsers());
		setUsersPasAndBlockUsersArrayList(UserDAO.getAllPasAndBlockUsers());
	}
	
	public void approveRegistration() {
		boolean result = false;
		
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		int id = 0;
		if (reqMap.containsKey("userId")) {
			String z = reqMap.get("userId");
			id = Integer.parseInt(z);
			result = UserDAO.approveRegistration(id);
		}
		
		if (result) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("Successfully approved account"));
			return;
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("Action is not possible"));
		return;
		}
	}
		
	public void blockUser() {
			boolean result = false;
			
			Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			int id = 0;
			if (reqMap.containsKey("userId")) {
				String z = reqMap.get("userId");
				id = Integer.parseInt(z);
				result = UserDAO.blockUser(id);
			}
			
			if (result) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage("", new FacesMessage("Account successfully blocked"));
				return;
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage("", new FacesMessage("Action is not possible"));
			return;
			}
	}
		
	public void resetUserPassword() {
		boolean result = false;
		
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		int id = 0;
		if (reqMap.containsKey("userId")) {
			String z = reqMap.get("userId");
			id = Integer.parseInt(z);
			result = UserDAO.resetPassword(id);
		}
		
		if (result) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("successfully reset password"));
			return;
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("", new FacesMessage("Action is not possible"));
		return;
		}
	}
}

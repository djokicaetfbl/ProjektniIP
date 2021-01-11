package org.unibl.etf.beans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dao.TokenDAO;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.User;


@ManagedBean(name = "userBean")
@SessionScoped
//@ViewScoped
public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private ArrayList<User> usersArrayList = UserDAO.getAllUsers();
	private ArrayList<User> normalUsersArrayList = new ArrayList<User>();
	private ArrayList<User> usersPasAndBlockUsersArrayList = new ArrayList<User>();

	public ArrayList<User> getNormalUsersArrayList() {
		return normalUsersArrayList;
	}

	public void setNormalUsersArrayList(ArrayList<User> normalUsersArrayList) {
		this.normalUsersArrayList = normalUsersArrayList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
	public ArrayList<User> getUsersPasAndBlockUsersArrayList() {
		return usersPasAndBlockUsersArrayList;
	}

	public void setUsersPasAndBlockUsersArrayList(ArrayList<User> usersPasAndBlockUsersArrayList) {
		this.usersPasAndBlockUsersArrayList = usersPasAndBlockUsersArrayList;
	}

	public void login() {
		if(UserDAO.login(user.getUsername(), user.getPassword())) {
			List<User> result = this.usersArrayList.stream().filter(user -> {
				//try {
					//return user.getUsername().equals(username) && user.getPassword().equals(hash(password));
					return user.getUsername().equals(user.getUsername()) && user.getPassword().equals(user.getPassword());
				//} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
				//return false;
			}).collect(Collectors.toList());
			if(!result.isEmpty()) {
				//result.forEach(System.out::println);
				//this.user = result.get(0);
				this.setUser(result.get(0));

				if(this.user != null) {
					HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
					httpSession.setAttribute("user", user);
					
					FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
							FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");
					return;
				}				
			}
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage("login-form:login-btn", new FacesMessage("Wrong username or password"));
		return;
		
}
	
	public void logout() {
		HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) sesija.getAttribute("user");
		if(user != null) {
		sesija.removeAttribute("user");
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?faces-redirect=true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getUsersArrayList() {
		return usersArrayList;
	}

	public void setUsersArrayList(ArrayList<User> usersArrayList) {
		this.usersArrayList = usersArrayList;
	}
	
}

package org.unibl.etf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.unibl.etf.dao.UserDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Hex;
import org.unibl.etf.dto.User;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private ArrayList<User> usersArrayList = UserDAO.getAllUsers();
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ArrayList<User> getUsersArrayList() {
		return usersArrayList;
	}

	public void setUsersArrayList(ArrayList<User> usersArrayList) {
		this.usersArrayList = usersArrayList;
	}
	
	public boolean add(User user) {
		this.user = user;
		boolean result = UserDAO.add(user);
		return result;
	}
	
	public boolean login(String username, String password) {
		
			if(UserDAO.login(username, password)) {
				List<User> result = this.usersArrayList.stream().filter(user -> {
					try {
						return user.getUsername().equals(username) && user.getPassword().equals(hash(password));
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchProviderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}).collect(Collectors.toList());
				if(!result.isEmpty()) {
					//result.forEach(System.out::println);
					this.user = result.get(0);
				}
				return true;
			}
		return false;
	}
	
	public boolean isUserNameUsed(String username) {
		return UserDAO.isUsernameUsed(username);
	}
	
	public boolean isEmalUsed(String email) {
		return UserDAO.isEmailUsed(email);
	}
	
	public boolean updateUserProfile(User user) {
		return UserDAO.updateUserProfile(user);
	}
	
	public String hash(String tekst) throws NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda;
		mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digest = mda.digest(tekst.trim().getBytes());
		return Hex.encodeHexString(digest);
	}
	
	
	public boolean checkEmailNotation(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
}
}

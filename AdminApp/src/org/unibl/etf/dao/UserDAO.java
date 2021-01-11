package org.unibl.etf.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.unibl.etf.dto.User;

public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username=? AND password=? and user_group_iduser_group = 1 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user WHERE user_group_iduser_group = 1 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_SELECT_NORMAL_ALL_USERS = "SELECT * FROM user WHERE user_group_iduser_group = 2 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_UPDATE_USER_APPROVED = "UPDATE user SET approved = 1 where id = ?";
	private static final String SQL_UPDATE_USER_SET_UNACTIVE = "UPDATE user SET active = 0 and blocked = 1 where id = ?";
	private static final String SQL_UPDATE_USER_SET_ACTIVE = "UPDATE user SET active = 1 and blocked = 0 where id = ?";
	private static final String SQL_UPDATE_USER_PASSWORD_TO_RANDOM = "update user set password = \r\n" + 
			"CONCAT(SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1)\r\n" + 
			"      ,SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',FLOOR(1.0+RAND()*62),1))\r\n" + 
			"where id = ?";
	private static final String SQL_SELECT_ALL_NOT_APPROVED_USERS = "SELECT * FROM user WHERE user_group_iduser_group = 2 and (blocked = 0 or blocked = NULL) and approved = 0";
	
	public static boolean login(String username, String password) {
		Connection connection = null;
		ResultSet rSet = null;
		/*String tmpPass = "";
		try {
			tmpPass = hash(password);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//Object values[] = {username, tmpPass};
		
		Object values[] = {username, password};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
			rSet = preparedStatement.executeQuery();
			
			if(rSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return false;
	}
	
	public static String hash(String tekst) throws NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda;
		mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digest = mda.digest(tekst.trim().getBytes());
		return Hex.encodeHexString(digest);
	}
	
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> usersArrayList = new ArrayList<User>();
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL_USERS, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				User user = new User(rSet.getInt("id"), rSet.getInt("user_group_iduser_group"), rSet.getString("first_name"), rSet.getString("last_name"),
						rSet.getString("username"), rSet.getString("password"), rSet.getString("email"), rSet.getBoolean("active"),
						rSet.getBoolean("blocked"), rSet.getBoolean("approved"), rSet.getString("picture"), rSet.getInt("signInCounter"), rSet.getString("country"),
						rSet.getString("region"), rSet.getString("city"),rSet.getBoolean("emergency_notification"));
				usersArrayList.add(user);
			}
			preparedStatement.close();
			return usersArrayList;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return null;
	}
	
	public static ArrayList<User> getAllNotApprovedUsers() {
		ArrayList<User> usersArrayList = new ArrayList<User>();
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL_NOT_APPROVED_USERS, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				User user = new User(rSet.getInt("id"), rSet.getInt("user_group_iduser_group"), rSet.getString("first_name"), rSet.getString("last_name"),
						rSet.getString("username"), rSet.getString("password"), rSet.getString("email"), rSet.getBoolean("active"),
						rSet.getBoolean("blocked"), rSet.getBoolean("approved"), rSet.getString("picture"), rSet.getInt("signInCounter"), rSet.getString("country"),
						rSet.getString("region"), rSet.getString("city"),rSet.getBoolean("emergency_notification"));
				usersArrayList.add(user);
			}
			preparedStatement.close();
			return usersArrayList;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return null;
	}
	public static ArrayList<User> getAllPasAndBlockUsers(){
		ArrayList<User> usersArrayList = new ArrayList<User>();
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_NORMAL_ALL_USERS, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				User user = new User(rSet.getInt("id"), rSet.getInt("user_group_iduser_group"), rSet.getString("first_name"), rSet.getString("last_name"),
						rSet.getString("username"), rSet.getString("password"), rSet.getString("email"), rSet.getBoolean("active"),
						rSet.getBoolean("blocked"), rSet.getBoolean("approved"), rSet.getString("picture"), rSet.getInt("signInCounter"), rSet.getString("country"),
						rSet.getString("region"), rSet.getString("city"),rSet.getBoolean("emergency_notification"));
				usersArrayList.add(user);
			}
			preparedStatement.close();
			return usersArrayList;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return null;
	}
	
	public static boolean approveRegistration(Integer id) {
		boolean result = false;	
		Connection connection = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER_APPROVED, true, values);
			int queryResult = preparedStatement.executeUpdate();
			if(queryResult != 0) {
				result = true;
			} else {
				result = false;
			}
			preparedStatement.close();
		}catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	//blockUser
	
	public static boolean blockUser(Integer id) {
		boolean result = false;
		ArrayList<User> usersArrayList = new ArrayList<User>();
		usersArrayList = getAllPasAndBlockUsers();
		User user = null;
		
		for(User u: usersArrayList) {
			if(id == u.getId()) {
				user = u;
			}
		}
		Connection connection = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement;
			if(!user.getActive()) {
				preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER_SET_ACTIVE, true, values);
			} else {
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER_SET_UNACTIVE, true, values);
			}
			int queryResult = preparedStatement.executeUpdate();
			if(queryResult != 0) {
				result = true;
			} else {
				result = false;
			}
			preparedStatement.close();
		}catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean resetPassword(Integer id) {
		boolean result = false;
		Connection connection = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER_PASSWORD_TO_RANDOM, true, values);
			int queryResult = preparedStatement.executeUpdate();
			if(queryResult != 0) {
				result = true;
			} else {
				result = false;
			}
			preparedStatement.close();
		}catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
}

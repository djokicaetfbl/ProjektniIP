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

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Hex;
import org.unibl.etf.dto.User;


public class UserDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT = "INSERT INTO user (first_name, last_name, username, password, email, active, user_group_iduser_group, blocked, approved, picture, signInCounter, country, region, city, emergency_notification) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username=? AND password=? and user_group_iduser_group = 2 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user WHERE user_group_iduser_group = 2 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_SELECT_USERNAME = "SELECT * FROM user WHERE username = ?";
	private static final String SQL_SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
	private static final String SQL_UPDATE_USER_PROFILE = "UPDATE user SET first_name = ?, last_name = ?, email = ?, picture = ?, emergency_notification = ?, country = ?, region =?, city = ? where id = ? ";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id = ? and user_group_iduser_group = 2 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)";
	private static final String SQL_ENLARGE_SIGN_IN_COUNTER = "SELECT signInCounter FROM user where id = ?";
	private static final String SQL_SET_SIGN_IN_COUNTER_BY_USER_ID = "UPDATE user SET signInCounter = ? WHERE id = ?";
	private static final String SQL_SELECT_ALL_USERS_WITH_EMERGENCY_NOTIFICATION = "SELECT * FROM user WHERE user_group_iduser_group = 2 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL) and emergency_notification = true";
	
	public static boolean add (User user) {
		boolean result = false;
		Connection conn = null;
		ResultSet generatedKeys = null;
				
		Object values[] = {user.getFirstName(), user.getLastName(), user.getUsername(),user.getPassword(), user.getEmail(), user.getActive(),
				user.getUserGroupId(), user.getBlocked(), user.getApproved(), user.getPicture(), user.getSignInCounter(), user.getCountry(), user.getRegion(), user.getCity(), user.isEmergencyNotification() };
		try {
			conn = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(conn, SQL_INSERT, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			if(preparedStatement.getUpdateCount() > 0) {
				result = true;
			}
			if(generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(conn);
		}
		return result;
	}
	
	public static boolean login(String username, String password) {
		Connection connection = null;
		ResultSet rSet = null;
		String tmpPass = "";
		try {
			tmpPass = hash(password);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object values[] = {username, tmpPass};
		
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
	
	public static ArrayList<User> getUsersForEmergencyNotification(){
		ArrayList<User> usersArrayList = new ArrayList<User>();
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL_USERS_WITH_EMERGENCY_NOTIFICATION, false, values);
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
	
	public static boolean isUsernameUsed(String username) {
		boolean result = false;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_USERNAME, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = true;
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
			result = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean isEmailUsed(String email) {
		boolean result = false;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {email};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_EMAIL, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = true;
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
			result = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
		
	public static boolean updateUserProfile(User user) {
		boolean result = false;
		Connection connection = null;
		Object[] values = {user.getFirstName(), user.getLastName(), user.getEmail(), user.getPicture(), user.isEmergencyNotification(), user.getCountry(), user.getRegion(), user.getCity(), user.getId()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER_PROFILE, true, values);
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
	
	public static User getUserById (int userId) {
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {userId};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_ID, false, values);
			rSet = preparedStatement.executeQuery();
			if(rSet.next()) {
				return new User(rSet.getInt("id"), rSet.getInt("user_group_iduser_group"), rSet.getString("first_name"), rSet.getString("last_name"), rSet.getString("username"), rSet.getString("password"), rSet.getString("email"), rSet.getBoolean("active"), rSet.getBoolean("blocked"), rSet.getBoolean("approved"), rSet.getString("picture"), rSet.getInt("signInCounter"), rSet.getString("country"), rSet.getString("region"), rSet.getString("city"), rSet.getBoolean("emergency_notification"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return null;
	}
	
	
	
	public static boolean setUserLoginCounter(int id) {
		boolean result = false;
		Connection connection = null;
		Object[] values = {id};
		int signInCounter = 0;
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_ENLARGE_SIGN_IN_COUNTER, true, values);
			ResultSet rSet = preparedStatement.executeQuery();
			if(rSet.next()) {
				signInCounter = rSet.getInt("signInCounter");
			}
			signInCounter++;
			Object[] values2 = {signInCounter, id};
			PreparedStatement preparedStatement2 = DAOUtil.prepareStatement(connection, SQL_SET_SIGN_IN_COUNTER_BY_USER_ID, true, values2);
			int resultQuery = preparedStatement2.executeUpdate();
			if(resultQuery != 0) {
				result = true;
			} else {
				result = false;
			}
			preparedStatement.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
}








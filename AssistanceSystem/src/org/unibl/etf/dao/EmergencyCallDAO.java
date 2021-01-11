package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.dto.EmergencyCall;

public class EmergencyCallDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_ADD_EMERGENCY_CALL = "INSERT into emergency_call (name, time, location, description, pictureURL, blocked, fake_call, share_time, emergency_category_id) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_GET_ALL_EMERGENCY_CALL = "SELECT * FROM emergency_call where blocked = 0 AND fake_call = 0";
	private static final String SQL_BLOCK_EMERGENCY_CALL = "UPDATE emergency_call SET blocked = 1 where id = ?";
	private static final String SQL_REPORT_FAKE_CALL = "UPDATE emergency_call SET fake_call = 1 where id = ?";
	private static final String SQL_GET_CATEGORY_NAME = "SELECT name FROM emergency_category where id = ?";
	private static final String SQL_DELETE_EMERGENCY_CALL = "DELETE FROM emergency_call where id = ?";
	
	public static boolean add (EmergencyCall emergencyCall) {
		boolean result = false;
		Connection conn = null;
		ResultSet generatedKeys = null;
				
		Object values[] = {emergencyCall.getName(), emergencyCall.getTime(), emergencyCall.getLocation(), emergencyCall.getDescription(), emergencyCall.getPircutreURL(),
				emergencyCall.isBlocked(), emergencyCall.isFakeCall(), new Date(), emergencyCall.getEmergencyCategoryId()};
		try {
			conn = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(conn, SQL_ADD_EMERGENCY_CALL, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			if(preparedStatement.getUpdateCount() > 0) {
				result = true;
			}
			if(generatedKeys.next()) {
				emergencyCall.setId(generatedKeys.getInt(1));
			}
			if(result) {
				emergencyCall.setName(null);
				emergencyCall.setTime(null);
				emergencyCall.setLocation(null);
				emergencyCall.setDescription(null);
				emergencyCall.setPircutreURL(null);
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(conn);
		}
		return result;
	}
	
	/*	public EmergencyCall(Integer id, String name, Date time, String location, String description, String pircutreURL,
			boolean blocked, boolean fakeCall, Date shareTime, Integer emergencyCategoryId) */
	
	public static ArrayList<EmergencyCall> getAllEmergencyCallPosts(){
		ArrayList<EmergencyCall> postsArrayList = new ArrayList<EmergencyCall>();
		Connection connection = null;
		ResultSet rs = null;
		Object[] values = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_ALL_EMERGENCY_CALL, false, values);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				EmergencyCall emergencyCall = new EmergencyCall();
				emergencyCall.setId(rs.getInt("id"));
				emergencyCall.setName(rs.getString("name"));
				emergencyCall.setTime(rs.getTimestamp("time"));
				emergencyCall.setLocation(rs.getString("location"));
				emergencyCall.setDescription(rs.getString("description"));
				emergencyCall.setPircutreURL(rs.getString("pictureURL"));
				emergencyCall.setBlocked(rs.getBoolean("blocked"));
				emergencyCall.setFakeCall(rs.getBoolean("fake_call"));
				emergencyCall.setShareTime(rs.getTimestamp("share_time"));
				emergencyCall.setEmergencyCategoryId(rs.getInt("emergency_category_id"));
				String tmp = EmergencyCallCategoryDAO.getCategoryName(rs.getInt("emergency_category_id"));
				emergencyCall.setEcName(tmp);
				postsArrayList.add(emergencyCall);
				/*postsArrayList.add(new EmergencyCall(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("time"), rs.getString("location"), rs.getString("description"),
						rs.getString("pictureURL"), rs.getBoolean("blocked"), rs.getBoolean("fake_call"), rs.getTimestamp("share_time"), rs.getInt("emergency_category_id")));*/
			}
			preparedStatement.close();
			return postsArrayList;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		
		return null;
	}
	
	public static boolean blockEmergencyCall(Integer id) {
		boolean result = false;
		Connection connection = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement;
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_REPORT_FAKE_CALL, true, values);
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
	
	public static boolean reportFakekEmergencyCall(Integer id) {
		boolean result = false;
		Connection connection = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement;
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_BLOCK_EMERGENCY_CALL, true, values);
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
	
	public static String getCategoryName(Integer id) {
		String result = "";
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {id};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_CATEGORY_NAME, false, values);
			rSet = preparedStatement.executeQuery();
			if (rSet.next()) {
				result = rSet.getString("name");
			}
			preparedStatement.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
	
	public static boolean deleteEmergencyCall(Integer id) {
		boolean result = false;
		Connection connnection = null;
		Object values[] = { id };
		try {
			connnection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connnection, SQL_DELETE_EMERGENCY_CALL, true, values);
			int queryResult = preparedStatement.executeUpdate();
			if (queryResult == 0)
				result = false;
			else
				result = true;
			preparedStatement.close();
		} catch (SQLException e) {
			result = false;
		} finally {
			connectionPool.checkIn(connnection);
		}
		return result;
	}
	
}

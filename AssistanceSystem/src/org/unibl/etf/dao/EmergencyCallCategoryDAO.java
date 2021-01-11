package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.unibl.etf.dto.EmergencyCallCategory;

public class EmergencyCallCategoryDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_GET_ALL_EMERGENCY_CALL_CATEGORY = "SELECT * FROM emergency_category";
	private static final String SQL_GET_CATEGORY_NAME = "SELECT name FROM emergency_category where id = ?";
	
	public static ArrayList<EmergencyCallCategory> getEmergencyCallCategory(){
		ArrayList<EmergencyCallCategory> result = new ArrayList<EmergencyCallCategory>();
		
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_ALL_EMERGENCY_CALL_CATEGORY, false, values);
			rSet = preparedStatement.executeQuery();
			while (rSet.next()) {
				result.add(new EmergencyCallCategory(rSet.getInt("id"), rSet.getString("name")));
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
	
}

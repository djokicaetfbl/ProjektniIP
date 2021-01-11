package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmergencyCallDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_GET_CATEGORY_NAME = "SELECT name FROM emergency_category where id = ?";
	
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

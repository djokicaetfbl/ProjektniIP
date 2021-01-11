package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.dto.CharJs;
import org.unibl.etf.dto.User;


public class TokenDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_GET_ONLINE_USERS = "SELECT COUNT(*) FROM (SELECT * FROM TOKEN where active = 1) as x;";
	private static final String SQL_SELECT_ALL_USERS = "SELECT COUNT(*) FROM (SELECT * FROM user WHERE user_group_iduser_group = 2 and active = 1 and approved = 1 and (blocked = 0 or blocked = NULL)) as y";
	private static final String SQL_SELECT_USERS_PER_HOUR_LAST_24_HOUR = "SELECT HOUR(creation_time) 'hr' , COUNT(DISTINCT user_id) FROM token WHERE creation_time >= NOW() - INTERVAL 1 DAY GROUP BY hr";
	
	
	public static int getOnlineUsers() {
		int result = 0;
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_ONLINE_USERS, false, values);
			rSet = preparedStatement.executeQuery();
			rSet.next();
			String sum = rSet.getString(1);
			result = Integer.parseInt(sum);
			preparedStatement.close();
			return result;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
		
	}
	
	public static int getApprovedUsers() {
		int result = 0;
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL_USERS, false, values);
			rSet = preparedStatement.executeQuery();
			rSet.next();
			String sum = rSet.getString(1);
			result = Integer.parseInt(sum);
			preparedStatement.close();
			return result;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;		
	}
	
	public static ArrayList<CharJs> getNumberOfUsersPerHours() {
		ArrayList<CharJs> charJsArrayList = new ArrayList<CharJs>();
		Connection connection = null;
		ResultSet rSet = null;
		Object values[] = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_USERS_PER_HOUR_LAST_24_HOUR, false, values);
			rSet = preparedStatement.executeQuery();
			
			while(rSet.next()) {
				//CharJs charJs = new CharJs(rSet.getTimestamp("hr"),rSet.getInt(2));
				CharJs charJs = new CharJs(rSet.getInt(1),rSet.getInt(2));
				charJsArrayList.add(charJs);
				
			}
			preparedStatement.close();
			return charJsArrayList;
		} catch (SQLException e) {
			
		} finally {
			connectionPool.checkIn(connection);
		}
		return null;		
	}

}

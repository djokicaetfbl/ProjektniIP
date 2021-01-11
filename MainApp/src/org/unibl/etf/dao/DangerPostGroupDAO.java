package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.dto.DangerPostGroup;

public class DangerPostGroupDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_DANGER_GROUP = "SELECT * FROM danger_post_group WHERE active = 1 ORDER BY name DESC";
	private static final String SQL_SELECT_DANGER_GROUP_FOR_POST = "SELECT name FROM danger_post_group WHERE active = 1 AND id = ?  ORDER BY name DESC";
	
	
	public static ArrayList<DangerPostGroup> getDangerGroup(){
		
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {};
		ArrayList<DangerPostGroup> dangerPostGroupsArrayList = new ArrayList<DangerPostGroup>();
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_DANGER_GROUP, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				dangerPostGroupsArrayList.add(new DangerPostGroup(rSet.getInt("id"), rSet.getString("name"), rSet.getBoolean("active")));
			}
			preparedStatement.close();
			return dangerPostGroupsArrayList;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		
		return null;
	}
	
	public static String getPostGroupName (Integer id) {
		String resultString = "";
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_DANGER_GROUP_FOR_POST, false, values);
			rSet = pstmt.executeQuery();
			if (rSet.next()){
				resultString = rSet.getString("name");
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		return resultString;
	}
}

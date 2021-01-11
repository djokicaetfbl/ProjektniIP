package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.dto.DangerPostGroup;
import org.unibl.etf.dto.PostHasDangerPostGroup;

public class PostHasDangerPostGroupDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_DANG_POST_HAS_MORE_DANG_GROUP = "INSERT INTO post_has_danger_post_group (post_id,danger_post_group_id) values (?,?)";
	private static final String SQL_GET_DANGER_GROUP_ID = "SELECT danger_post_group_id FROM post_has_danger_post_group where post_id = ?";
	
	public static boolean insert(PostHasDangerPostGroup phdpg) {
		Connection connection = null;
		ResultSet generatedKeys = null;
		boolean result = false;
		
		for(Integer dangerPostGroupId : phdpg.getDangerPostGroupIdArrayList()) {
			Object[] values = {phdpg.getPostID(),dangerPostGroupId};
			
			try {
				connection = connectionPool.checkOut();
				PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT_DANG_POST_HAS_MORE_DANG_GROUP, true, values);
				preparedStatement.executeUpdate();
				generatedKeys = preparedStatement.getGeneratedKeys();
				
				if(preparedStatement.getUpdateCount() > 0) {
					result = false;
				}
				
				if(generatedKeys.next()) {
					result = true;
				}
				preparedStatement.close();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				connectionPool.checkIn(connection);
			}
		}
		return false;
	}
	
	public static ArrayList<Integer> getDangerGroupIds(Integer postID){
		
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = {postID};
		ArrayList<Integer> dangerGroupIds = new ArrayList<Integer>();
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_DANGER_GROUP_ID, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				dangerGroupIds.add(rSet.getInt("danger_post_group_id"));
			}
			preparedStatement.close();
			return dangerGroupIds;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		
		return null;
	}

}

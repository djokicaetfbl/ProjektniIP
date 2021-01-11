package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.dto.Post;
import org.unibl.etf.service.EmailService;

public class PostDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT = "INSERT INTO post (contents, user_id, active, post_group_idpost_group, share_time, emergency_notification, geographic_latitude, geographic_longitude ) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_GET_ALL_POSTS = "SELECT * FROM post WHERE active = 1 ORDER BY share_time DESC";
	
	public static int insertPost(Post post) {
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object[] values = {post.getContents(), post.getUserId(), post.isActive(), post.getPostGroupIdPostGroup(), post.getShareTime(), post.isEmergencyNotification(), post.getGeographicLatitude(), post.getGeographicLongitude()};
		int result = 0;	
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			if(preparedStatement.getUpdateCount() > 0) {
				result = 0;
			}
			if(generatedKeys.next()) {
				result = generatedKeys.getInt(1);
				post.setId(result);
			}
			
			if(post.isEmergencyNotification()) {
				new EmailService().sendMails(post);
			}		
			preparedStatement.close();		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
	
	public static ArrayList<Post> getAllPosts(){
		ArrayList<Post> postsArrayList = new ArrayList<Post>();
		Connection connection = null;
		ResultSet rs = null;
		Object[] values = {};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_ALL_POSTS, false, values);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				postsArrayList.add(new Post(rs.getInt("id"), rs.getString("contents"), rs.getInt("user_id"), rs.getBoolean("active"), rs.getInt("post_group_idpost_group"), rs.getTimestamp("share_time"), rs.getBoolean("emergency_notification"), rs.getFloat("geographic_latitude"), rs.getFloat("geographic_longitude")));
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
	
}

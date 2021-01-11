package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.unibl.etf.dto.Image;
import org.unibl.etf.dto.Post;

public class ImageDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT = "INSERT INTO image (content, active, post_id) VALUES (?,?,?)";
	private static final String SQL_GET_ALL_PICTURES_FOR_POST = "SELECT * FROM image WHERE active = 1 AND post_id = ?";
	
	public static int insertImageInPost(Image image) {
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object[] values = {image.getContent(), image.isActive(), image.getPostId()};
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
				image.setId(result);
			}
			
			preparedStatement.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
		
	public static ArrayList<Image> getAllImagesForPost(Integer postId){
		ArrayList<Image> imagesArrayList = new ArrayList<Image>();
		Connection connection = null;
		ResultSet rs = null;
		Object[] values = {postId};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_GET_ALL_PICTURES_FOR_POST, false, values);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				imagesArrayList.add(new Image(rs.getInt("id"), rs.getString("content"),rs.getBoolean("active"),rs.getInt("post_id")));
			}
			return imagesArrayList;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionPool.checkIn(connection);
		}		
		return null;		
	}
}

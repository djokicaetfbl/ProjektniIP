package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.unibl.etf.dto.User;
import org.unibl.etf.dto.Comment;

public class CommentDAO {
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT = "INSERT INTO comment (content, share_time, picture, user_id, post_id) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_GET_COMMENTS_FOR_POST = "SELECT * FROM comment where post_id = ? ORDER BY share_time DESC";
	
	
	public static int addComment(Comment comment) {
		int result = 0;
		Connection conn = null;
		ResultSet generatedKeys = null;
						
		Object values[] = {comment.getContent(), comment.getShareTime(), comment.getPicture(), comment.getUserId(), comment.getPostId() };
		try {
			conn = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(conn, SQL_INSERT, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			if(preparedStatement.getUpdateCount() > 0) {
				result = 0;
			}
			if(generatedKeys.next()) {
				comment.setId(generatedKeys.getInt(1));
				result = generatedKeys.getInt(1);
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(conn);
		}
		return result;
	}
	
	public static int addCommentWithPicture(Comment comment) {
		int result = 0;
		Connection conn = null;
		ResultSet generatedKeys = null;
		
		byte[] array = Base64.getEncoder().encode(comment.getPicture().getBytes());
				
		Object values[] = {comment.getContent(), comment.getShareTime(), array, comment.getUserId(), comment.getPostId() };
		try {
			conn = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(conn, SQL_INSERT, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			if(preparedStatement.getUpdateCount() > 0) {
				result = 0;
			}
			if(generatedKeys.next()) {
				comment.setId(generatedKeys.getInt(1));
				result = generatedKeys.getInt(1);
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(conn);
		}
		return result;
	}
	
	public static ArrayList<Comment> getAllCommentsForPost(Integer postId){
		ArrayList<Comment> result = new ArrayList<Comment>();
		Connection conn = null;
		ResultSet rSet = null;
		Object[] values = {postId};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(conn, SQL_GET_COMMENTS_FOR_POST, false, values);
			rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				byte[] getDecodePicture = new byte[0];
				byte[] tmpPic = rSet.getBytes("picture");
				if(tmpPic != null) {
					 getDecodePicture = Base64.getDecoder().decode(tmpPic);
				} else {
					getDecodePicture = new byte[0];
				}
				result.add(new Comment(rSet.getInt("id"), rSet.getString("content"), rSet.getTimestamp("share_time"), new String(getDecodePicture), rSet.getInt("user_id"), rSet.getInt("post_id")));				
				
			}			
			preparedStatement.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(conn);
		}
		
		return result;
		
	}
}

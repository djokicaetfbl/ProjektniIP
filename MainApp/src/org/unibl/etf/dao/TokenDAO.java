package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.dto.Token;

public class TokenDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_RESET = "UPDATE token SET active=0 WHERE user_id=?";
	private static final String SQL_INSERT = "INSERT into token (token, creation_time, expiration_time, active, user_id) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_USERS_TOKEN = "SELECT * FROM token WHERE user_id = ? AND active = true";
	
	public static boolean reset(int userId) {
		boolean result = false;
		Connection connection = null;
		Object[] values = { userId};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_RESET, true, values);
			int queryResult = preparedStatement.executeUpdate();
			if(queryResult != 0) {
				result = true;
			}
			preparedStatement.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static boolean insert(Token token) {
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object[] values = {token.getToken(), token.getCreationTime(), token.getExpirationTime(), token.isActive(), token.getUserId()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if(preparedStatement.getUpdateCount() > 0) {
				result = true;
			}
			if(generatedKeys.next()) {
				token.setId(generatedKeys.getInt(1));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
	
	public static ArrayList<Token> getUsersToken(int id){
		ArrayList<Token> tokenArrayList = new ArrayList<Token>();
		Connection connection = null;
		ResultSet rSet = null;
		Object[] values = { id };
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SQL_SELECT_USERS_TOKEN, false, values);
			rSet = preparedStatement.executeQuery();
			if(rSet.next()) {
				tokenArrayList.add(new Token(rSet.getInt("id"), rSet.getString("token"), rSet.getTimestamp("creation_time"), rSet.getTimestamp("expiration_time"), rSet.getBoolean("active"), rSet.getInt("user_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return tokenArrayList;
	}
}

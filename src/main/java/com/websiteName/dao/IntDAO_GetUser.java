package com.websiteName.dao;

import java.sql.SQLException;
import java.util.Map;

import com.websiteName.dto.User;

public interface IntDAO_GetUser {

	public User getUserByEmail(String email) throws SQLException;

	public User getUserByUsername(String username) throws SQLException;
	
	public Map<String, Boolean> existsUsernameOrEmail(String username, String email) throws SQLException;

}

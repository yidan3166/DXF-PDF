package com.websiteName.bo;

import java.sql.SQLException;

import com.websiteName.dao.DAO_GetUser;
import com.websiteName.dto.User;

public class BO_GetUser {

	public User getUserByEmail(String email) {
		try {
			return new DAO_GetUser().getUserByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public User getUserByUsername(String username) {
		try {
			return new DAO_GetUser().getUserByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}

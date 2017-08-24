package com.websiteName.bo;

import java.sql.SQLException;

import com.websiteName.dao.DAO_User;
import com.websiteName.dto.User;

public class BO_User {
	
	public boolean addUser(User user){
		try {
			return new DAO_User().addUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

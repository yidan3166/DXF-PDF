package com.websiteName.bo;

import java.sql.SQLException;
import java.util.Map;

import com.websiteName.dao.DAO_GetUser;

public class BO_Check {

	public static void main(String[] args) {

		Map<String, Boolean> exists = new BO_Check().existsUsernameOrEmail("sanel1", "amel@amel");

		if (exists == null) {
			// email and username are free
			System.out.println("all free");
		} else if (exists.get("email") && exists.get("username")) {
			System.out.println("nothing free");
		} else if (exists.get("email")) {
			System.out.println("email NOT free");
		} else {
			System.out.println("username NOT free");
		}

	}

	public Map<String, Boolean> existsUsernameOrEmail(String username, String email) {
		try {
			return new DAO_GetUser().existsUsernameOrEmail(username, email);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean isPasswordLengthOK(String password) {
		int passwordLength = password.length();

		if (passwordLength > 7 && passwordLength < 30) {
			return true;
		}
		return false;
	}
	
}

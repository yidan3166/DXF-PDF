package com.websiteName.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.websiteName.dto.User;
import com.websiteName.util.DatabaseNames;
import com.websiteName.util.MyConnection;
import com.websiteName.util.PasswordEncryption;

public class DAO_GetUser implements IntDAO_GetUser, DatabaseNames {

	private Connection connection;

	// Method
	public User getUserByEmail(String email) throws SQLException {

		// Email encryption
		String encryptedEmail = new PasswordEncryption().encryptOtherText(email);
		// End of encryption

		String querry = "SELECT * FROM " + tableUsers + " WHERE email LIKE ?";

		try {
			connection = MyConnection.connect();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.setString(1, encryptedEmail);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int userId = resultSet.getInt(userIdDBN);
				String username = resultSet.getString(usernameDBN);
				String password = resultSet.getString(passwordDBN);

				PasswordEncryption encryption = new PasswordEncryption();
				String decryptedUsername = encryption.decryptOtherText(username);

				return new User(userId, decryptedUsername, email, password);
			}

		} catch (Exception e) {
			System.out.println("Ups, Error! Retrieving user didt't success.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return null;
	}

	// Method
	public User getUserByUsername(String username) throws SQLException {

		// username encryption
		String encryptedUsername = new PasswordEncryption().encryptOtherText(username);
		// End of encryption

		String querry = "SELECT * FROM " + tableUsers + " WHERE username LIKE ?";

		try {
			connection = MyConnection.connect();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.setString(1, encryptedUsername);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int userId = resultSet.getInt(userIdDBN);
				String email = resultSet.getString(emailDBN);
				String password = resultSet.getString(passwordDBN);

				PasswordEncryption encryption = new PasswordEncryption();
				String decryptedEmail = encryption.decryptOtherText(email);

				return new User(userId, username, decryptedEmail, password);
			}

		} catch (Exception e) {
			System.out.println("Ups, Error! Retrieving user didt't success.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return null;
	}

	// Method
	public Map<String, Boolean> existsUsernameOrEmail(String username, String email) throws SQLException {

		// Email and username encryption
		PasswordEncryption encryption = new PasswordEncryption();
		String encryptedEmail = encryption.encryptOtherText(email);
		String encryptedUsername = encryption.encryptOtherText(username);
		// End of encryption

		String querry = "SELECT username, email FROM " + tableUsers + " WHERE username LIKE ? OR email LIKE ?";

		Map<String, Boolean> exists = new HashMap<String, Boolean>();
		try {
			connection = MyConnection.connect();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.setString(1, encryptedUsername);
			preparedStatement.setString(2, encryptedEmail);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String usernameFromDB = resultSet.getString(usernameDBN);
				String emailFromDB = resultSet.getString(emailDBN);

				// Email and username DEcryption
				String getUsername = encryption.decryptOtherText(usernameFromDB);
				String getEmail = encryption.decryptOtherText(emailFromDB);
				// end of decryption

				if (getUsername.equals(username) && getEmail.equals(email)) {

					// email and username from the same profile
					exists.put("email", true);
					exists.put("username", true);
				} else if (getUsername.equals(username)) {

					// if one result equals username, username = TRUE
					if (resultSet.next()) {
						// if two results
						exists.put("email", true);
						exists.put("username", true);
					} else {
						// if only one result, only username exists
						exists.put("email", false);
						exists.put("username", true);
					}
				} else {

					// if one result doesn't equal username, email = TRUE
					if (resultSet.next()) {
						// if two results
						exists.put("email", true);
						exists.put("username", true);
					} else {
						// if only one result, only email exists
						exists.put("email", true);
						exists.put("username", false);
					}
				}

				return exists;
			}

		} catch (Exception e) {
			System.out.println("Ups, Error! Retrieving user didt't success.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return null;
	}

}

package com.websiteName.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.websiteName.dto.User;

import com.websiteName.util.*;

/**
 * Ima metoda -- 'mysqlTransactionExample(User user)' -- na kraju gdje ima
 * primjer kako se radi MySQL transakcija u javi
 * 
 */

/*
 * OVA sintaksa je za MySQL bazu na SERVERU:
 * 
 * CREATE DATABASE IF NOT EXISTS myTestDatabase CHARACTER SET utf8 COLLATE
 * utf8_unicode_ci; CREATE TABLE IF NOT EXISTS users (userId INT NOT NULL
 * AUTO_INCREMENT, username VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL,
 * password VARCHAR(100) NOT NULL, PRIMARY KEY (userId)) CHARACTER SET utf8
 * COLLATE utf8_unicode_ci;
 */

public class DAO_User implements IntDAO_User, DatabaseNames {

	private Connection connection;

	/* main method for testing purposes */
	public static void main(String[] args) throws SQLException {

		// Create a database and a table
		System.out.println("Database: " + new AAA_CreateDatabaseAndTable().createDatabase());
		System.out.println("Table: " + new AAA_CreateDatabaseAndTable().createTable());

		// Add a user
		User user = new User("amel", "amel@amel", "amelamel");
		System.out.println("User: " + user);
		System.out.println("User added: " + new DAO_User().addUser(user));

	}

	/* */
	/* */
	public boolean addUser(User user) throws SQLException {
		String username = user.getUsername();
		String email = user.getEmail();
		String password = user.getPassword();

		String querry = "INSERT INTO users (username,email,password) VALUES (?,?,?)";

		try {
			connection = MyConnection.connect();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			preparedStatement.executeUpdate();

			return true;
		} catch (Exception e) {
			System.out.println("Ups, Error! Adding contact didn't success");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return false;
	}

	/* */
	/* */
	public boolean mysqlTransactionExample(User user) throws SQLException {

		// execute a transaction in JAVA ////////////////////////////////
		// we didn't write SELECT * FROM ... because we don't need double
		// rows for uderId (from both tables)
		String querry1 = "CREATE TEMPORARY TABLE temptable1 AS "
				+ "SELECT usr.userId, usr.username, usr.email, usr.passw, usrvid.videoId FROM "
				+ "users usr LEFT JOIN userVideos usrvid ON " + "usr.userId = usrvid.userId WHERE "
				+ "usr.email LIKE ?";

		String querry2 = "SELECT * FROM temptable1 ta1 LEFT JOIN videos vid ON ta1.videoId = vid.videoId";

		try {
			connection = MyConnection.connect();
			
			if (connection != null) {

				// ----------execute transaction in java
				connection.setAutoCommit(false);

				PreparedStatement preparedStatement = connection.prepareStatement(querry1);
				PreparedStatement preparedStatement2 = connection.prepareStatement(querry2);

				preparedStatement.setString(1, user.getEmail());

				preparedStatement.executeUpdate();
				ResultSet resultSet = preparedStatement2.executeQuery();

				connection.commit();
				// -------------transaction complete

				if (resultSet.next()) {

					return true;
				} // if end
			}

		} catch (Exception e) {
			System.out.println("Ups, Error! Testing transaction didn't success");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}
			
		}
		return false;
	}

}

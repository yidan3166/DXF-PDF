package com.websiteName.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.websiteName.util.DatabaseNames;
import com.websiteName.util.MyConnection;

/*
 * This is just for testing purposes
 * 
 * OVA sintaksa je za MySQL bazu na SERVERU:
 * 
 * CREATE DATABASE IF NOT EXISTS myTestDatabase CHARACTER SET utf8 COLLATE utf8_unicode_ci;
 * CREATE TABLE IF NOT EXISTS users (userId INT NOT NULL AUTO_INCREMENT, username VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL, 
password VARCHAR(100) NOT NULL, PRIMARY KEY (userId)) CHARACTER SET utf8 COLLATE utf8_unicode_ci;
 */

public class AAA_CreateDatabaseAndTable implements DatabaseNames {

	private Connection connection;

	public static void main(String[] args) throws SQLException {

		// Create a database and a table
		System.out.println("Database: " + new AAA_CreateDatabaseAndTable().createDatabase());
		System.out.println("Table: " + new AAA_CreateDatabaseAndTable().createTable());
	}

	/* */
	/* */
	public boolean createDatabase() throws SQLException {

		String querry = "CREATE DATABASE IF NOT EXISTS " + myTestDatabase
				+ " CHARACTER SET utf8 COLLATE utf8_unicode_ci";

		try {
			connection = MyConnection.connectForDatabaseCreation();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.executeUpdate();

			System.out.println("Database created");
			return true;

		} catch (Exception e) {
			System.out.println("Ups, Error! Creating database didn't success");
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return false;
	}

	/* */
	/* */
	public boolean createTable() throws SQLException {

		String querry = "CREATE TABLE IF NOT EXISTS users (userId INT NOT NULL AUTO_INCREMENT, "
				+ "username VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL, "
				+ "password VARCHAR(100) NOT NULL, PRIMARY KEY (userId)) "
				+ "CHARACTER SET utf8 COLLATE utf8_unicode_ci ";

		try {
			connection = MyConnection.connect();

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			preparedStatement.executeUpdate();

			System.out.println("Table created");
			return true;

		} catch (Exception e) {
			System.out.println("Ups, Error! Creating table didn't success");
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return false;
	}

}

package com.websiteName.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection implements DatabaseNames {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static String hostname = "localhost"; // System.getProperty("RDS_HOSTNAME");

	private static String dataBaseName = myTestDatabase; // Insert your DB name
															// HERE

	private static final String CONN_STRING = "jdbc:mysql://" + hostname + ":3306/" + myTestDatabase
			+ "?characterEncoding=UTF-8&useSSL=true";

	private static Connection connection;

	public MyConnection() {

	}

	public static Connection connect() {
		try {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// This i ONLY for database creation
	public static Connection connectForDatabaseCreation() {
		try {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return connection = DriverManager.getConnection(
					"jdbc:mysql://" + hostname + ":3306/?characterEncoding=UTF-8&useSSL=true", USERNAME, PASSWORD);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

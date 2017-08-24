package com.websiteName.dao;

import java.sql.SQLException;
import com.websiteName.dto.User;

public interface IntDAO_User {

	public boolean addUser(User user) throws SQLException;

	/* An example of a transaction */
	public boolean mysqlTransactionExample(User user) throws SQLException;

}

package com.excilys.librarymanager.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

/**
 * <b>ConnectionManager</b>
 * Controller class to make the connection to the database
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro.sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-10
 */
public class ConnectionManager {
	private static final String DB_CONNECTION = "jdbc:h2:~/libraryManagerDatabase";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	private static JdbcDataSource datasource = null;

	private static void init() {
		if (datasource == null) {
			datasource = new JdbcDataSource();
			datasource.setURL(DB_CONNECTION);
			datasource.setUser(DB_USER);
			datasource.setPassword(DB_PASSWORD);
		}
	}

	
	/** 
	 * Returns the current connection
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		init();
		return datasource.getConnection();
	}

}

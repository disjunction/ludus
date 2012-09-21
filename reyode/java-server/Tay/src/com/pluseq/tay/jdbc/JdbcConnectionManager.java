package com.pluseq.tay.jdbc;


import java.sql.DriverManager;
import java.util.Properties;
import com.pluseq.tay.*;
//import com.pluseq.tay.sql.mysql.MysqlConnection;

public class JdbcConnectionManager {

	protected SqlConnectionInterface makeJdbcConnection(Properties properties) throws Exception {
		String driver = properties.getProperty("tay.connection.driver", "com.mysql.jdbc.Driver");
		
		// System.out.print("registering " + driver + "... ");
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
		// System.out.println("ok");
		
		
		// sample url definition in metaword.properties
		// tay.connection.url = jdbc:mysql://localhost/metaword?user=root&password=root
		
		String url = properties.getProperty("tay.connection.url");
		if (null == url) {
			throw new TayException("tay.connection.url not defined!");
		}
			    
	    if (driver.equals("com.mysql.jdbc.Driver")) {
	    	Class.forName(driver).newInstance();
	    	JdbcConnectionInterface con = (JdbcConnectionInterface)Class.forName("com.pluseq.tay.sql.mysql.MysqlConnection").newInstance();
	    	con.setConnection(DriverManager.getConnection(url));
	    	return con;
	    } else if (driver.equals("org.sqlite.JDBC")) {
	    	Class.forName(driver).newInstance();
	    	JdbcConnectionInterface con = (JdbcConnectionInterface)Class.forName("com.pluseq.tay.sql.sqlite.SqliteConnection").newInstance();
	    	con.setConnection(DriverManager.getConnection(url));
	    	return con;
	    }
		throw new TayException("no vivid implementation for driver " + driver);
	}
	public SqlConnectionInterface makeConnection(Properties properties) throws Exception {
		String type = properties.getProperty("tay.connection.type", "jdbc");
		if (type.equals("jdbc")) {
			return makeJdbcConnection(properties);
		}
		throw new TayException("connection type '" + type + "' is not supported. Please change tay.connection.type" );
	}
}

package com.pluseq.vivid;
import java.sql.DriverManager;
import java.util.Properties;

import com.pluseq.vivid.mysql.VividMySQLConnection;

public class VividConnectionManager {
	protected VividConnectionInterface makeJdbcConnection(Properties properties) throws Exception {
		String driver = properties.getProperty("vivid.connection.driver", "com.mysql.jdbc.Driver");
		
		// System.out.print("registering " + driver + "... ");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// System.out.println("ok");
		
		
		// sample url definition in metaword.properties
		// vivid.connection.url = jdbc:mysql://localhost/metaword?user=root&password=root
		
		String url = properties.getProperty("vivid.connection.url");
		if (null == url) {
			throw new VividException("vivid.connection.url not defined!");
		}
		
		// System.out.print("connecting... ");
		java.sql.Connection connection = DriverManager.getConnection(url);
	    // System.out.println("ok");
	    
	    if (driver.equals("com.mysql.jdbc.Driver")) {
	    	return new VividMySQLConnection(connection);
	    }
		throw new VividException("no vivid implementation for driver " + driver);
	}
	public VividConnectionInterface makeConnection(Properties properties) throws Exception {
		String type = properties.getProperty("vivid.connection.type", "jdbc");
		if (type.equals("jdbc")) {
			return makeJdbcConnection(properties);
		}
		throw new VividException("connection type '" + type + "' is not supported. Please change vivid.connection.type" );
	}
}

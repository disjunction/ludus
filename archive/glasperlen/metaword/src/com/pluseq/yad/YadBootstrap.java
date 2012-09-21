package com.pluseq.yad;

import java.io.File;
import java.sql.SQLException;
import java.util.Properties;

import com.pluseq.metaword.runtime.MetawordBootstrap;
import com.pluseq.metaword.storage.*;
import com.pluseq.vivid.*;
import com.pluseq.vivid.mysql.VividMySQLConnection;

public class YadBootstrap extends MetawordBootstrap {
	public void init() throws Exception {
		Properties properties = getProperties();
		
		VividSqlConnectionInterface con;
		try {
			con = getSqlConnection(properties);
		} catch (SQLException e) {
			throw new RuntimeException("couldnt connect to DB, check metaword.properties", e);
		}
		VividTableRegistry tr = Vivid.getTableRegistry();
		tr.addTable(new WordSqlTable(con));
		tr.addTable(new RelSqlTable(con));
		tr.addTable(new SimpleTransSqlTable(con));
	}
	
	private static YadBootstrap instance;
	public static YadBootstrap _() {
		return null == instance? instance = new YadBootstrap() : instance;
	}
}

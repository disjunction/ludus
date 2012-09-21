package com.pluseq.metaword.runtime;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pluseq.vivid.*;

public abstract class MetawordBootstrap {
	
	private static Logger logger = MetawordRegistry.getLogger(MetawordBootstrap.class.getName());

	
	protected static String[] defaultPaths = {"~/.metaword", "/etc/metaword", "/usr/local/share/metaword"};
	protected static String defaultFilename = "metaword.properties";
	
	protected File findProperties() throws IOException {
		return findProperties(defaultFilename);
	}
	
	protected File findProperties(String fileName) throws IOException {
		for (String path : defaultPaths) {
			path = path.replaceAll("~", System.getProperty("user.home"));
			File testFile = new File(path + "/" + fileName);
			if (testFile.exists()) {
				return testFile;
			}
		}
		throw new FileNotFoundException("cannot find metaword.properties");
	}
	
	protected Properties getProperties() {
		Properties properties;
		try {
			File file = findProperties();
			properties = new Properties();
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
			properties = getDefaultProperties();
		} catch (Exception e) {
			logger.warning("error while loading file: " + e.getMessage());
			properties = getDefaultProperties();
		}
		return properties;
	}
	
	protected Properties getDefaultProperties() {
		Properties properties = new Properties();
		properties.setProperty("vivid.connection.url", "jdbc:mysql://localhost/metaword?user=metaword&password=metaword");
		return properties;
	}
	
	protected VividSqlConnectionInterface getSqlConnection(Properties properties) throws Exception {
		VividConnectionManager cm = Vivid.getConnManager();
		VividConnectionInterface vc = cm.makeConnection(properties);
		return (VividSqlConnectionInterface)vc;
	}
	
	public void init() throws Exception {
		MetawordRegistry.setProperties(getProperties());
		MetawordRegistry.setLoggerLevel(Level.INFO);
	}
}

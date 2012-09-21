package com.pluseq.reyad.cli;

import java.io.*;
import java.util.*;

import com.pluseq.reyad.*;
import com.pluseq.reyad.jdbc.StorageJdbc;
import com.pluseq.tay.*;


public class Bootstrap {
    protected static String[] defaultPaths = {"~/.reyad", "/etc/reyad", "/usr/local/share/reyad"};
    protected static String defaultFilename = "reyad.properties";
    
    protected void log(String str)
    {
    	System.out.println(str);
    }
    
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
            throw new FileNotFoundException("cannot find " + defaultFilename);
    }
    
    protected Properties getProperties() {
            Properties properties;
            try {
                    File file = findProperties();
                    properties = new Properties();
                    properties.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                    log(e.getMessage());
                    properties = getDefaultProperties();
            } catch (Exception e) {
                    log("error while loading file: " + e.getMessage());
                    properties = getDefaultProperties();
            }
            return properties;
    }
    protected Properties getDefaultProperties() {
        Properties properties = new Properties();
		// properties.setProperty("tay.connection.driver", "com.mysql.jdbc.Driver");
		// properties.setProperty("tay.connection.url", "jdbc:mysql://localhost/reyad?user=reyad&password=reyad");
		
		properties.setProperty("tay.connection.driver", "org.sqlite.JDBC");
		properties.setProperty("tay.connection.url", "jdbc:sqlite://home/or/reyad.sqlite");
        return properties;
	}
	
	protected SqlConnectionInterface getConnection(Properties properties) throws Exception {
		StorageJdbc s = new StorageJdbc();
		s.init(properties);
		Storage.setInstance(s);
		return s.getConnection();
	}
	
	public void init() throws Exception {
	    Properties p = getProperties();
		Reyad.setProperties(p);
		getConnection(p);
	}
}

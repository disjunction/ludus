package com.pluseq.metaword.runtime;
import java.util.Properties;
import java.util.logging.*;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class MetawordRegistry {
	private static Level loggerLevel = Level.INFO;
	private static Properties properties = null;
	private static Hashtable objects = new Hashtable();
	
	static void setLoggerLevel(Level level) {
		loggerLevel = level;
	}
	
	static void setProperties(Properties properties) {
		MetawordRegistry.properties = properties;
	}
	
	static Properties getProperties() {
		return properties;
	}

	public static Logger getLogger(String name) {
		Logger logger = Logger.getLogger(MetawordBootstrap.class.getName());
		logger.setLevel(loggerLevel);
		return logger;
	}
	
	public static void put(String key, Object o) {
		objects.put(key, o);
	}
	
	public static Object get(String key) {
		return objects.get(key);
	}
}

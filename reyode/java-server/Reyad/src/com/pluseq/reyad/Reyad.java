package com.pluseq.reyad;

import java.util.Properties;

public class Reyad {
	static Properties properties;
	
	public static Storage getStorage()
	{
		return Storage._();
	}
	
	public static void setStorage(Storage storage)
	{
		Storage.setInstance(storage);
	}
	
	public static void setProperties(Properties properties)
	{
		Reyad.properties = properties;
	}
}

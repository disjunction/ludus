package com.pluseq.vivid;

import java.util.*;

public class Vivid {	
	protected static VividTableRegistry tableRegistry = new VividTableRegistry();
	protected static VividConnectionManager connectionManager = new VividConnectionManager();
	
	public static VividTableRegistry getTableRegistry()
	{
		return Vivid.tableRegistry;
	}
	
	public static VividConnectionManager getConnManager()
	{
		return Vivid.connectionManager;
	}
}

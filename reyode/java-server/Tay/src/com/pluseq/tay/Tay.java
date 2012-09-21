package com.pluseq.tay;


public class Tay {
	protected static TableRegistry tableRegistry = new TableRegistry();
	protected static ConnectionRegistry connectionRegistry = new ConnectionRegistry();
	
	public static TableRegistry getTableRegistry()
	{
		return Tay.tableRegistry;
	}
	
	public static ConnectionRegistry getConnManager()
	{
		return Tay.connectionRegistry;
	}
}

package com.pluseq.yad;
import com.pluseq.metaword.storage.MetawordStorage;
import com.pluseq.vivid.Vivid;

public class YadStorage {
	public static int SimpleTransTableId = 250;
	
	public SimpleTransSqlTable getSimpleTransTable()
	{
		return (SimpleTransSqlTable) Vivid.getTableRegistry().getTable(YadStorage.SimpleTransTableId);
	}
	
	protected static YadStorage instance = new YadStorage();
	public static YadStorage _()
	{
		return instance;
	}
}

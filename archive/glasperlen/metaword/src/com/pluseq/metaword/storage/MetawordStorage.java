package com.pluseq.metaword.storage;

import com.pluseq.metaword.mwq.MWQStatement;
import com.pluseq.vivid.Vivid;

public class MetawordStorage {
	
	public static int WordTableId = 200;
	public static int RelTableId = 201;
	public static int SimpleTransTableId = 201;
	
	public WordSqlTable getWordTable()
	{
		return (WordSqlTable) Vivid.getTableRegistry().getTable(MetawordStorage.WordTableId);
	}
	
	public RelSqlTable getRelTable()
	{
		return (RelSqlTable) Vivid.getTableRegistry().getTable(MetawordStorage.RelTableId);
	}
	
	public MWQInterface createMWQ() {
		return new MWQStatement();
	}
	
	protected static MetawordStorage instance = new MetawordStorage();
	public static MetawordStorage _()
	{
		return instance;
	}
}

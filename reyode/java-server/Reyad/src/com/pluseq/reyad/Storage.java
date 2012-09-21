package com.pluseq.reyad;

import com.pluseq.reyad.sql.RelTable;
import com.pluseq.tay.SqlConnectionInterface;
import com.pluseq.tay.TableRegistry;

abstract public class Storage extends TableRegistry {
	protected static Storage storage;

	public static int Word = 10001;
	public static int Rel = 10002;
	public static int SimpleTrans = 10020;

	static public Storage _() {
		if (null == storage) {
			throw new RuntimeException("singleton is called before the storage was set");
		}
		return storage;
	}
		
	public static void setInstance(Storage storage)
	{
		Storage.storage = storage;
	}

	abstract public SimpleTransTableInterface getSimpleTrans();
	abstract public RelTableInterface getRel();
	abstract public SqlConnectionInterface getConnection();
}

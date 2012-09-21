package com.pluseq.tay;
import java.util.Hashtable;

public interface TableInterface {
	/**
	 * this ID is used by Tay for the registry
	 */
	//public int getTableId();
	public boolean save(Object object);
	public Object get(String id) throws TayException;
	public Hashtable<String, Object> toHashtable(Object object);
}

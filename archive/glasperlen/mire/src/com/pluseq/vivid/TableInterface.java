package com.pluseq.vivid;

public interface TableInterface {	
	/**
	 * this ID is used by Vivid for the registry
	 */
	public int getTableId();
	
	public boolean save(HolderInterface holder);
	public HolderInterface holderById(String id) throws Exception;
}

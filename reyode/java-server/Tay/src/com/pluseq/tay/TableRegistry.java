package com.pluseq.tay;
import java.util.*;

public class TableRegistry {
	protected Map<Integer, TableInterface> tables = Collections.synchronizedMap(new HashMap<Integer, TableInterface>());
	
	public void set(TableInterface table, int tableId)
	{
		tables.put(tableId, table);
	}
	
	public TableInterface get(int tableId)
	{
		return tables.get(tableId);
	}
}

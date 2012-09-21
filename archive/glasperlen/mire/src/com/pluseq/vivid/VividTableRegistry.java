package com.pluseq.vivid;

import java.util.*;

public class VividTableRegistry {
	protected Map<Integer, TableInterface> tables = Collections.synchronizedMap(new HashMap<Integer, TableInterface>());
	
	public void addTable(TableInterface table)
	{
		tables.put(table.getTableId(), table);
	}
	
	public TableInterface getTable(int tableId)
	{
		return tables.get(tableId);
	}
}

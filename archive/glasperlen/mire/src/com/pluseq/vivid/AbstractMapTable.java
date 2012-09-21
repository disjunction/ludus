package com.pluseq.vivid;

import java.util.Hashtable;

@SuppressWarnings("serial")
public abstract class AbstractMapTable 
	extends Hashtable<String, HolderInterface>
	implements TableInterface 
{
	@Override
	public HolderInterface holderById(String id) {
		return get(id);
	}

	@Override
	public boolean save(HolderInterface holder) {
		put(holder.getStringId(), holder);
		return true;
	}

}

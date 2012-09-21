package com.pluseq.mire.storage;
import com.pluseq.vivid.AbstractMapTable;


public class SessionMapTable 
	extends AbstractMapTable
	implements SessionTableInterface
{
	private static final long serialVersionUID = -1046146916054384810L;

	@Override
	public int getTableId() {
		return SessionTableInterface.TableId;
	}
}

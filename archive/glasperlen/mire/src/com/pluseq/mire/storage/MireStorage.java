package com.pluseq.mire.storage;

import com.pluseq.vivid.Vivid;

public class MireStorage {
	protected static MireStorage instance = new MireStorage();
	
	public UserTableInterface getUserTable()
	{
		return (UserTableInterface) Vivid.getTableRegistry().getTable(UserTableInterface.TableId);
	}
	
	public SessionTableInterface getSessionTable()
	{
		return (SessionTableInterface) Vivid.getTableRegistry().getTable(SessionTableInterface.TableId);
	}
	
	public LocationTable getLocationTable()
	{
		return (LocationTable) Vivid.getTableRegistry().getTable(335);
	}
	
	public static MireStorage _()
	{
		return instance;
	}
}

package com.pluseq.tay;

import java.util.*;

public class ConnectionRegistry {
	protected Map<Integer, ConnectionInterface> cons = Collections.synchronizedMap(new HashMap<Integer, ConnectionInterface>());
	
	public void set(ConnectionInterface con)
	{
		cons.put(con.getConnectionId(), con);
	}
	
	public ConnectionInterface get(int con)
	{
		return cons.get(con);
	}
}

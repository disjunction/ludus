package com.pluseq.mire.storage;
import java.util.ArrayList;
import java.util.Hashtable;

import com.pluseq.mire.core.MireSession;
import com.pluseq.vivid.*;

public class SessionSqlTable extends AbstractSqlTable implements SessionTableInterface {
	//private String createSql = "create table MireSession (sessionId varchar(50), login varchar(32), createTime timestamp, pingTime timestamp, unique key(sessionid))";
	
	public SessionSqlTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("sessionId"));
		l.add(new VividField("login"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "MireSession";
	}

	@Override
	public int getTableId() {
		return SessionTableInterface.TableId;
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		MireSession s = new MireSession(hashtable.get("login").toString(), hashtable.get("sessionId").toString());
		return new SessionHolder(s);
	}
}

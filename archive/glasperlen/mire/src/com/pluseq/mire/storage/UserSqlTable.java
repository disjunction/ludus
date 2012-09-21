package com.pluseq.mire.storage;
import java.util.*;

import com.pluseq.mire.core.MireUser;
import com.pluseq.vivid.*;

public class UserSqlTable extends AbstractSqlTable implements UserTableInterface {
	// create table MireUser (login varchar(32),password varchar(32), name varchar(127) , email varchar(127) );
	
	public UserSqlTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("login"));
		l.add(new VividField("password"));
		l.add(new VividField("name"));
		l.add(new VividField("email"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "MireUser";
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		MireUser u = new MireUser();
		u.login = (String)hashtable.get("login");
		u.password = (String)hashtable.get("password");
		u.name = (String)hashtable.get("name");
		u.email = (String)hashtable.get("email");
		return new UserHolder(u);
	}

	@Override
	public int getTableId() {
		return UserTableInterface.TableId;
	}
}

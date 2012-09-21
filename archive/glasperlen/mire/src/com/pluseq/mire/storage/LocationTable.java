package com.pluseq.mire.storage;
import java.util.ArrayList;
import java.util.Hashtable;

import com.pluseq.vivid.*;

public class LocationTable extends AbstractSqlTable {

	public LocationTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("id"));
		l.add(new VividField("title"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "Location";
	}

	@Override
	public int getTableId() {
		return 335;
	}

	@Override
	public boolean save(HolderInterface holder) {
		return super.save((HashtableHolderInterface)holder);
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		// TODO Auto-generated method stub
		return null;
	}
}

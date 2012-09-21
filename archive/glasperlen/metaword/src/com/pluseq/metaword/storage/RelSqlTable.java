package com.pluseq.metaword.storage;
import com.pluseq.metaword.core.*;
import java.util.*;
import com.pluseq.vivid.*;

public class RelSqlTable  extends AbstractSqlTable {

	public RelSqlTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("subjSign"));
		l.add(new VividField("objSign"));
		l.add(new VividField("typeSign"));
		l.add(new VividField("value"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "Rel";
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		Rel rel = new Rel((String)hashtable.get("objSign"),
						  (String)hashtable.get("objSign"),
						  (String)hashtable.get("typeSign"),
						  Integer.valueOf((String)hashtable.get("value")));
		return new RelHolder(rel);
	}
	
	@Override
	public int getTableId() {
		return MetawordStorage.RelTableId;
	}
	
	@Override
	public HolderInterface holderById(String id) throws VividException {
		throw new VividException("Rel has no simple primary key");
	}
}
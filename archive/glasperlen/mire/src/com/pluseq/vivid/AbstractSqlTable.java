package com.pluseq.vivid;
import java.util.*;

public abstract class AbstractSqlTable implements TableInterface {
	protected VividSqlConnectionInterface con;
	
	public AbstractSqlTable(VividSqlConnectionInterface vividConnection)
	{
		this.con = vividConnection;
	}
	
	protected abstract String getTableName();
	protected abstract ArrayList<VividField> getTableFields();
	
	/**
	 * By default the first field is the ID
	 * @return String
	 */
	public String getIdFieldName() {
		return this.getTableFields().get(0).name;
	}
	
	@Override
	public boolean save(HolderInterface holder) {
		if (holder instanceof SavableInterface) {
			if (((SavableInterface) holder).getSaved()) {
				return update((HashtableHolderInterface)holder);
			} else {
				return insert((HashtableHolderInterface)holder);
			}			
		} else {
			// this object cannot be saved!
			return false;
		}
	}
	
	public boolean insert(HashtableHolderInterface holder) {
		Hashtable<String, Object> ht = holder.toHashtable();
		String sql = con.getSqlBuilder().buildInsert(getTableName(), ht);
		con.execute(sql);
		return true;
	}

	public boolean replace(HashtableHolderInterface holder) {
		Hashtable<String, Object> ht = holder.toHashtable();
		String sql = con.getSqlBuilder().buildInsert(getTableName(), ht, "replace into");
		con.execute(sql);
		return true;
	}
	
	public boolean update(HashtableHolderInterface holder) {
		String id = holder.getStringId();
		if (null == id) {
			return false;
		}
		Hashtable<String, Object> ht = holder.toHashtable();
		String condition = getIdFieldName() + " = '" + VividSqlBuilder.escapeOnly(holder.getStringId()) + "'"; 
		String sql = con.getSqlBuilder().buildUpdate(getTableName(), ht, condition);
		con.execute(sql);
		return true;
	}
	
	public boolean delete(HolderInterface holder) {
		String id = holder.getStringId();
		if (null == id) {
			return false;
		}
		String condition = getIdFieldName() + " = '" + VividSqlBuilder.escapeOnly(holder.getStringId()) + "'";
		String sql = con.getSqlBuilder().buildDelete(getTableName(), condition);
		con.execute(sql);
		return true;
	}
	
	public HolderInterface makeHolder(ArrayList<VividField> fields, String[] values) {
		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
		int i = 0;
		for (VividField f : fields) {
			String v = values[i++];
			if (v != null) {
				hashtable.put(f.name, v);
			}
		}
		
		HolderInterface holder = makeHolder(hashtable);
		
		if (holder instanceof SavableInterface) {
			((SavableInterface) holder).setSaved(true);
		}
		
		return holder;
	}
	
	abstract public HolderInterface makeHolder(Hashtable<String, Object> hashtable);
	
	public HolderInterface holderById(String id) throws Exception {
		String sql = con.getSqlBuilder().buildSelectById(this.getTableName(),
																	 this.getIdFieldName(),
																	 id);
		try {
			VividResultSet rs = con.getAllAsStrings(sql);
			if (null == rs) {
				return null;
			}
			if (rs.stringRows.size() == 0) {
				return null;
			}
			return makeHolder(rs.fields, rs.stringRows.get(0));
		} catch (Exception e) {
			System.out.println("cant get all");
			e.printStackTrace();
		}
		return null;
	}
	
	public VividList query(String sql) throws Exception {
		VividResultSet rs = con.getAllAsStrings(sql);
		VividList l = new VividList();
		for (String[] row : rs.stringRows) {
			l.add(this.makeHolder(rs.fields, row));
		}
		return l;
	}
	
	public void truncate() {
		con.execute("truncate " + getTableName());
	}
	
	public VividSqlConnectionInterface getConnection() {
		return con;
	}
}

package com.pluseq.tay;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;


public abstract class SqlTable implements TableInterface {
	
	public abstract Object fromRow(String row[]);
	
	protected SqlConnectionInterface con;
	
	public SqlTable(SqlConnectionInterface con)
	{
		this.con = con;
	}
	
	protected abstract String getTableName();
	protected TableSchema schema;

	/**
	 * By default the first field is the ID
	 * @return String
	 */
	public String getIdFieldName() {
		return schema.getFields().get(0).name;
	}
	
	@Override
	public boolean save(Object object) {
		if (object instanceof Savable) {
			if (((Savable) object).getSaved()) {
				return update((Savable)object);
			} else {
				return insert((Identified)object);
			}			
		} else {
			// this object cannot be saved!
			return false;
		}
	}
	
	public boolean insert(Identified object) {
		Hashtable<String, Object> ht = toHashtable(object);
		String sql = con.getSqlBuilder().buildInsert(getTableName(), ht);
		con.execute(sql);
		return true;
	}

	public boolean replace(Identified object) {
		Hashtable<String, Object> ht = toHashtable(object);
		String sql = con.getSqlBuilder().buildInsert(getTableName(), ht, "replace into");
		con.execute(sql);
		return true;
	}
	
	public boolean update(Savable object) {
		String id = object.getStringId();
		if (null == id) {
			return false;
		}
		Hashtable<String, Object> ht = toHashtable(object);
		String condition = getIdFieldName() + " = '" + con.getSqlBuilder().escapeOnly(object.getStringId()) + "'"; 
		String sql = con.getSqlBuilder().buildUpdate(getTableName(), ht, condition);
		con.execute(sql);
		return true;
	}
	
	public boolean delete(Identified object) {
		String id = object.getStringId();
		if (null == id) {
			return false;
		}
		String condition = getIdFieldName() + " = '" + con.getSqlBuilder().escapeOnly(object.getStringId()) + "'";
		String sql = con.getSqlBuilder().buildDelete(getTableName(), condition);
		con.execute(sql);
		return true;
	}
	
	public Object makeRow(ArrayList<Field> fields, String[] values) {
		/*
		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
		int i = 0;
		ArrayList<String> values = new ArrayList<String>();
		for (Field f : fields) {
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
		*/
		return null;
	}

	public ArrayList<Object> find(String sql) throws TayException, SQLException {
		TableResultSet rs = con.select(sql);
		ArrayList<Object> l = new ArrayList<Object>();
		for (String[] row : rs.stringRows) {
			l.add(fromRow(row));
		}
		return l;
	}
	
	public void truncate() {
		con.execute("truncate " + getTableName());
	}
	
	public SqlConnectionInterface getConnection() {
		return con;
	}
	
	
}

package com.pluseq.reyad.sql;

import java.util.Hashtable;

import com.pluseq.reyad.Rel;
import com.pluseq.reyad.RelTableInterface;
import com.pluseq.tay.SqlBuilder;
import com.pluseq.tay.SqlConnectionInterface;
import com.pluseq.tay.SqlTable;
import com.pluseq.tay.TableResultSet;
import com.pluseq.tay.TayException;

public class RelTable extends SqlTable implements RelTableInterface {

	public RelTable(SqlConnectionInterface con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object get(String id) throws TayException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, Object> toHashtable(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromRow(String[] row) {
		Rel r = new Rel();
		r.subjSign = row[0];
		r.objSign = row[1];
		r.typeSign = row[2];
		r.value = Integer.parseInt(row[3]);
		return r;
	}

	@Override
	protected String getTableName() {
		return "Rel";
	}
	
	@Override
	public Rel getBySubjectType(String subjSign, String typeSign) throws Exception {
		SqlBuilder b = getConnection().getSqlBuilder();
		String sql = "select * from " + this.getTableName() + " where " +
					 "subjSign=" + b.escape(subjSign) + " and " +
					 "typeSign=" + b.escape(typeSign);
		
		TableResultSet rs = con.select(sql);
		if (rs.stringRows.size() == 0) return null;
		Rel r = (Rel) this.fromRow(rs.stringRows.get(0));
		return r;
	}

}

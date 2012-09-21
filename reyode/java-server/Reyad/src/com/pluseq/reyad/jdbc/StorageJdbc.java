package com.pluseq.reyad.jdbc;

import java.util.Properties;

import com.pluseq.reyad.Storage;
import com.pluseq.reyad.sql.RelTable;
import com.pluseq.reyad.sql.SimpleTransTable;
import com.pluseq.tay.SqlConnectionInterface;
import com.pluseq.tay.jdbc.JdbcConnectionManager;

public class StorageJdbc extends Storage {
	private SqlConnectionInterface c;

	public void init(Properties properties) throws Exception
	{
		JdbcConnectionManager cm = new JdbcConnectionManager();
		c = cm.makeConnection(properties);
		
		set(new SimpleTransTable(c), SimpleTrans);
		set(new RelTable(c), Rel);
	}
	
	public void init() throws Exception
	{
		Storage.setInstance(this);
		Properties properties = new Properties();
		properties.setProperty("tay.connection.url", "jdbc:mysql://localhost/metaword?user=metaword&password=metaword");
		init(properties);
	}
	
	@Override
	public SimpleTransTable getSimpleTrans()
	{
		return (SimpleTransTable) get(SimpleTrans);
	}
	
	@Override
	public RelTable getRel()
	{
		return (RelTable) get(Rel);
	}

	@Override
	public SqlConnectionInterface getConnection() {
		return c;
	}
}

package com.pluseq.tay;

import java.sql.SQLException;

public interface SqlConnectionInterface extends ConnectionInterface{
	public TableResultSet select(String sql) throws SQLException, TayException;
	public boolean execute(String sql);
	public SqlBuilder getSqlBuilder();
}
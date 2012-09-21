package com.pluseq.vivid;
import java.sql.Connection;
import java.sql.ResultSet;

public interface VividSqlConnectionInterface extends VividConnectionInterface {
	public void setConnection(Connection con);
	
	public VividResultSet getAllAsStrings(String sql) throws Exception;
	public boolean execute(String sql);
	public ResultSet query(String sql);
	public VividSqlBuilder getSqlBuilder();
}

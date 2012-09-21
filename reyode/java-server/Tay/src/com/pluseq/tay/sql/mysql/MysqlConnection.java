package com.pluseq.tay.sql.mysql;

import com.pluseq.tay.*;

import java.sql.*;
import java.util.logging.*;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class MysqlConnection implements JdbcConnectionInterface {
	protected Connection connection;
	protected SqlBuilder sqlBuilder = new SqlBuilder();
	protected Logger logger; 
	private Statement st;
	
	public MysqlConnection(Connection con) {
		this();
		setConnection(con);
	}

	public MysqlConnection(){
		logger = Logger.getLogger(MysqlConnection.class.getName());
		logger.setLevel(Level.FINEST);
	}
	
	@Override
	public int getConnectionId()
	{
		return 1;
	}
	
	@Override
	public void setConnection(Connection con) {
		connection = con;
		try {
			st = connection.createStatement();
		} catch (Exception e) {}
	}

	protected ResultSet executeQuery(String sql) throws SQLException, TayException
	{
		// logger.fine(sql);
		try {
			return st.executeQuery(sql);
		} catch (MySQLSyntaxErrorException e) {
			throw new TayException("syntax error in sql: " + sql, e);
		}
	}
	
	public boolean execute(String sql) {
		try {
			logger.fine(sql);
			st.executeUpdate(sql);
		} catch (MysqlDataTruncation e) {
			logger.info(e.toString());
		} catch (SQLException e) {
			logger.warning(e.toString());
			return false;
		}
		return true;
	}
	
	public ResultSet query(String sql) {
		try {
			return executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TayException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TableResultSet select(String sql) throws SQLException, TayException {
		TableResultSet vrs = new TableResultSet();
		
		ResultSet         rs = executeQuery(sql);		
		ResultSetMetaData rmd = rs.getMetaData();
		int fieldCount = rmd.getColumnCount();
        for (int i = 0; i < fieldCount; i++) {
        	vrs.fields.add(new Field(rmd.getColumnName(i+1)));
        }
        
        while(rs.next()){
            String[] row = new String[fieldCount];
            for (int i=0; i < fieldCount; i++) {
            	try {
            		row[i] = rs.getString(i+1);
            	} catch (SQLException e) {
            		row[i] = null;
            	}
            }
            vrs.stringRows.add(row);
        }
		
		return vrs;
	}

	@Override
	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}
}

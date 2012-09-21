package com.pluseq.vivid.mysql;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.pluseq.vivid.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VividMySQLConnection implements VividSqlConnectionInterface{

	protected Connection connection;
	protected VividSqlBuilder sqlBuilder = new VividSqlBuilder();
	protected Logger logger; 
	private Statement st;
	
	public VividMySQLConnection(Connection con) {
		this();
		setConnection(con);
	}

	public VividMySQLConnection(){
		logger = Logger.getLogger(VividMySQLConnection.class.getName());
		logger.setLevel(Level.FINEST);
	}
	
	@Override
	public void setConnection(Connection con) {
		connection = con;
		try {
			st = connection.createStatement();
		} catch (Exception e) {}
	}

	protected ResultSet executeQuery(String sql) throws SQLException, VividException
	{
		// logger.fine(sql);
		try {
			return st.executeQuery(sql);
		} catch (MySQLSyntaxErrorException e) {
			throw new VividException("syntax error in sql: " + sql, e);
		}
	}
	
	@Override
	public boolean execute(String sql) {
		try {
			// Statement st = connection.createStatement();
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
	
	@Override
	public ResultSet query(String sql) {
		try {
			return executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (VividException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VividResultSet getAllAsStrings(String sql) throws SQLException, VividException {
		VividResultSet vrs = new VividResultSet();
		
		ResultSet         rs = executeQuery(sql);		
		ResultSetMetaData rmd = rs.getMetaData();
		int fieldCount = rmd.getColumnCount();
        for (int i = 0; i < fieldCount; i++) {
        	vrs.fields.add(new VividField(rmd.getColumnName(i+1)));
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
	public VividSqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}
}

package com.pluseq.tay;
import java.util.*;

public class SqlBuilder {
	/**
	 * Converts String[] to 'Peter','John','Marry'
	 */
	public String escape(String[] array){
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (String i : array) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append(escape(i));
		}
		return sb.toString();
	}

	public String random() {
		return "RAND()";
	}
	
	public String escape(String s){
		return "'" + escapeOnly(s) + "'";
	}
	
	/**
	 * Static String formatting and query routines.
	 * Copyright (C) 2001-2005 Stephen Ostermiller
	 * http://ostermiller.org/contact.pl?regarding=Java+Utilities
	 */
	public String escapeOnly(String s){
	    int length = s.length();
	    int newLength = length;

	    for (int i=0; i<length; i++){
	      char c = s.charAt(i);
	      switch(c){
	        case '\\':
	        case '\"':
	        case '\'':
	        case '\0':{
	          newLength += 1;
	        } break;
	      }
	    }
	    if (length == newLength){
	      // nothing to escape in the string
	      return s;
	    }
	    StringBuffer sb = new StringBuffer(newLength);
	    for (int i=0; i<length; i++){
	      char c = s.charAt(i);
	      switch(c){
	        case '\\':{
	          sb.append("\\\\");
	        } break;
	        case '\"':{
	          sb.append("\\\"");
	        } break;
	        case '\'':{
	          sb.append("\\\'");
	        } break;
	        case '\0':{
	          sb.append("\\0");
	        } break;
	        default: {
	          sb.append(c);
	        }
	      }
	    }
	    return sb.toString();
	}
	
	
	public String buildSelectById(String tableName, String idFieldName, String idValue)
	{
		return "select * from " + tableName + " where " + idFieldName + " = " + escape(idValue);
	}
	
	public String buildInsert(String tableName, Hashtable<String,Object> values)
	{
		return buildInsert(tableName, values, "insert into");
	}
	
	public String buildInsert(String tableName, Hashtable<String,Object> values, String prefix)
	{
		Operation p = Operation.fromValues(values);
		StringBuffer strBuf = new StringBuffer(prefix + " ");
		strBuf.append(tableName);
		strBuf.append("(");
		
		for (Field field : p.whatFields) {
			strBuf.append(field.name);
			strBuf.append(",");
		}
		strBuf.delete(strBuf.length()-1, strBuf.length());
		strBuf.append(") values (");
		
		for (String value : p.whatValues) {
			strBuf.append("'" + escapeOnly(value) + "',");
		}
		strBuf.delete(strBuf.length()-1, strBuf.length());
		strBuf.append(")");
		return strBuf.toString();
	}

	public String buildUpdate(String tableName, Hashtable<String, Object> values, String condition) {
		if (values.size() == 0) {
			return null;
		}
		
		StringBuffer b = new StringBuffer("update ");
		b.append(tableName);
		b.append(" set ");
		for (String key : values.keySet()) {
			b.append(key);
			b.append("= '");
			b.append(escapeOnly((String)values.get(key)));
			b.append("',");
		}
		b.delete(b.length()-1, b.length());
		b.append(" where ");
		b.append(condition);
		return b.toString();
	}
	
	public String buildDelete(String tableName, String condition) {
		return "delete from " + tableName + " where " + condition;
	}
	
}

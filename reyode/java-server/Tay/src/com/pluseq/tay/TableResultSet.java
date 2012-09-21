package com.pluseq.tay;
import java.util.ArrayList;

/**
 * This can only be used for strict tables, such as CSV or RDB
 */
public class TableResultSet {
	public ArrayList<Field> fields = new ArrayList<Field>();
	public ArrayList<String[]> stringRows = new ArrayList<String[]>();
}

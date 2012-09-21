package com.pluseq.tay.sql.sqlite;
import com.pluseq.tay.SqlBuilder;

public class SqliteBuilder extends SqlBuilder {
	@Override
	public String random() {
		return "RANDOM()";
	}
}

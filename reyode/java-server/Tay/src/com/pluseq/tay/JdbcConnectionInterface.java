package com.pluseq.tay;

import java.sql.Connection;

public interface JdbcConnectionInterface extends SqlConnectionInterface {
	void setConnection(Connection con);
}

package com.pluseq.reyad.sql;

import com.pluseq.reyad.Storage;

public class DbInitilizer {	
	public void initializeAll() throws Exception	{
		Storage._().getSimpleTrans().initialize();
	}
}

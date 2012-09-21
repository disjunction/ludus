package com.pluseq.metaword.server;

import com.pluseq.yad.YadConsole;

public class RemoteYadConsole extends YadConsole {
	protected String output = "";
	
	protected void out(String s) {
		output += s + "\n";
	}
	
	public String getOuput()
	{
		String result = output;
		output  = "";
		return result;
	}
}

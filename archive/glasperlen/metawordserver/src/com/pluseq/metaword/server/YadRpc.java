package com.pluseq.metaword.server;

import com.pluseq.metaword.core.*;
import com.pluseq.yad.*;

public class YadRpc {
	protected RemoteYadConsole console = new RemoteYadConsole();
	
	public YadRpc() throws Exception {
		YadBootstrap._().init();
	}
	public String translate(String spelling) throws Exception {
		Word[] words = YadStorage._().getSimpleTransTable().findTranslationWords(spelling);
		if (null == words) {
			return "Sorry, '" + spelling + "' not found\n";
		} else {
			String response = "";
			for (Word w : words) {
				response += "(" + w.getLangId() + ") " + w.getSpelling() + "\n";
			}
			return response;
		}
	}
	
	public String consoleCommand(String command)
	{
		try {
			console.processLine(command);
		} catch (Exception e) {
			return "Exception: " + e.getMessage();
		}
		return console.getOuput();
	}
}

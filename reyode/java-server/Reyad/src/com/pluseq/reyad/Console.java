package com.pluseq.reyad;

import java.io.*;
import java.util.*;

public class Console {
	private Hashtable<String, String> variables = new Hashtable<String, String>();
	
	public Console()
	{
		variables.put("langFrom", Lang.Any);
		variables.put("langTo", Lang.Any);
	}
	
	private enum Keyword {
		lang, parse, read, simpleTrans, resimpleTrans, truncate, trans, t, word, sign, list, q, quit, help, h, nul, get,
		
		// MWQ
		mwq, set, countWords, show,
		
		// List
		add, remove, load, save, clear,
		
		// Quiz
		quiz, variants, start;
		
		public static Keyword toKeyword(String str) {
			if (str.equals("?")) return help;
	        try { return valueOf(str); } 
	        catch (Exception ex) { return nul; }
	    }   
	}
	
	protected void out(String s) {
		System.out.println(s);
	}
	
	protected void out(Word w) {
		out(w.getLangId() + ": " + w.getSpelling() + " (" + w.getSign() + ")");
	}
	
	private void translateSpelling(String spelling) throws Exception
	{
		SimpleTransTableInterface s = Storage._().getSimpleTrans();
		Word[] words = s.findTranslationWords(spelling,
				new Direction(variables.get("langFrom"), variables.get("langTo")));
		if (null == words) {
			System.out.println("Sorry, '" + spelling + "' not found");
		} else {
			for (Word w : words) {
				out(w);
			}
		}
	}
	
	public void processLine(String line) throws Exception {
		line = line.trim();
		if (line.length() > 0 && line.substring(0, 1).equals("#")) {
			return;
		}
		String[] chunks = line.split(" ");
		String command = chunks[0];
		
		String[] params = new String[chunks.length -1];
		for (int i = 1; i< chunks.length; i++) {
			params[i - 1] = chunks[i];
		}
		
		switch (Keyword.toKeyword(command)) {
			case q:
			case quit:
				throw new RuntimeException("quit!");
			case trans:
			case t:
				translateSpelling(params[0]);
				break;
			case h:
			case help:
				InputStream stream = Console.class.getResourceAsStream("ConsoleHelp.txt");
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String tmpLine = null;
				while((tmpLine = in.readLine()) != null) out(tmpLine);
				stream.close();
				break;
			default:
				throw new RuntimeException("unknown command " + command);
		}
	}
	
	
	/**
	 * this is used for the auto-complete functions
	 */
	public String[] getCommands() {
		return new String[]{"parse", "read", "simpleTrans", "resimpleTrans", "trans", "help", "truncate", "sign", "mwq", "list", "quit"};
	}
	
}

package com.pluseq.metaword.cli;

import gnu.getopt.Getopt;
import java.io.IOException;
import jline.ConsoleReader;
import jline.SimpleCompletor;

import com.pluseq.util.StringUtil;
import com.pluseq.yad.YadBootstrap;
import com.pluseq.yad.YadConsole;


public class YadCLI {
	
	protected static void showHelp() {
		System.out.println("Usage:\n" +
				" 1. yad           - run yad console" +
				" 2. yad word      - translate given word" +
				" 3. yad [options] [args]" +
				"        -h               - help\n" +
				"        -e arg1 arg2 ... - executed command in yad-console");			
	}
	
	protected static void consoleLoop() throws IOException
	{
		ConsoleReader cr = new ConsoleReader();
		YadConsole console = new YadConsole();
		cr.addCompletor(new SimpleCompletor(console.getCommands()));
		
		while (true) {				
			String input = cr.readLine("jline@yad> ");
			try { 
				console.processLine(input);
			} catch (Exception e) {
				if (e.getMessage() != null && e.getMessage().equals("quit!")) {
					System.out.println("Bye.");
					return;
				} else {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		YadBootstrap._().init();		
		YadConsole console = new YadConsole();
		
		// if there is data in stdin, then run as a script an exit
		java.io.InputStreamReader isr = new java.io.InputStreamReader(System.in);
		if (isr.ready()) {
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("exec: " + line);
				console.processLine(line);
			}
			return;
		}
		
		// if no arguments present - run in console mode
		if (args.length == 0) {
			consoleLoop();
			return;
		}
		
		Getopt g = new Getopt("yad", args, "-eh");
		int c;
		while ((c = g.getopt()) != -1) {
			switch (c) {
				case 'h':
					System.out.println("help");
					showHelp();
					return;
				case 'e':
					console.processLine(StringUtil.implode(" ", args, g.getOptind()));
					return;
				default:
					if (args.length > 0) {
						console.processLine("t " + args[0]);
					}
			}
		}
	}
}

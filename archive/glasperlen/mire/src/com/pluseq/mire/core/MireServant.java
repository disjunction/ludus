package com.pluseq.mire.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MireServant {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		World world = new World("MireServant");
		NPC servant = new NPC("Servant");

		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);		
		
		// TODO Auto-generated method stub
		while (true) {
			System.out.print(servant.getName() + "> ");
			
			String input = in.readLine();
			
			if (input.equals("q") || input.equals("quit")) {
				return;
			}			
		}
	}

}

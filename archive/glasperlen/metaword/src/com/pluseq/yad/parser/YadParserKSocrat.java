package com.pluseq.yad.parser;

import java.util.StringTokenizer;
import com.pluseq.metaword.storage.WordSourceHeader;

public class YadParserKSocrat extends YadParserAbstract {
	protected void parseLine(String s) {		
		if (s.length() == 0) return;
		if (s.substring(0, 2).equals("//")) {
			String[] chunks = s.split(" ");
			if (chunks.length > 2) {
				System.out.println("ok, chunks check");
				if (chunks[1].equals("direction")) {
					System.out.println("ok, direction tag found");
					String direction = chunks[2];
					if (direction.length() == 4) {
						langFrom = direction.substring(0, 2);
						langTo = direction.substring(2, 4);
					} else {
						System.out.println("wrong format of direction: " + direction + "\n");
					}
				}
			}
			return;
		}
		
		String[] chunks = s.split("--");
		if (chunks.length != 2) return;
		
		chunks[0] = chunks[0].trim();
		chunks[1] = chunks[1].trim();
		
		String[] meanings = chunks[1].split(";");
		for (String meaning : meanings) {
			feedSpellings(chunks[0], meaning);
		}
	}
	
	@Override
	public void feed(String data) {
		StringTokenizer st = new StringTokenizer(data, "\n\r", false);
		while (st.hasMoreTokens()) {
			parseLine(st.nextToken());
		}
	}

	@Override
	public WordSourceHeader getHeader() {
		WordSourceHeader header = new WordSourceHeader();
		header.domain = "ksocrat";
		header.url = "http://ksocrat.linux.kiev.ua/";
		return header;
	}
}

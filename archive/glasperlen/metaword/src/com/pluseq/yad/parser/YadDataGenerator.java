package com.pluseq.yad.parser;

import java.io.*;

import com.pluseq.metaword.core.*;
import com.pluseq.metaword.storage.*;
import com.pluseq.metaword.storage.serializer.XMLWordSource;
import com.pluseq.vivid.VividSqlBuilder;
import com.pluseq.yad.SimpleTransSqlTable;
import com.pluseq.yad.YadStorage;

/**
 * Conversions between serialized sources (usually XML), DB and aggregated tables
 */
public class YadDataGenerator {	
	public void fill(WordSourceInterface source) {
		WordSqlTable wt = MetawordStorage._().getWordTable();
		WordHolder wh = new WordHolder();
		for (Word w : source.getWords()) {
			wh.set(w);
			wt.replace(wh);
		}
		
		RelSqlTable rt = MetawordStorage._().getRelTable();
		RelHolder rh = new RelHolder();
		for (Rel r : source.getRels()) {
			rh.set(r);
			rt.replace(rh);
		}
	}
	
	public String getFileContents(File file) throws IOException {
		FileReader fis = new FileReader(file);
		char[] cbuf = new char[4000];
		StringBuffer sb = new StringBuffer();
		int charsRead;
		int totalRead = 0;
		while ((charsRead = fis.read(cbuf)) > 0) {
			sb.append(cbuf, 0, charsRead);
			totalRead += charsRead;
		}
		fis.close();
		System.out.println("read " + totalRead + " chars");
		return sb.toString();
	}
	
	public void fill(File file, YadParserInterface parser) throws IOException {
		parser.feed(getFileContents(file));
		fill(parser);		
	}
	
	public void parseAndSerialize(File source, File out, YadParserInterface parser) throws IOException {
		String content = getFileContents(source);
		parser.feed(content);
		XMLWordSource xws = new XMLWordSource(parser);
		xws.setHeader(parser.getHeader());
		xws.writeFile(out);
		System.out.println("done");
	}
	
	public void generateSimpleTrans(String fromLangId, String toLangId, boolean reversed) {
		String direction = fromLangId + toLangId;
		SimpleTransSqlTable st = YadStorage._().getSimpleTransTable();
		
		if (direction.length() > 5) {
			st.deleteDirection(direction.substring(0, 2) + direction.substring(4, 6));
			st.fillTriDirection(direction);
			return;
		}
		
		st.deleteDirection(direction);
		if (reversed) {
			st.fillInverseDirection(direction);
		} else {
			st.fillDirection(direction);
		}
	}
}

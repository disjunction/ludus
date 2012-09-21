package com.pluseq.yad.parser;
import com.pluseq.metaword.storage.WordSourceHeader;
import com.pluseq.metaword.storage.WordSourceInterface;

public interface YadParserInterface extends WordSourceInterface {
	/**
	 * resulting HashMap will contain the stats 
	 */
	public void feed(String data);
	public WordSourceHeader getHeader();
}
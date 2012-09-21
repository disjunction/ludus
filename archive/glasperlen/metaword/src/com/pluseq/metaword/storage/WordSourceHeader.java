package com.pluseq.metaword.storage;
import com.pluseq.metaword.core.Metaword;

public class WordSourceHeader {
	public String generator = Metaword.getSignature();
	public String title;
	public String domain;
	public String url;
	public String[] parts = {"words", "rels"};
}

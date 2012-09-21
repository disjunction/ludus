package com.pluseq.yad.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.pluseq.metaword.core.*;
import com.pluseq.metaword.core.util.Transliterator;

public abstract class YadParserAbstract implements YadParserInterface {

	protected InputStream input;
	protected ArrayList<Rel> rels = new ArrayList<Rel>();
	protected ArrayList<Word> words = new ArrayList<Word>();
	
	protected String langFrom = "en";
	protected String langTo = "ru";
	protected Transliterator transliterator;
	protected Logger logger = Logger.getLogger(YadParserAbstract.class.getName());


	public YadParserAbstract() {
		transliterator = Transliterator._();
	}
	
	
	@Override
	public ArrayList<Rel> getRels() {
		return rels;
	}

	@Override
	public ArrayList<Word> getWords() {
		return words;
	}
	
	protected void feedSpellings(String spellingFrom, String spellingTo) {
		Word wordFrom = new Word(transliterator.toSign(spellingFrom, langFrom), spellingFrom);
		Word wordTo = new Word(transliterator.toSign(spellingTo, langTo), spellingTo);
		
		words.add(wordFrom);
		words.add(wordTo);
		
		Rel rel = new Rel(wordFrom, wordTo, Rel.TypeTrans);
		rels.add(rel);
	}

}

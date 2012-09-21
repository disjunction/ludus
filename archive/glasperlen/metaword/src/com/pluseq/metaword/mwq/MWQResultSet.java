package com.pluseq.metaword.mwq;

import java.util.ArrayList;
import com.pluseq.metaword.core.Rel;
import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.storage.WordSourceInterface;

public class MWQResultSet implements WordSourceInterface {

	private int countWords = -1;
	private int countRels = -1;
	protected ArrayList<Word> words = new ArrayList<Word>();
	private ArrayList<Rel> rels  = new ArrayList<Rel>();
	
	@Override
	public ArrayList<Rel> getRels() {
		return rels;
	}

	@Override
	public ArrayList<Word> getWords() {	
		return words;
	}
	
	public void setCountWords(int countWords) {
		this.countWords = countWords; 
	}
	public void setCountRels(int countRels) {
		this.countRels = countRels; 
	}

	public int getCountWords() {
		return countWords < 0? words.size() : countWords;
	}
	public int getCountRels() {
		return countRels < 0? rels.size() : countRels;
	}
}

package com.pluseq.metaword.storage;
import java.util.Hashtable;

import com.pluseq.metaword.core.*;
import com.pluseq.vivid.*;

public class WordHolder extends VividAutoSavedHolder implements HashtableHolderInterface {
	
	public WordHolder(Word word) {
		set(word);
	}

	public WordHolder() {
	}

	@Override
	public Word get() {
		return (Word) super.get();
	}
	
	@Override
	public String getStringId()
	{
		return get().sign;
	}

	@Override
	public boolean save() {
		return MetawordStorage._().getWordTable().save(this);
	}

	@Override
	public Hashtable<String, Object> toHashtable() {
		Word word = get();
		Hashtable<String, Object> h = new Hashtable<String, Object>();
		h.put("sign", word.sign);
		h.put("langId", word.getLangId());
		h.put("spelling", word.getSpelling());
		return h;
	}
}

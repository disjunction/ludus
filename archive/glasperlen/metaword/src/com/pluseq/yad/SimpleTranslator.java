package com.pluseq.yad;

import com.pluseq.metaword.core.LangDirection;
import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.core.util.MetawordException;

public class SimpleTranslator implements TranslatorInterface {
	private LangDirection direction = new LangDirection("....");
	
	public SimpleTranslator(LangDirection direction) {
		this.direction = direction;
	}
	
	@Override
	public Word[] findForSpelling(String spelling) {
		try {
			String[] directions = new String[]{direction.toString()};
			return YadStorage._().getSimpleTransTable().findTranslationWords(spelling, directions);
		} catch (Exception e) {
			throw new MetawordException("failed to make translation query", e);
		}
	}

	@Override
	public void setSourceLangs(String[] langIds) {
		if (langIds.length != 1) {
			throw new MetawordException("only one argument is possible");
		}
		direction.source = langIds[0];
	}

	@Override
	public void setTargetLangs(String[] langIds) {
		if (langIds.length != 1) {
			throw new MetawordException("only one argument is possible");
		}
		direction.target = langIds[0];
	}
}

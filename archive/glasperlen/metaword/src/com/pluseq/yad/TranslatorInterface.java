package com.pluseq.yad;

import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.core.util.MetawordException;

public interface TranslatorInterface {
	void setTargetLangs(String[] langIds) throws MetawordException;
	void setSourceLangs(String[] langIds) throws MetawordException;
	Word[] findForSpelling(String spelling) throws MetawordException;
}

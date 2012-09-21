package com.pluseq.metaword.core.util;

public interface TransliteratorLangInterface {
	public int weight(String spelling);
	public String transliterate(String spelling);
}

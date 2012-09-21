package com.pluseq.metaword.core.util;

import java.util.HashMap;

import com.pluseq.metaword.core.Lang;

public class Transliterator {
	protected HashMap<String, TransliteratorLangInterface> langs;
	
	public Transliterator()	{
		langs = new HashMap<String, TransliteratorLangInterface>();
		langs.put("en", new TransliteratorLangUni());
		langs.put("de", new TransliteratorLangDe());
		langs.put("ru", new TransliteratorLangRu());
	}
	
	public String detectLang(String spelling)
	{
		int detectedWeight = -100;
		int weight;
		String detectedLang = Lang.Eidos;
		for (String langId : langs.keySet()) {
			weight = langs.get(langId).weight(spelling);
			if (weight > detectedWeight) {
				detectedLang = langId;
				detectedWeight = weight;
			}
		}
		return detectedLang;
	}
	
	public String toSign(String spelling) {
		return toSign(spelling, detectLang(spelling));
	}
		
	public String toSign(String spelling, String langId) {
		TransliteratorLangInterface t = langs.get(langId);
		if (null == t) {
			t = langs.get("en");
			langId = "en";
		}
		return langId + t.transliterate(spelling);
	}
	
	protected static Transliterator instance;
	public static Transliterator _() {
		if (null == instance) {
			instance = new Transliterator();
		}
		return instance;
	}
}

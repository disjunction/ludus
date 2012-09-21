package com.pluseq.metaword.core.util;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;

public class TransliteratorLangDe implements TransliteratorLangInterface {

	protected StringToStringTable map;
	
	public TransliteratorLangDe() {
		map = new StringToStringTable();
		map.put("ü", "ue");
		map.put("ö", "oe");
		map.put("ä", "oe");
		map.put("ß", "s");
		map.put("Ü", "Ue");
		map.put("Ö", "Oe");
		map.put("Ä", "Ae");
	}
	
	@Override
	public String transliterate(String spelling) {
		StringBuffer sb = new StringBuffer();
		String val;
		for (char c : spelling.toCharArray()) {
			if (null != (val = map.get(String.valueOf(c)))) {
				sb.append(val);
			} else {
				sb.append(c);
			}
		}
		return TransliteratorLangUni.postProcess(sb.toString());
	}

	@Override
	public int weight(String spelling) {
		int theWeight = -1;
		char[] ca = spelling.toCharArray();
		
		// german words often start with a capital letter
		if (String.valueOf(ca[0]).equals(String.valueOf(ca[0]).toUpperCase())) {
			theWeight+=2;
		}
		
		for (char c : ca) {
			if (null != map.get(String.valueOf(c))) {
				theWeight+=10;
			}
		}
		return theWeight;
	}
}

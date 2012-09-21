package com.pluseq.metaword.core.util;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;

public class TransliteratorLangRu implements TransliteratorLangInterface {

	protected StringToStringTable map;
	
	public TransliteratorLangRu() {
		map = new StringToStringTable();
		map.put("а", "a");
		map.put("б", "b");
		map.put("в", "v");
		map.put("г", "g");
		map.put("д", "d");
		map.put("е", "e");
		map.put("ё", "jo");
		map.put("ж", "zh");
		map.put("з", "z");
		map.put("и", "i");
		map.put("й", "j");
		map.put("к", "k");
		map.put("л", "l");
		map.put("м", "m");
		map.put("н", "n");
		map.put("о", "o");
		map.put("п", "p");
		map.put("р", "r");
		map.put("с", "s");
		map.put("т", "t");
		map.put("у", "u");
		map.put("ф", "f");
		map.put("х", "h");
		map.put("ц", "ts");
		map.put("ч", "ch");
		map.put("ш", "sh");
		map.put("щ", "sch");
		map.put("ь", "");
		map.put("ы", "y");
		map.put("ъ", "");
		map.put("э", "e");
		map.put("ю", "ju");
		map.put("я", "ja");
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
		return TransliteratorLangUni.postProcess(sb.toString().toLowerCase());
	}

	@Override
	public int weight(String spelling) {
		String normal = spelling.toLowerCase();
		int theWeight = -1;
		for (char c : spelling.toCharArray()) {
			if (null != map.get(String.valueOf(c))) {
				theWeight+=10;
			}
		}
		return theWeight;
	}
}

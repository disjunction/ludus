package com.pluseq.util;

import java.util.Arrays;

public class StringUtil {
	public static String implode(String glue, String[] chunks) {
		return implode(glue, chunks, 0);
	}
	
	public static String implode(String glue, String[] chunks, int shift) {
		if (chunks.length == 0) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = shift; i < chunks.length; i++) {
			if (sb.length() > 0) {
				sb.append(glue);
			}
			sb.append(chunks[i]);
		}
		return sb.toString();
	}
	
	public static String implode(String[] chunks) {
		return implode(" ", chunks);
	}

	public static String implode(String glue, Object[] array) {
		return implode(glue, (String[]) Arrays.copyOf(array, array.length, String[].class));
	}
}

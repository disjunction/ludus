package com.pluseq.metaword.runtime;

import java.util.ArrayList;

import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.core.util.MetawordException;
import com.pluseq.metaword.mwq.*;
import com.pluseq.metaword.storage.MetawordStorage;
import com.pluseq.metaword.storage.WordHolder;
import com.pluseq.util.StringUtil;
import com.pluseq.vivid.*;

public class WordManager {
	
	/**
	 * this method can be ambiguous
	 * @param spelling
	 * @param langId
	 * @throws Exception 
	 */
	public Word getBySpelling(String spelling, String langId) throws Exception {
		VividList vl = MetawordStorage._().getWordTable().findBySpellingLang(spelling, langId);
		return vl.isEmpty()? null : (Word) vl.get(0).get();
	}
	
	public Word getBySign(String sign) throws Exception {
		WordHolder w = (WordHolder) MetawordStorage._().getWordTable().holderById(sign);
		return null == w? null : w.get();
	}
	
	private String makeSQL(MWQStatement mwqs) {
		MWQFilters filters = mwqs.getFilters();
		if (null == filters) {
			return null;
		}
		
		String target;
		switch (mwqs.getOperation()) {
			case countWords:
				target = "count(*) as countWords";
				break;
			case words:
				target = " * ";
				break;
			default:
				throw new MetawordException("only words and countWords operations are currently supported");
		}
		
		String result = "select " + target + " from Word";
		
		ArrayList<String> conditions = new ArrayList<String>();
		
		if (null != filters.lang) {
			conditions.add("langId=" + VividSqlBuilder.escape(filters.lang));
		}
		if (null != filters.sign) {
			conditions.add("sign=" + VividSqlBuilder.escape(filters.sign));
		}
		if (null != filters.spelling) {
			conditions.add("spelling like " + VividSqlBuilder.escape(filters.spelling));
		}
		if (null != filters.where) {
			conditions.add(filters.where);
		}
		if (conditions.size() > 0) {
			result += " where " + StringUtil.implode(" and ", conditions.toArray());
		}
		
		return result;
	}
	
	public MWQResultSet executeMWQ(MWQStatement mwqs) {
		MWQResultSet result = new MWQResultSet();
		String sql = makeSQL(mwqs);
		
		if (null == sql) {
			return null;
		}
		
		try {
			switch (mwqs.getOperation()) {
				case words:
					ArrayList<Word> words = result.getWords();
					VividList vl = MetawordStorage._().getWordTable().query(makeSQL(mwqs));
					for (HolderInterface holder : vl) {
						words.add((Word) holder.get());
					}
					break;
				case countWords:
					VividResultSet rs = MetawordStorage._().getWordTable().getConnection().getAllAsStrings(makeSQL(mwqs));
					String[] row = rs.stringRows.get(0);
					result.setCountWords(Integer.valueOf(row[0]));
					break;
			}
		} catch (Exception e) {
			return null;
		}

		return result;
	}
}

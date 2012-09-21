package com.pluseq.metaword.storage;
import com.pluseq.metaword.core.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.pluseq.vivid.*;
import com.pluseq.vivid.mysql.VividMySQLConnection;
import com.sun.org.apache.xml.internal.utils.StringToStringTable;

public class WordSqlTable  extends AbstractSqlTable {

	public WordSqlTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("sign"));
		l.add(new VividField("langId"));
		l.add(new VividField("spelling"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "Word";
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		Word word = new Word((String)hashtable.get("sign"));
		word.spelling = (String)hashtable.get("spelling");
		word.langId = (String)hashtable.get("langId");
		return new WordHolder(word);
	}
	
	public VividList findBySpellingLang(String spelling, String langId) throws Exception {
		String sql = "select * from " + getTableName() + " where spelling=" +
		             VividSqlBuilder.escape(spelling) +
		             " and langId=" + VividSqlBuilder.escape(langId);
		return query(sql);
	}

	public VividList listByRequest(WordRequest request) throws Exception {
		String sql = "select * from " + getTableName() + " where langId in (" +
					 VividSqlBuilder.escape(request.langIds) + ")";
		return this.query(sql);
	}
	
	@Override
	public int getTableId() {
		return MetawordStorage.WordTableId;
	}
}

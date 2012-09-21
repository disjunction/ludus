package com.pluseq.yad;
import com.pluseq.metaword.core.*;

import java.util.*;
import com.pluseq.vivid.*;

public class SimpleTransSqlTable  extends AbstractSqlTable {

	public static final int TableId = 250;
	
	public SimpleTransSqlTable(VividSqlConnectionInterface vividConnection) {
		super(vividConnection);
	}

	@Override
	protected ArrayList<VividField> getTableFields() {
		ArrayList<VividField> l = new ArrayList<VividField>();
		l.add(new VividField("direction"));
		l.add(new VividField("subjSign"));
		l.add(new VividField("subjSpelling"));
		l.add(new VividField("objSign"));
		l.add(new VividField("objSpelling"));
		return l;
	}

	@Override
	protected String getTableName() {
		return "SimpleTrans";
	}

	@Override
	public HolderInterface makeHolder(Hashtable<String, Object> hashtable) {
		return null;
	}
	
	@Override
	public int getTableId() {
		return TableId;
	}
	
	@Override
	public HolderInterface holderById(String id) throws VividException {
		throw new VividException("SimpleTrans has no simple primary key");
	}
	
	public void deleteDirection(String direction) {
		con.execute("delete from " + getTableName() + " where direction = " + VividSqlBuilder.escape(direction.substring(0, 4)));
	}
	
	public void fillDirection(String direction) {
		String langFrom = direction.substring(0, 2);
		String langTo = direction.substring(2, 4);
		
		String sql = "insert into SimpleTrans " +
					 "select " + VividSqlBuilder.escape(direction) + " as direction," +
					 "       subj.sign as subjSign, " +
					 "       subj.spelling as subjSpelling," +
					 "       obj.sign as objSign," +
					 "       obj.spelling as objSpelling " +
					 "from Word subj join Rel on Rel.subjSign=subj.sign " +
					 "               join Word obj on Rel.objSign = obj.sign " +
					 "where subj.langId=" + VividSqlBuilder.escape(langFrom) +
					 "      and obj.langId=" + VividSqlBuilder.escape(langTo) +
					 "      and Rel.typeSign=" + VividSqlBuilder.escape(Rel.TypeTrans);
		
		System.out.println(sql);
		con.execute(sql);
	}
	
	/**
	 * if there is translation form en->ru and ru->de, then
	 * this method makes en->de translation (very dirty)
	 * @param direction
	 */
	public void fillTriDirection(String direction) {
		String langFrom = direction.substring(0, 2);
		String langMid = direction.substring(2, 4);
		String langTo = direction.substring(4, 6);
		
		String sql = "insert ignore into SimpleTrans " +
					 "select " + VividSqlBuilder.escape(langFrom + langTo) + " as direction," +
					 "       subj.sign as subjSign, " +
					 "       subj.spelling as subjSpelling," +
					 "       obj.sign as objSign," +
					 "       obj.spelling as objSpelling " +
					 "from Word subj join Rel rel1 on rel1.subjSign=subj.sign " +
					 "               join Rel rel2 on rel2.subjSign = rel1.objSign " +
					 "               join Word obj on rel2.objSign = obj.sign " +
					 "where subj.langId=" + VividSqlBuilder.escape(langFrom) +
					 "      and obj.langId=" + VividSqlBuilder.escape(langTo) +
					 "      and rel1.typeSign=" + VividSqlBuilder.escape(Rel.TypeTrans) +
		             "      and rel2.typeSign=" + VividSqlBuilder.escape(Rel.TypeTrans) +
		             "      and substr(rel2.subjSign, 1, 2)=" + VividSqlBuilder.escape(langMid);
		
		System.out.println(sql);
		con.execute(sql);
	}
	
	public void fillInverseDirection(String direction) {
		String langFrom = direction.substring(0, 2);
		String langTo = direction.substring(2, 4);
		
		String sql = "insert into SimpleTrans " +
					 "select " + VividSqlBuilder.escape(direction) + " as direction," +
					 "       objSign, objSpelling, subjSign, subjSpelling " +
					 "from SimpleTrans " +
					 "where direction=" + VividSqlBuilder.escape(langTo + langFrom);		
		System.out.println(sql);
		con.execute(sql);
	}
	
	public Word[] findTranslationWords(String spelling) throws Exception {
		return findTranslationWords(spelling, new String[]{});
	}
	
	/**
	 * wildcarded tanslation
	 * @param spelling
	 * @param direction
	 * @return
	 * @throws Exception
	 */
	public Word[] findTranslationWords(String spelling, String direction) throws Exception {
		return findTranslationWords(spelling, new String[] {direction});
	}
	
	/**
	 * no wildcards supported here
	 * @param spelling
	 * @param directions
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public Word[] findTranslationWords(String spelling, String[] directions) throws Exception {
		String sql = "select objSign, objSpelling from SimpleTrans where binary subjSpelling = " + VividSqlBuilder.escape(spelling);
		if (null != directions && directions.length != 0) {
			sql += " and (direction in (" + VividSqlBuilder.escape(directions) + ")";
			for (String direction : directions) {
				// doesn't add any meaning in the query
				//if (direction.equals(Lang.Any + Lang.Any)) continue;
				
				String mask = direction.replace("**", "%");
				sql += " or direction like " + VividSqlBuilder.escape(mask);
			}
			sql += ")";
		}
		VividResultSet rs = con.getAllAsStrings(sql);
		if (rs.stringRows.size() == 0) return null;
		
		Word[] result = new Word[rs.stringRows.size()];
		int i = 0;
		for (String[] row : rs.stringRows) {
			Word w = new Word(row[0], row[1]);
			result[i++] = w;
		}
		return result;
	}
}
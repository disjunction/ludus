package com.pluseq.reyad.sql;

import java.util.Hashtable;
import com.pluseq.reyad.*;
import com.pluseq.tay.*;

public class SimpleTransTable extends SqlTable implements SimpleTransTableInterface
{
	private int hardLimit = 200;
	
	public SimpleTransTable(SqlConnectionInterface con) {
		super(con);
	}
	
	@Override
	public SimpleTrans fromRow(String[] row) {
		SimpleTrans s = new SimpleTrans();
		s.direction = row[0];
		s.subjSign = row[1];
		s.subjSpelling = row[2];
		s.objSign = row[3];
		s.objSpelling = row[4];
		return s;
	}
	
	@Override
	protected String getTableName() {
		return "SimpleTrans";
	}

	@Override
	public Object get(String id) throws TayException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, Object> toHashtable(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Word[] findTranslationWords(String spelling, Direction direction)
			throws Exception {
		SqlBuilder b = getConnection().getSqlBuilder();
		String sql = "select objSign, objSpelling from SimpleTrans where binary subjSpelling = " + b.escape(spelling);
		if (null != direction /*&& direction.length != 0*/) {
			sql += " and (direction in (" + b.escape(direction.toString()) + ")";
			//for (String direction : directions) {
				// doesn't add any meaning in the query
				//if (direction.equals(Lang.Any + Lang.Any)) continue;
				
				String mask = direction.toString().replace("**", "%");
				sql += " or direction like " + b.escape(mask);
			//}
			sql += ") limit " + hardLimit;
		}
		TableResultSet rs = con.select(sql);
		if (rs.stringRows.size() == 0) return null;
		
		Word[] result = new Word[rs.stringRows.size()];
		int i = 0;
		for (String[] row : rs.stringRows) {
			Word w = new Word(row[0], row[1]);
			result[i++] = w;
		}
		return result;
	}

	@Override
	public void initialize() throws Exception {
		con.execute("DROP TABLE IF EXISTS SimpleTrans");
		con.execute("CREATE TABLE SimpleTrans (" +
    			"direction char(4) NOT NULL DEFAULT '....'," +
    			"subjSign varchar(40) NOT NULL DEFAULT ''," +
    			"subjSpelling varchar(80) DEFAULT NULL," +
    			"objSign varchar(40) NOT NULL DEFAULT ''," +
    			"objSpelling varchar(80) DEFAULT NULL," +
    			"constraint theprimary primary key (subjSign, 2objSign))");
		con.execute("INSERT INTO `SimpleTrans` (direction, subjSign, subjSpelling, objSign, objSpelling) VALUES ('deen','deWeiskehl-Buschtimalie','Weißkehl-Buschtimalie','enausten''s_spotted_tree_babbler','Austen''s Spotted Tree Babbler');");
	}

	@Override
	public SimpleTrans[] findObjByHomoRels(String[] objSigns, String type, Direction direction, int limit) throws Exception {
		String strDirection = direction.toString();
		SqlBuilder b = getConnection().getSqlBuilder();
		String sql = "select distinct SimpleTrans.* from " + getTableName();
		if (null != objSigns) {
			sql += " inner join Rel on SimpleTrans.objSign=Rel.subjSign and Rel.typeSign=" +
					b.escape(type) + " and Rel.objSign in (" +
					b.escape(objSigns) + ")";
		}
		sql += " where direction='" + strDirection + "' order by " + b.random() + " limit " + limit;
		
		TableResultSet rs = con.select(sql);
		if (rs.stringRows.size() == 0) return null;
		
		SimpleTrans[] result = new SimpleTrans[rs.stringRows.size()];
		int i = 0;
		for (String[] row : rs.stringRows) {
			SimpleTrans st = new SimpleTrans();
			st.direction = strDirection;
			st.subjSign = row[1];
			st.subjSpelling = row[2];
			st.objSign = row[3];
			st.objSpelling = row[4];
			result[i++] = st;
		}
		return result;
	}
	
	@Override
	public SimpleTrans[] findByTags(String[] tags, Direction direction, int limit) throws Exception {
		return this.findObjByHomoRels(tags, "r.tag", direction, limit); 
	}
}

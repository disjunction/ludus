package com.pluseq.reyad;

import java.util.ArrayList;
import com.pluseq.tay.Field;
import com.pluseq.tay.TableSchema;

public class SimpleTransSchema extends TableSchema {

	@Override
	public ArrayList<Field> getFields() {
		ArrayList<Field> l = new ArrayList<Field>();
		l.add(new Field("direction"));
		l.add(new Field("subjSign"));
		l.add(new Field("subjSpelling"));
		l.add(new Field("objSign"));
		l.add(new Field("objSpelling"));
		return l;
	}
}

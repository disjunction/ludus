package com.pluseq.tay;
import java.util.*;

public class Operation {
	public String type;
	public ArrayList<Field> whatFields = new ArrayList<Field>();
	public ArrayList<String> whatValues = new ArrayList<String>();
	
	public static Operation fromValues(Hashtable<String,Object> values)
	{
		Operation p = new Operation();
		for (String key : values.keySet()) {
			p.whatFields.add(new Field(key));
			p.whatValues.add(values.get(key).toString());
		}
		return p;
	}
}

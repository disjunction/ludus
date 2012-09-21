package com.pluseq.reyad;

public class SimpleTrans {
	public String direction;
	public String subjSign;
	public String subjSpelling;
	public String objSign;
	public String objSpelling;
	
	public Word makeSubjWord() {
		return new Word(subjSign, subjSpelling);
	}
	
	public Word makeObjWord() {
		return new Word(objSign, objSpelling);
	}
	
	public static SimpleTrans[] invertArray(SimpleTrans[] a) {
		SimpleTrans[] inverted = new SimpleTrans[a.length];
		for (int i = 0; i < a.length; i++) {
			SimpleTrans source = a[i];
			SimpleTrans temp = new SimpleTrans();
			temp.direction = source.direction.substring(2,2) + source.direction.substring(0,2);
			temp.subjSign = source.objSign;
			temp.subjSpelling = source.objSpelling;
			temp.objSign = source.subjSign;
			temp.objSpelling = source.subjSpelling;
			inverted[i] = temp;
		}
		return inverted;
	}
}

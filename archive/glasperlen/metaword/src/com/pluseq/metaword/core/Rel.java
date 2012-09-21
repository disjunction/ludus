package com.pluseq.metaword.core;

public class Rel {
	public String subjSign;
	public String objSign;
	public String typeSign;
	public int value = Full;
	
	public static final int Full = 100;
	public static final int None = 0;
	public static final int Kaum = 10;
	public static final int Half = 50;
	
	public static final String TypeTrans = Lang.Sys + "tr";
	public static final String TypeProperty = Lang.Sys + "pr";
	
	public Rel(String subjSign, String objSign, String typeSign) {
		this.subjSign = subjSign;
		this.objSign = objSign;
		this.typeSign = typeSign;
	}
	
	public Rel(Word subj, Word obj, String typeSign) {
		this.subjSign = subj.sign;
		this.objSign = obj.sign;
		this.typeSign = typeSign;
	}
	
	public Rel(String subjSign, String objSign, String typeSign, int value) {
		this(subjSign, objSign, typeSign);
		this.value = (byte) value;
	}
}

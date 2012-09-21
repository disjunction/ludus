package com.pluseq.metaword.core;

import javax.xml.bind.annotation.XmlElement;

public class Word {
	public String sign;
	public String spelling;
	public String langId;
	
	public Word(){
		sign = "..null";
	}
	
	public Word(String sign) {
		this.sign = sign;
	}
	
	public Word(String sign, String spelling) {
		this.sign = sign;
		this.spelling = spelling;
	}
	
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	@XmlElement
	public String getSpelling() {
		return (null == spelling)? sign.substring(2) : spelling;
	}
	
	@XmlElement
	public String getSign() {
		return sign;
	}
	
	public String getLangId() {
		return (null == langId)? sign.substring(0, 2) : langId;
	}
}

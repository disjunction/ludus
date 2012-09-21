package com.pluseq.reyad;

public class Word {
	private String sign;
	private String spelling;
	
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
	
	public String getSpelling() {
		return (null == spelling)? sign.substring(2) : spelling;
	}

	public String getSign() {
		return sign;
	}
	
	public String getLangId() {
		return sign.substring(0, 2);
	}
	
	public String[] toArray() {
		return new String[] {this.sign, this.spelling};
	}
}

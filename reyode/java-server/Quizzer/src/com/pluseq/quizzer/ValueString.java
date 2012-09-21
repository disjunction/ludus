package com.pluseq.quizzer;

public class ValueString implements ValueInterface {
	protected String val;
	
	public ValueString(String val) {
		this.val = val;
	}
	
	@Override
	public String toString() {
		return val;
	}
}

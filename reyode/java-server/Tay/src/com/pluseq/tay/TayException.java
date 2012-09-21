package com.pluseq.tay;

public class TayException extends Exception {
	private static final long serialVersionUID = -7072751115347782423L;
	
	public TayException(String string) {
		super(string);
	}
	
	public TayException(String string, Throwable cause) {
		super(string, cause);
	}
	
	public TayException() {
		super();
	}
}

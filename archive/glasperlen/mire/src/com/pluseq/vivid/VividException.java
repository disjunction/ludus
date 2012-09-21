package com.pluseq.vivid;

public class VividException extends Exception {
	public VividException(String string) {
		super(string);
	}
	
	public VividException(String string, Throwable cause) {
		super(string, cause);
	}
	
	public VividException() {
		super();
	}
	private static final long serialVersionUID = -505151433558841818L;
}

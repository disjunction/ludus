package com.pluseq.metaword.core.util;

public class MetawordException extends RuntimeException {
	private static final long serialVersionUID = -7167174698886939667L;
	
	public MetawordException(String message) {
		super(message);
	}
	
	public MetawordException(String message, Throwable cause) {
		super(message, cause);
	}
}

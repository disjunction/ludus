package com.pluseq.metaword.core;

public class LangDirection {
	public String source;
	public String target;
	
	public LangDirection(String source, String target) {
		this.source = source;
		this.target = target;
	}
	
	public LangDirection(String direction) {
		source = direction.substring(0, 2);
		target = direction.substring(2);
	}
	
	public String toString() {
		return source +	target;
	}
	
	public LangDirection reversed() {
		return new LangDirection(target, source);
	}
}

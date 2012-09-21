package com.pluseq.reyad;

public class Direction {
	public String source;
	public String target;
	
	public Direction(String source, String target) {
		this.source = source;
		this.target = target;
	}
	
	public Direction(String direction) {
		source = direction.substring(0, 2);
		target = direction.substring(2);
	}
	
	public String toString() {
		return source +	target;
	}
	
	public Direction reversed() {
		return new Direction(target, source);
	}
}

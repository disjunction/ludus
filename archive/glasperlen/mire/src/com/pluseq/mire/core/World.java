package com.pluseq.mire.core;

/**
 * Describes the general
 * @author or
 */
public class World {
	public String name;
	public Universe universe;
	public World () {
		this("default");
	}
	public World (String name) {
		this.name = name;
	}
	
	public String yell(String in) {
		return "aaaa" + in;
	}
}
package com.pluseq.mire.core;

/**
 * NPC and Players
 * @author or
 *
 */
public class Character extends Entity {
	protected String name;
	
	public Character(String name) {
		this.name = name;
	}
	public Character() {
		this("Unknown");
	}

	
	public String getName() {
		return this.name;
	}
}

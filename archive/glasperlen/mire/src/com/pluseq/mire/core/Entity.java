package com.pluseq.mire.core;

/**
 * Everything in Mire which can be placed in a room
 * @author or
 *
 */
public class Entity implements Describable {
	protected String name = "unnamed";
	
	@Override
	public String getDescription() {
		return "description of " + this.getTitle();
	}

	@Override
	public String getShortDescription() {
		return "about " + this.getTitle();
	}

	@Override
	public String getTitle() {
		return this.name;
	}
}

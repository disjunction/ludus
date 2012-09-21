package com.pluseq.tay;

public interface Savable extends Identified{
	void setSaved(boolean saved);
	boolean getSaved();
}
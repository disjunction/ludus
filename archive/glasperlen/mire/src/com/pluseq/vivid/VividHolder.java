package com.pluseq.vivid;

/**
 * Holder will try to UPDATE the object on saving, unless
 * the fieldId is not set (is null). In this case it would use UPDATE
 * @author or
 *
 */
public abstract class VividHolder implements HolderInterface, SavableInterface
{

	protected Object o;
	
	@Override
	public Object get() {
		return o;
	}

	@Override
	public void set(Object o) {
		this.o = o;
	}
	
	public boolean saved = false;

	@Override
	public boolean getSaved() {
		return getStringId() == null;
	}

	@Override
	public void setSaved(boolean saved) {
	}
}

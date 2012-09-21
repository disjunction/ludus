package com.pluseq.vivid;
/**
 * Auto-saved objects will be INSERTED if the object was constructed manually,
 * otherwise (if it was retrieved from DB) it gets UPDATED
 * @author or
 *
 */
public abstract class VividAutoSavedHolder extends VividHolder{
	public boolean saved = false;

	@Override
	public boolean getSaved() {
		return saved;
	}

	@Override
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}

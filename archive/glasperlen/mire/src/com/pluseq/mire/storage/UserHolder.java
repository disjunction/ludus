package com.pluseq.mire.storage;
import java.util.Hashtable;

import com.pluseq.mire.core.*;
import com.pluseq.vivid.*;

public class UserHolder extends VividAutoSavedHolder implements HashtableHolderInterface {
	public UserHolder(MireUser u) {
		set(u);
	}

	@Override
	public MireUser get() {
		return (MireUser)super.get();
	}
	
	@Override
	public String getStringId() {
		return get().login;
	}

	@Override
	public boolean save() {
		return MireStorage._().getUserTable().save(this);
	}

	@Override
	public Hashtable<String, Object> toHashtable() {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		MireUser u = get();
		ht.put("login", u.login);
		ht.put("password", u.password);
		ht.put("name", u.name);
		ht.put("email", u.email);
		return ht;
	}	
}

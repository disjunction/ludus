package com.pluseq.mire.storage;
import java.util.Hashtable;

import com.pluseq.mire.core.*;
import com.pluseq.vivid.*;

public class SessionHolder extends VividAutoSavedHolder implements HashtableHolderInterface
{
	public SessionHolder(MireSession session)
	{
		set(session);
	}
	
	@Override
	public String getStringId() {
		return get().getSessionId();
	}

	@Override
	public MireSession get() {
		return (MireSession) super.get();
	}

	@Override
	public boolean save() {
		SessionTableInterface t = MireStorage._().getSessionTable();
		return t.save(this);
	}

	@Override
	public Hashtable<String, Object> toHashtable() {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		MireSession s = (MireSession)get();
		ht.put("sessionId", s.getSessionId());
		ht.put("login", s.getLogin());
		return ht;
	}
}
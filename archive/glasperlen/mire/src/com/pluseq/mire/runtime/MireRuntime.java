package com.pluseq.mire.runtime;

import com.pluseq.mire.core.*;
import com.pluseq.mire.storage.MireStorage;

public class MireRuntime {
	protected SessionManager sessionManager;
	protected MireUmgebung umgebung;
	
	public MireStorage getStorage() {
		return MireStorage._();
	}
	
	public SessionManager getSessionManager()
	{
		if (null == sessionManager) {
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}
}

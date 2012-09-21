package com.pluseq.mire.runtime;
import com.pluseq.mire.core.*;
import com.pluseq.mire.storage.*;

abstract public class MireClient {
	protected MireRuntime runtime;
	protected SessionHolder sessionHolder;
	protected MireStorage storage;

	public MireClient() {
		runtime = new MireRuntime();
		storage = runtime.getStorage();
	}
	
	public abstract Object execute(String signature, Object[] params);
	public abstract boolean login(String login, String password);
}

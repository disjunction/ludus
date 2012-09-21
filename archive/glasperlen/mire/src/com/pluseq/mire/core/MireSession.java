package com.pluseq.mire.core;
import java.util.Random;

/**
 * Object holds all important information for a single user connection
 * and all his environment
 * @author or
 *
 */
public class MireSession {
	protected String login;
	protected String sessionId;
	
	public MireSession(String login, String sessionId)
	{
		this.login = login;
		this.sessionId = sessionId;
	}
	
	public MireSession(String login)
	{
		this(login, "s" + Long.toString(Math.abs((new Random()).nextLong()), 36));	
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	public String getLogin()
	{
		return login;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
}

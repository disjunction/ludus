package com.pluseq.metaword.core;

public class Lang {
	public String langId;

	/**
	 * the name of this language (usually in the same language)
	 */
	public String sign;
	
	public static final String English = "en";
	public static final String Latin = "la";
	public static final String German = "de";
	public static final String Russian = "ru";
	
	public static final String Num = "00";
	
	public static final String Any = "**";
	
	/**
	 * idea, detached from any language
	 */
	public static final String Eidos = "..";
	
	/**
	 * linguistic
	 */
	public static final String Ling = "l.";
	
	public static final String Article = "a.";
	public static final String Sys = "++";
	
	/**
	 * external media: picture/video/audio
	 */
	public static final String Media = "m.";
	
	/**
	 * sources are officially registered sources for knoledge in 
	 */
	public static final String Source = "s.";
	
	/**
	 * generic
	 */
	public static final String Generic = "--";
}
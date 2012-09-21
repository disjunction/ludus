package com.pluseq.reyad;

public class Article {
	/**
	 * e.g. how to grow apples - a reference to Word with sign "a.XXX"
	 * the word spelling will be the title of this article
	 */
	public String sign;
	
	/**
	 * see constants in this class
	 */
	public String articleType;

	/**
	 * ex: ru.wikipedia.org, lenta.ru
	 */
	public String domain;
	
	/**
	 * ex: html, txt
	 */
	public String format;
	
	/**
	 * en
	 */
	public String langId;
	public String content;
	
	
	public static final String TypeText = "text";
	public static final String TypeImage = "image";
	public static final String TypeAudio = "audio";
	public static final String TypeVideo = "video";
	public static final String TypeExercise = "exercise";
}

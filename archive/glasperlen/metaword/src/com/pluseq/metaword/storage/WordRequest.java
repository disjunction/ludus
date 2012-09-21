package com.pluseq.metaword.storage;

public class WordRequest {
	protected String[] langIds;
	protected String[] tags;
	
	public void setLangIds(String langId) {
		setLangIds(new String[]{langId});
	}
	
	public void setLangIds(String[] langIds) {
		this.langIds = langIds;
	}
	
	public String[] getLangIds() {
		return langIds;
	}
}

package com.xagnhay.kirmancki.model;

public class Langs {
	private long langId;
	private String langShortName;
	private String langName;
	
	public long getLangId() {
		return langId;
	}
	public void setLangId(long langId) {
		this.langId = langId;
	}
	public String getLangShortName() {
		return langShortName;
	}
	public void setLangShortName(String langShortName) {
		this.langShortName = langShortName;
	}
	public String getLangName() {
		return langName;
	}
	public void setLangName(String langName) {
		this.langName = langName;
	}
		
	@Override
	public String toString() {
		return langShortName + "-" + langName;
	}
}

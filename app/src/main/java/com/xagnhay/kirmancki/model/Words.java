package com.xagnhay.kirmancki.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Words implements Parcelable{
	private long wordId;
	private long groupId;
	private long langId;
	private long catId;
	private int wordActive;
	private int wordCustom;
	private String wordType;
	private String wordText;
	private String wordRemarks;
	private String wordUsage;
	private String wordSound;
	private String wordImage;
	
	public long getWordId() {
		return wordId;
	}
	public void setWordId(long wordId) {
		this.wordId = wordId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getLangId() {
		return langId;
	}
	public void setLangId(long langId) {
		this.langId = langId;
	}
	public String getWordType() {
		return wordType;
	}
	public void setWordType(String wordType) {
		this.wordType = wordType;
	}
	public long getCatId() {
		return catId;
	}
	public void setCatId(long catId) {
		this.catId = catId;
	}
	
	public int getWordActive() {
		return wordActive;
	}
	public void setWordActive(int wordActive) {
		this.wordActive = wordActive;
	}
	public int getWordCustom() {
		return wordCustom;
	}
	public void setWordCustom(int wordCustom) {
		this.wordCustom = wordCustom;
	}
	public String getWordText() {
		return wordText;
	}
	public void setWordText(String wordText) {
		this.wordText = wordText;
	}
	public String getWordRemarks() {
		return wordRemarks;
	}
	public void setWordRemarks(String wordRemarks) {
		this.wordRemarks = wordRemarks;
	}
	public String getWordUsage() {
		return wordUsage;
	}
	public void setWordUsage(String wordUsage) {
		this.wordUsage = wordUsage;
	}
	public String getWordSound() {
		return wordSound;
	}
	public void setWordSound(String wordSound) {
		this.wordSound = wordSound;
	}
	public String getWordImage() {
		return wordImage;
	}
	public void setWordImage(String wordImage) {
		this.wordImage = wordImage;
	}
	@Override
	public String toString() {
		return wordText;
	}
	
	// Empty constructor
	public Words() {
	}
	
	// constructor
	public Words(long LangId, long GroupId, long CatId, String WordText, int Active, int Custom){
		this.langId = LangId;
		this.groupId = GroupId;
		this.catId  = CatId;
		this.wordText = WordText;
		this.wordActive = Active;
		this.wordCustom = Custom;
	}
	
	public Words(Parcel in) {
		wordId = in.readLong();
		groupId = in.readLong();
		langId = in.readLong();
		catId = in.readLong();
		wordText = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(wordId);
		dest.writeLong(groupId);
		dest.writeLong(langId);
		dest.writeLong(catId);
		dest.writeString(wordText);		
	}
	
	public static final Parcelable.Creator<Words> CREATOR =
			new Parcelable.Creator<Words>() {
		
		@Override
		public Words createFromParcel(Parcel source) {
			return new Words(source);
		}
		
		@Override
		public Words[] newArray(int size) {
			return new Words[size];
		}
	};
	
}

package com.xagnhay.kirmancki.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
	private long catId;
	private long catLangId;
	private String catName;
	private String catDesc;
	
	public long getCatId() {
		return catId;
	}
	public void setCatId(long catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	public String getCatDesc() {
		return catDesc;
	}
	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}
	
	public long getCatLangId() {
		return catLangId;
	}
	public void setCatLangId(long catlangId) {
		this.catLangId = catlangId;
	}

	@Override
	public String toString() {
		return catName; // + " (" + catId + ")";
		//return catId + " - " + catName + "\n(" + catDesc + ")";
	}
	
	public Category() {
	}
	
	public Category(Parcel in) {
		catId = in.readLong();
		catLangId = in.readLong();
		catName = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(catId);
		dest.writeLong(catLangId);
		dest.writeString(catName);		
	}
	
	public static final Parcelable.Creator<Category> CREATOR =
			new Parcelable.Creator<Category>() {
		
		@Override
		public Category createFromParcel(Parcel source) {
			return new Category(source);
		}
		
		@Override
		public Category[] newArray(int size) {
			return new Category[size];
		}
	};
		
}

package com.xagnhay.kirmancki.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
	private long catId;
	private long catlangId;
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
	
	public long getCatlangId() {
		return catlangId;
	}
	public void setCatlangId(long catlangId) {
		this.catlangId = catlangId;
	}
	@Override
	public String toString() {
		return catName;
		//return catId + " - " + catName + "\n(" + catDesc + ")";
	}
	
	public Category() {
	}
	
	public Category(Parcel in) {
		catId = in.readLong();
		catlangId = in.readLong();
		catName = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(catId);
		dest.writeLong(catlangId);
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

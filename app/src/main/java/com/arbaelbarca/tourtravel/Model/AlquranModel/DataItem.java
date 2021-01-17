package com.arbaelbarca.tourtravel.Model.AlquranModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("number")
	private int number;

	@SerializedName("englishName")
	private String englishName;

	@SerializedName("numberOfAyahs")
	private int numberOfAyahs;

	@SerializedName("revelationType")
	private String revelationType;

	@SerializedName("name")
	private String name;

	@SerializedName("englishNameTranslation")
	private String englishNameTranslation;

	protected DataItem(Parcel in) {
		number = in.readInt();
		englishName = in.readString();
		numberOfAyahs = in.readInt();
		revelationType = in.readString();
		name = in.readString();
		englishNameTranslation = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setEnglishName(String englishName){
		this.englishName = englishName;
	}

	public String getEnglishName(){
		return englishName;
	}

	public void setNumberOfAyahs(int numberOfAyahs){
		this.numberOfAyahs = numberOfAyahs;
	}

	public int getNumberOfAyahs(){
		return numberOfAyahs;
	}

	public void setRevelationType(String revelationType){
		this.revelationType = revelationType;
	}

	public String getRevelationType(){
		return revelationType;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEnglishNameTranslation(String englishNameTranslation){
		this.englishNameTranslation = englishNameTranslation;
	}

	public String getEnglishNameTranslation(){
		return englishNameTranslation;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"number = '" + number + '\'' + 
			",englishName = '" + englishName + '\'' + 
			",numberOfAyahs = '" + numberOfAyahs + '\'' + 
			",revelationType = '" + revelationType + '\'' + 
			",name = '" + name + '\'' + 
			",englishNameTranslation = '" + englishNameTranslation + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(number);
		parcel.writeString(englishName);
		parcel.writeInt(numberOfAyahs);
		parcel.writeString(revelationType);
		parcel.writeString(name);
		parcel.writeString(englishNameTranslation);
	}
}
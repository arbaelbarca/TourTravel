package com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

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

	@SerializedName("edition")
	private Edition edition;

	@SerializedName("ayahs")
	private List<AyahsItemRead> ayahs;

	@SerializedName("englishNameTranslation")
	private String englishNameTranslation;

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

	public void setEdition(Edition edition){
		this.edition = edition;
	}

	public Edition getEdition(){
		return edition;
	}

	public void setAyahs(List<AyahsItemRead> ayahs){
		this.ayahs = ayahs;
	}

	public List<AyahsItemRead> getAyahs(){
		return ayahs;
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
			"Data{" + 
			"number = '" + number + '\'' + 
			",englishName = '" + englishName + '\'' + 
			",numberOfAyahs = '" + numberOfAyahs + '\'' + 
			",revelationType = '" + revelationType + '\'' + 
			",name = '" + name + '\'' + 
			",edition = '" + edition + '\'' + 
			",ayahs = '" + ayahs + '\'' + 
			",englishNameTranslation = '" + englishNameTranslation + '\'' + 
			"}";
		}
}
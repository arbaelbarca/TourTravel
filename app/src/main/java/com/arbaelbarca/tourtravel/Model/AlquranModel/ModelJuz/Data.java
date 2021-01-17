package com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("number")
	private int number;

	@SerializedName("edition")
	private Edition edition;

	@SerializedName("ayahs")
	private List<AyahsItem> ayahs;

	@SerializedName("surahs")
	private Surahs surahs;

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setEdition(Edition edition){
		this.edition = edition;
	}

	public Edition getEdition(){
		return edition;
	}

	public void setAyahs(List<AyahsItem> ayahs){
		this.ayahs = ayahs;
	}

	public List<AyahsItem> getAyahs(){
		return ayahs;
	}

	public void setSurahs(Surahs surahs){
		this.surahs = surahs;
	}

	public Surahs getSurahs(){
		return surahs;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"number = '" + number + '\'' + 
			",edition = '" + edition + '\'' + 
			",ayahs = '" + ayahs + '\'' + 
			",surahs = '" + surahs + '\'' + 
			"}";
		}
}
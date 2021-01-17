package com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz;

import com.google.gson.annotations.SerializedName;

public class AyahsItem{

	@SerializedName("number")
	private int number;

	@SerializedName("hizbQuarter")
	private int hizbQuarter;

	@SerializedName("ruku")
	private int ruku;

	@SerializedName("manzil")
	private int manzil;

	@SerializedName("text")
	private String text;

	@SerializedName("page")
	private int page;

	@SerializedName("sajda")
	private boolean sajda;

	@SerializedName("surah")
	private Surah surah;

	@SerializedName("numberInSurah")
	private int numberInSurah;

	@SerializedName("juz")
	private int juz;

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setHizbQuarter(int hizbQuarter){
		this.hizbQuarter = hizbQuarter;
	}

	public int getHizbQuarter(){
		return hizbQuarter;
	}

	public void setRuku(int ruku){
		this.ruku = ruku;
	}

	public int getRuku(){
		return ruku;
	}

	public void setManzil(int manzil){
		this.manzil = manzil;
	}

	public int getManzil(){
		return manzil;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setSajda(boolean sajda){
		this.sajda = sajda;
	}

	public boolean isSajda(){
		return sajda;
	}

	public void setSurah(Surah surah){
		this.surah = surah;
	}

	public Surah getSurah(){
		return surah;
	}

	public void setNumberInSurah(int numberInSurah){
		this.numberInSurah = numberInSurah;
	}

	public int getNumberInSurah(){
		return numberInSurah;
	}

	public void setJuz(int juz){
		this.juz = juz;
	}

	public int getJuz(){
		return juz;
	}

	@Override
 	public String toString(){
		return 
			"AyahsItemRead{" +
			"number = '" + number + '\'' + 
			",hizbQuarter = '" + hizbQuarter + '\'' + 
			",ruku = '" + ruku + '\'' + 
			",manzil = '" + manzil + '\'' + 
			",text = '" + text + '\'' + 
			",page = '" + page + '\'' + 
			",sajda = '" + sajda + '\'' + 
			",surah = '" + surah + '\'' + 
			",numberInSurah = '" + numberInSurah + '\'' + 
			",juz = '" + juz + '\'' + 
			"}";
		}
}
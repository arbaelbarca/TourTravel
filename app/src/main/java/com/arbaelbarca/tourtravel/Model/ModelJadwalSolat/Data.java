package com.arbaelbarca.tourtravel.Model.ModelJadwalSolat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("Sunset")
	private String sunset;

	@SerializedName("Asr")
	private String asr;

	@SerializedName("Isha")
	private String isha;

	@SerializedName("Fajr")
	private String fajr;

	@SerializedName("Dhuhr")
	private String dhuhr;

	@SerializedName("Maghrib")
	private String maghrib;

	@SerializedName("method")
	private List<String> method;

	@SerializedName("TengahMalam")
	private String tengahMalam;

	@SerializedName("Sunrise")
	private String sunrise;

	@SerializedName("DuapertigaMalam")
	private String duapertigaMalam;

	@SerializedName("SepertigaMalam")
	private String sepertigaMalam;

	public void setSunset(String sunset){
		this.sunset = sunset;
	}

	public String getSunset(){
		return sunset;
	}

	public void setAsr(String asr){
		this.asr = asr;
	}

	public String getAsr(){
		return asr;
	}

	public void setIsha(String isha){
		this.isha = isha;
	}

	public String getIsha(){
		return isha;
	}

	public void setFajr(String fajr){
		this.fajr = fajr;
	}

	public String getFajr(){
		return fajr;
	}

	public void setDhuhr(String dhuhr){
		this.dhuhr = dhuhr;
	}

	public String getDhuhr(){
		return dhuhr;
	}

	public void setMaghrib(String maghrib){
		this.maghrib = maghrib;
	}

	public String getMaghrib(){
		return maghrib;
	}

	public void setMethod(List<String> method){
		this.method = method;
	}

	public List<String> getMethod(){
		return method;
	}

	public void setTengahMalam(String tengahMalam){
		this.tengahMalam = tengahMalam;
	}

	public String getTengahMalam(){
		return tengahMalam;
	}

	public void setSunrise(String sunrise){
		this.sunrise = sunrise;
	}

	public String getSunrise(){
		return sunrise;
	}

	public void setDuapertigaMalam(String duapertigaMalam){
		this.duapertigaMalam = duapertigaMalam;
	}

	public String getDuapertigaMalam(){
		return duapertigaMalam;
	}

	public void setSepertigaMalam(String sepertigaMalam){
		this.sepertigaMalam = sepertigaMalam;
	}

	public String getSepertigaMalam(){
		return sepertigaMalam;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"sunset = '" + sunset + '\'' + 
			",asr = '" + asr + '\'' + 
			",isha = '" + isha + '\'' + 
			",fajr = '" + fajr + '\'' + 
			",dhuhr = '" + dhuhr + '\'' + 
			",maghrib = '" + maghrib + '\'' + 
			",method = '" + method + '\'' + 
			",tengahMalam = '" + tengahMalam + '\'' + 
			",sunrise = '" + sunrise + '\'' + 
			",duapertigaMalam = '" + duapertigaMalam + '\'' + 
			",sepertigaMalam = '" + sepertigaMalam + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.ModelJadwalSolat;

import com.google.gson.annotations.SerializedName;

public class Time{

	@SerializedName("date")
	private String date;

	@SerializedName("offset")
	private int offset;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("time")
	private String time;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	@Override
 	public String toString(){
		return 
			"Time{" + 
			"date = '" + date + '\'' + 
			",offset = '" + offset + '\'' + 
			",timezone = '" + timezone + '\'' + 
			",time = '" + time + '\'' + 
			"}";
		}
}
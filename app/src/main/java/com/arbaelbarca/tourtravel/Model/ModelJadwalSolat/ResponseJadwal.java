package com.arbaelbarca.tourtravel.Model.ModelJadwalSolat;

import com.google.gson.annotations.SerializedName;

public class ResponseJadwal{

	@SerializedName("debug")
	private Debug debug;

	@SerializedName("data")
	private Data data;

	@SerializedName("location")
	private Location location;

	@SerializedName("time")
	private Time time;

	@SerializedName("status")
	private String status;

	public void setDebug(Debug debug){
		this.debug = debug;
	}

	public Debug getDebug(){
		return debug;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setTime(Time time){
		this.time = time;
	}

	public Time getTime(){
		return time;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseJadwal{" + 
			"debug = '" + debug + '\'' + 
			",data = '" + data + '\'' + 
			",location = '" + location + '\'' + 
			",time = '" + time + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
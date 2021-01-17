package com.arbaelbarca.tourtravel.Model.ModelProfile;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile{

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProfile{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.AdminUmroh;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdminResult{

	@SerializedName("result")
	private List<ResultItemAdmin> result;

	public void setResult(List<ResultItemAdmin> result){
		this.result = result;
	}

	public List<ResultItemAdmin> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"AdminResult{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
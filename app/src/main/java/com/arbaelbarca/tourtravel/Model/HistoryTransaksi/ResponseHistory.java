package com.arbaelbarca.tourtravel.Model.HistoryTransaksi;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseHistory{

	@SerializedName("result")
	private ArrayList<ResultItem> result;

	@SerializedName("token_random")
	private String token_random;

	public String getToken_random() {
		return token_random;
	}

	public void setToken_random(String token_random) {
		this.token_random = token_random;
	}

	public void setResult(ArrayList<ResultItem> result){
		this.result = result;
	}

	public ArrayList<ResultItem> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponseHistory{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
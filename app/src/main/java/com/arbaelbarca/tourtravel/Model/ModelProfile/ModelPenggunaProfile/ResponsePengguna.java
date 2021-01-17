package com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePengguna implements Serializable {

	@SerializedName("result")
	private List<ResultItem> result;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePengguna{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
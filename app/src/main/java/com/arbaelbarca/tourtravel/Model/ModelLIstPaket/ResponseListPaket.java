package com.arbaelbarca.tourtravel.Model.ModelLIstPaket;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListPaket{

	@SerializedName("result")
	private List<ResultItemPaket> result;

	public void setResult(List<ResultItemPaket> result){
		this.result = result;
	}

	public List<ResultItemPaket> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponseListPaket{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz;

import com.google.gson.annotations.SerializedName;

public class Surahs{

	@SerializedName("1")
	private JsonMember1 jsonMember1;

	@SerializedName("2")
	private JsonMember2 jsonMember2;

	public void setJsonMember1(JsonMember1 jsonMember1){
		this.jsonMember1 = jsonMember1;
	}

	public JsonMember1 getJsonMember1(){
		return jsonMember1;
	}

	public void setJsonMember2(JsonMember2 jsonMember2){
		this.jsonMember2 = jsonMember2;
	}

	public JsonMember2 getJsonMember2(){
		return jsonMember2;
	}

	@Override
 	public String toString(){
		return 
			"Surahs{" + 
			"1 = '" + jsonMember1 + '\'' + 
			",2 = '" + jsonMember2 + '\'' + 
			"}";
		}
}
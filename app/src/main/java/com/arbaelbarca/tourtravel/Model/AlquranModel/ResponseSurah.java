package com.arbaelbarca.tourtravel.Model.AlquranModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSurah implements Parcelable {

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private String status;

	protected ResponseSurah(Parcel in) {
		code = in.readInt();
		status = in.readString();
	}

	public static final Creator<ResponseSurah> CREATOR = new Creator<ResponseSurah>() {
		@Override
		public ResponseSurah createFromParcel(Parcel in) {
			return new ResponseSurah(in);
		}

		@Override
		public ResponseSurah[] newArray(int size) {
			return new ResponseSurah[size];
		}
	};

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
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
			"ResponseSurah{" + 
			"code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(code);
		parcel.writeString(status);
	}
}
package com.arbaelbarca.tourtravel.Model.AdminUmroh;

import com.google.gson.annotations.SerializedName;

public class ResponseAdmin {

	@SerializedName("alamat_vendor")
	private String alamatVendor;

	@SerializedName("no_tlp")
	private String noTlp;

	@SerializedName("latlong")
	private String latlong;

	@SerializedName("id")
	private String id;

	@SerializedName("nama_vendor")
	private String namaVendor;

	@SerializedName("email")
	private String email;

	@SerializedName("timestamp")
	private String timestamp;

	public void setAlamatVendor(String alamatVendor){
		this.alamatVendor = alamatVendor;
	}

	public String getAlamatVendor(){
		return alamatVendor;
	}

	public void setNoTlp(String noTlp){
		this.noTlp = noTlp;
	}

	public String getNoTlp(){
		return noTlp;
	}

	public void setLatlong(String latlong){
		this.latlong = latlong;
	}

	public String getLatlong(){
		return latlong;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setNamaVendor(String namaVendor){
		this.namaVendor = namaVendor;
	}

	public String getNamaVendor(){
		return namaVendor;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}

	public String getTimestamp(){
		return timestamp;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAdmin{" +
			"alamat_vendor = '" + alamatVendor + '\'' + 
			",no_tlp = '" + noTlp + '\'' + 
			",latlong = '" + latlong + '\'' + 
			",id = '" + id + '\'' + 
			",nama_vendor = '" + namaVendor + '\'' + 
			",email = '" + email + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}
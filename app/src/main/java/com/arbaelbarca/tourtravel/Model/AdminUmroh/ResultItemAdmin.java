package com.arbaelbarca.tourtravel.Model.AdminUmroh;

import com.google.gson.annotations.SerializedName;

public class ResultItemAdmin {

	@SerializedName("password")
	private String password;

	@SerializedName("alamat_vendor")
	private String alamatVendor;

	@SerializedName("logo_vendor")
	private String logo_vendor;

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

	@SerializedName("username")
	private String username;

	@SerializedName("timestamp")
	private String timestamp;

	public String getLogo_vendor() {
		return logo_vendor;
	}

	public void setLogo_vendor(String logo_vendor) {
		this.logo_vendor = logo_vendor;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

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

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
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
			"ResultItemAdmin{" +
			"password = '" + password + '\'' + 
			",alamat_vendor = '" + alamatVendor + '\'' + 
			",no_tlp = '" + noTlp + '\'' + 
			",latlong = '" + latlong + '\'' + 
			",id = '" + id + '\'' + 
			",nama_vendor = '" + namaVendor + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.HistoryTransaksi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoPenggunaItem implements Serializable {

	@SerializedName("status_penjual")
	private String statusPenjual;

	@SerializedName("latlong_current")
	private String latlongCurrent;

	@SerializedName("password")
	private String password;

	@SerializedName("no_tlp")
	private String noTlp;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("id")
	private String id;

	@SerializedName("alamat_rumah")
	private String alamatRumah;

	@SerializedName("photo_pengguna")
	private String photoPengguna;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("timestamp")
	private String timestamp;

	public void setStatusPenjual(String statusPenjual){
		this.statusPenjual = statusPenjual;
	}

	public String getStatusPenjual(){
		return statusPenjual;
	}

	public void setLatlongCurrent(String latlongCurrent){
		this.latlongCurrent = latlongCurrent;
	}

	public String getLatlongCurrent(){
		return latlongCurrent;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNoTlp(String noTlp){
		this.noTlp = noTlp;
	}

	public String getNoTlp(){
		return noTlp;
	}

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAlamatRumah(String alamatRumah){
		this.alamatRumah = alamatRumah;
	}

	public String getAlamatRumah(){
		return alamatRumah;
	}

	public void setPhotoPengguna(String photoPengguna){
		this.photoPengguna = photoPengguna;
	}

	public String getPhotoPengguna(){
		return photoPengguna;
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
			"InfoPenggunaItem{" + 
			"status_penjual = '" + statusPenjual + '\'' + 
			",latlong_current = '" + latlongCurrent + '\'' + 
			",password = '" + password + '\'' + 
			",no_tlp = '" + noTlp + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",id = '" + id + '\'' + 
			",alamat_rumah = '" + alamatRumah + '\'' + 
			",photo_pengguna = '" + photoPengguna + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.ModelLIstPaket;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItemPaket implements Serializable {

	@SerializedName("deskripsi_paket")
	private String deskripsiPaket;

	@SerializedName("id_vendor")
	private String idVendor;

	@SerializedName("id")
	private String id;

	@SerializedName("nama_paket")
	private String namaPaket;

	@SerializedName("nama_vendor")
	private String namaVendor;

	@SerializedName("timestamp")
	private String timestamp;

	@SerializedName("harga_paket")
	private String harga_paket;

	int isChecked;

	public int isChecked() {
		return isChecked;
	}

	public void setChecked(int checked) {
		isChecked = checked;
	}

	public String getHarga_paket() {
		return harga_paket;
	}

	public void setHarga_paket(String harga_paket) {
		this.harga_paket = harga_paket;
	}

	public void setDeskripsiPaket(String deskripsiPaket){
		this.deskripsiPaket = deskripsiPaket;
	}

	public String getDeskripsiPaket(){
		return deskripsiPaket;
	}

	public void setIdVendor(String idVendor){
		this.idVendor = idVendor;
	}

	public String getIdVendor(){
		return idVendor;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setNamaPaket(String namaPaket){
		this.namaPaket = namaPaket;
	}

	public String getNamaPaket(){
		return namaPaket;
	}

	public void setNamaVendor(String namaVendor){
		this.namaVendor = namaVendor;
	}

	public String getNamaVendor(){
		return namaVendor;
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
			"ResultItemPaket{" +
			"deskripsi_paket = '" + deskripsiPaket + '\'' + 
			",id_vendor = '" + idVendor + '\'' + 
			",id = '" + id + '\'' + 
			",nama_paket = '" + namaPaket + '\'' + 
			",nama_vendor = '" + namaVendor + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}
package com.arbaelbarca.tourtravel.Model.HistoryTransaksi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoPaketItem implements Serializable {

	@SerializedName("harga_paket")
	private String hargaPaket;

	@SerializedName("deskripsi_paket")
	private String deskripsiPaket;

	@SerializedName("id_vendor")
	private String idVendor;

	@SerializedName("id")
	private String id;

	@SerializedName("nama_paket")
	private String namaPaket;

	@SerializedName("timestamp")
	private String timestamp;

	public void setHargaPaket(String hargaPaket){
		this.hargaPaket = hargaPaket;
	}

	public String getHargaPaket(){
		return hargaPaket;
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

	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}

	public String getTimestamp(){
		return timestamp;
	}

	@Override
 	public String toString(){
		return 
			"InfoPaketItem{" + 
			"harga_paket = '" + hargaPaket + '\'' + 
			",deskripsi_paket = '" + deskripsiPaket + '\'' + 
			",id_vendor = '" + idVendor + '\'' + 
			",id = '" + id + '\'' + 
			",nama_paket = '" + namaPaket + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}
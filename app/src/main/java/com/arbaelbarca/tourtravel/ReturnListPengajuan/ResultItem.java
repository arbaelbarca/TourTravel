package com.arbaelbarca.tourtravel.ReturnListPengajuan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("no_sk")
	private String noSk;

	@SerializedName("rekening_uang")
	private String rekeningUang;

	@SerializedName("status_pengajuan")
	private String statusPengajuan;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("nama_perusahaan")
	private String namaPerusahaan;

	@SerializedName("id")
	private String id;

	@SerializedName("direktur_utama")
	private String direkturUtama;

	@SerializedName("tanggal_sk")
	private String tanggalSk;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("ktp_dirut")
	private String ktpDirut;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setNoSk(String noSk){
		this.noSk = noSk;
	}

	public String getNoSk(){
		return noSk;
	}

	public void setRekeningUang(String rekeningUang){
		this.rekeningUang = rekeningUang;
	}

	public String getRekeningUang(){
		return rekeningUang;
	}

	public void setStatusPengajuan(String statusPengajuan){
		this.statusPengajuan = statusPengajuan;
	}

	public String getStatusPengajuan(){
		return statusPengajuan;
	}

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
	}

	public void setNamaPerusahaan(String namaPerusahaan){
		this.namaPerusahaan = namaPerusahaan;
	}

	public String getNamaPerusahaan(){
		return namaPerusahaan;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setDirekturUtama(String direkturUtama){
		this.direkturUtama = direkturUtama;
	}

	public String getDirekturUtama(){
		return direkturUtama;
	}

	public void setTanggalSk(String tanggalSk){
		this.tanggalSk = tanggalSk;
	}

	public String getTanggalSk(){
		return tanggalSk;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setKtpDirut(String ktpDirut){
		this.ktpDirut = ktpDirut;
	}

	public String getKtpDirut(){
		return ktpDirut;
	}

	@Override
 	public String toString(){
		return 
			"ResultItemPaket{" +
			"result = '" + result + '\'' + 
			",no_sk = '" + noSk + '\'' + 
			",rekening_uang = '" + rekeningUang + '\'' + 
			",status_pengajuan = '" + statusPengajuan + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",nama_perusahaan = '" + namaPerusahaan + '\'' + 
			",id = '" + id + '\'' + 
			",direktur_utama = '" + direkturUtama + '\'' + 
			",tanggal_sk = '" + tanggalSk + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",ktp_dirut = '" + ktpDirut + '\'' + 
			"}";
		}
}
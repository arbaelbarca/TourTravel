package com.arbaelbarca.tourtravel.Model.HistoryTransaksi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberAdditionItem implements Serializable {

    @SerializedName("nama")
    private String nama;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("id")
    private String id;

    @SerializedName("id_vendor_transaksi")
    private String idVendorTransaksi;

    @SerializedName("timestamp")
    private String timestamp;

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIdVendorTransaksi(String idVendorTransaksi) {
        this.idVendorTransaksi = idVendorTransaksi;
    }

    public String getIdVendorTransaksi() {
        return idVendorTransaksi;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return
                "MemberAdditionItem{" +
                        "nama = '" + nama + '\'' +
                        ",no_hp = '" + noHp + '\'' +
                        ",id = '" + id + '\'' +
                        ",id_vendor_transaksi = '" + idVendorTransaksi + '\'' +
                        ",timestamp = '" + timestamp + '\'' +
                        "}";
    }
}
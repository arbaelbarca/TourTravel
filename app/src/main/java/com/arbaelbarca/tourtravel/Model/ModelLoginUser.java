package com.arbaelbarca.tourtravel.Model;

import com.google.gson.annotations.SerializedName;

public class ModelLoginUser {
    @SerializedName("nama_lengkap")
    public String nama_lengkap;

    @SerializedName("alamat_rumah")
    public String alamat_rumah;

    @SerializedName("email")
    public String email;

    @SerializedName("no_tlp")
    public String no_tlp;

    @SerializedName("latlong")
    public String latlong;

    @SerializedName("status_penjual")
    public String status_penjual;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;



    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getAlamat_rumah() {
        return alamat_rumah;
    }

    public void setAlamat_rumah(String alamat_rumah) {
        this.alamat_rumah = alamat_rumah;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getStatus_penjual() {
        return status_penjual;
    }

    public void setStatus_penjual(String status_penjual) {
        this.status_penjual = status_penjual;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

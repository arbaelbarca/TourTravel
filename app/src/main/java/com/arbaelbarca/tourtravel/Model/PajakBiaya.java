package com.arbaelbarca.tourtravel.Model;

public class PajakBiaya {
    public String biaya, judul;

    public PajakBiaya(String biaya, String judul) {
        this.biaya = biaya;
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
}

package com.arbaelbarca.tourtravel.Model;

public class CheckoutOrderUmroh {
    String id_pengguna, id_vendor,id_paket,status_konfirmasi,photo_bukti,json_member;

    public String getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getId_vendor() {
        return id_vendor;
    }

    public void setId_vendor(String id_vendor) {
        this.id_vendor = id_vendor;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getStatus_konfirmasi() {
        return status_konfirmasi;
    }

    public void setStatus_konfirmasi(String status_konfirmasi) {
        this.status_konfirmasi = status_konfirmasi;
    }

    public String getPhoto_bukti() {
        return photo_bukti;
    }

    public void setPhoto_bukti(String photo_bukti) {
        this.photo_bukti = photo_bukti;
    }

    public String getJson_member() {
        return json_member;
    }

    public void setJson_member(String json_member) {
        this.json_member = json_member;
    }
}

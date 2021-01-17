package com.arbaelbarca.tourtravel.Model.HistoryTransaksi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ResultItem implements Parcelable {

    @SerializedName("photo_bukti")
    private String photoBukti;

    @SerializedName("photo_invoice")
    private String photo_invoice;

    @SerializedName("member_lain")
    private String memberLain;

    @SerializedName("no_invoice")
    private String noInvoice;

    @SerializedName("status_konfirmasi")
    private String statusKonfirmasi;

    @SerializedName("info_vendor")
    private List<InfoVendorItem> infoVendor;

    @SerializedName("id_vendor")
    private String idVendor;

    @SerializedName("info_pengguna")
    private ArrayList<InfoPenggunaItem> infoPengguna;

    @SerializedName("id")
    private String id;

    @SerializedName("id_paket")
    private String idPaket;

    @SerializedName("member_addition")
    private ArrayList<MemberAdditionItem> memberAddition;

    @SerializedName("id_pengguna")
    private String idPengguna;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("total")
    private String total;

    @SerializedName("info_paket")
    private ArrayList<InfoPaketItem> infoPaket;

    @SerializedName("judul_pajak_a")
    private String judul_pajak_a;

    @SerializedName("judul_pajak_b")
    private String judul_pajak_b;

    @SerializedName("judul_pajak_c")
    private String judul_pajak_c;

    @SerializedName("pajak_b")
    private String pajak_b;

    @SerializedName("pajak_a")
    private String pajak_a;

    @SerializedName("pajak_c")
    private String pajak_c;


    protected ResultItem(Parcel in) {
        photoBukti = in.readString();
        photo_invoice = in.readString();
        memberLain = in.readString();
        noInvoice = in.readString();
        statusKonfirmasi = in.readString();
        idVendor = in.readString();
        id = in.readString();
        idPaket = in.readString();
        idPengguna = in.readString();
        timestamp = in.readString();
        total = in.readString();
        judul_pajak_a = in.readString();
        judul_pajak_b = in.readString();
        judul_pajak_c = in.readString();
        pajak_b = in.readString();
        pajak_a = in.readString();
        pajak_c = in.readString();
    }

    public static final Creator<ResultItem> CREATOR = new Creator<ResultItem>() {
        @Override
        public ResultItem createFromParcel(Parcel in) {
            return new ResultItem(in);
        }

        @Override
        public ResultItem[] newArray(int size) {
            return new ResultItem[size];
        }
    };

    public String getPhoto_invoice() {
        return photo_invoice;
    }

    public void setPhoto_invoice(String photo_invoice) {
        this.photo_invoice = photo_invoice;
    }

    public String getJudul_pajak_a() {
        return judul_pajak_a;
    }

    public void setJudul_pajak_a(String judul_pajak_a) {
        this.judul_pajak_a = judul_pajak_a;
    }

    public String getJudul_pajak_b() {
        return judul_pajak_b;
    }

    public void setJudul_pajak_b(String judul_pajak_b) {
        this.judul_pajak_b = judul_pajak_b;
    }

    public String getJudul_pajak_c() {
        return judul_pajak_c;
    }

    public void setJudul_pajak_c(String judul_pajak_c) {
        this.judul_pajak_c = judul_pajak_c;
    }

    public String getPajak_b() {
        return pajak_b;
    }

    public void setPajak_b(String pajak_b) {
        this.pajak_b = pajak_b;
    }

    public String getPajak_a() {
        return pajak_a;
    }

    public void setPajak_a(String pajak_a) {
        this.pajak_a = pajak_a;
    }

    public String getPajak_c() {
        return pajak_c;
    }

    public void setPajak_c(String pajak_c) {
        this.pajak_c = pajak_c;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPhotoBukti(String photoBukti) {
        this.photoBukti = photoBukti;
    }

    public String getPhotoBukti() {
        return photoBukti;
    }

    public void setMemberLain(String memberLain) {
        this.memberLain = memberLain;
    }

    public String getMemberLain() {
        return memberLain;
    }

    public void setStatusKonfirmasi(String statusKonfirmasi) {
        this.statusKonfirmasi = statusKonfirmasi;
    }

    public String getStatusKonfirmasi() {
        return statusKonfirmasi;
    }

    public void setInfoVendor(List<InfoVendorItem> infoVendor) {
        this.infoVendor = infoVendor;
    }

    public List<InfoVendorItem> getInfoVendor() {
        return infoVendor;
    }

    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

    public String getIdVendor() {
        return idVendor;
    }

    public void setInfoPengguna(ArrayList<InfoPenggunaItem> infoPengguna) {
        this.infoPengguna = infoPengguna;
    }

    public List<InfoPenggunaItem> getInfoPengguna() {
        return infoPengguna;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIdPaket(String idPaket) {
        this.idPaket = idPaket;
    }

    public String getIdPaket() {
        return idPaket;
    }

    public void setMemberAddition(ArrayList<MemberAdditionItem> memberAddition) {
        this.memberAddition = memberAddition;
    }

    public List<MemberAdditionItem> getMemberAddition() {
        return memberAddition;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setInfoPaket(ArrayList<InfoPaketItem> infoPaket) {
        this.infoPaket = infoPaket;
    }

    public ArrayList<InfoPaketItem> getInfoPaket() {
        return infoPaket;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "photo_bukti = '" + photoBukti + '\'' +
                        ",member_lain = '" + memberLain + '\'' +
                        ",status_konfirmasi = '" + statusKonfirmasi + '\'' +
                        ",info_vendor = '" + infoVendor + '\'' +
                        ",id_vendor = '" + idVendor + '\'' +
                        ",info_pengguna = '" + infoPengguna + '\'' +
                        ",id = '" + id + '\'' +
                        ",id_paket = '" + idPaket + '\'' +
                        ",member_addition = '" + memberAddition + '\'' +
                        ",id_pengguna = '" + idPengguna + '\'' +
                        ",timestamp = '" + timestamp + '\'' +
                        ",info_paket = '" + infoPaket + '\'' +
                        "}";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photoBukti);
        parcel.writeString(photo_invoice);
        parcel.writeString(memberLain);
        parcel.writeString(noInvoice);
        parcel.writeString(statusKonfirmasi);
        parcel.writeString(idVendor);
        parcel.writeString(id);
        parcel.writeString(idPaket);
        parcel.writeString(idPengguna);
        parcel.writeString(timestamp);
        parcel.writeString(total);
        parcel.writeString(judul_pajak_a);
        parcel.writeString(judul_pajak_b);
        parcel.writeString(judul_pajak_c);
        parcel.writeString(pajak_b);
        parcel.writeString(pajak_a);
        parcel.writeString(pajak_c);
    }
}
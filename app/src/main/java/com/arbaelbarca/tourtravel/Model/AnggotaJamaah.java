package com.arbaelbarca.tourtravel.Model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AnggotaJamaah implements Parcelable {
    public String name, nohp, photo_ktp, hargaAnggota;
    public String logBase64;
    public boolean isSeleted;


    protected AnggotaJamaah(Parcel in) {
        name = in.readString();
        nohp = in.readString();
        photo_ktp = in.readString();
        hargaAnggota = in.readString();
        logBase64 = in.readString();
        isSeleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nohp);
        dest.writeString(photo_ktp);
        dest.writeString(hargaAnggota);
        dest.writeString(logBase64);
        dest.writeByte((byte) (isSeleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnggotaJamaah> CREATOR = new Creator<AnggotaJamaah>() {
        @Override
        public AnggotaJamaah createFromParcel(Parcel in) {
            return new AnggotaJamaah(in);
        }

        @Override
        public AnggotaJamaah[] newArray(int size) {
            return new AnggotaJamaah[size];
        }
    };

    public String getLogBase64() {
        return logBase64;
    }

    public void setLogBase64(String logBase64) {
        this.logBase64 = logBase64;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }


    public AnggotaJamaah(String name, String nohp, String photo_ktp, String hargaAnggota, String logBase64, boolean isSeleted) {
        this.name = name;
        this.nohp = nohp;
        this.photo_ktp = photo_ktp;
        this.hargaAnggota = hargaAnggota;
        this.logBase64 = logBase64;
        this.isSeleted = isSeleted;
    }

    public String getHargaAnggota() {
        return hargaAnggota;
    }

    public void setHargaAnggota(String hargaAnggota) {
        this.hargaAnggota = hargaAnggota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }


    public String getPhoto_ktp() {
        return photo_ktp;
    }

    public void setPhoto_ktp(String photo_ktp) {
        this.photo_ktp = photo_ktp;
    }



}

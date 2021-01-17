package com.arbaelbarca.tourtravel.Favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favoriteSurah")
public class FavoriteList implements Parcelable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "surah")
    private String surah;

    @ColumnInfo(name = "ayat")
    private String ayat;

    @ColumnInfo(name = "number_ayat")
    private String number;

    @ColumnInfo(name = "number_surah")
    private String number_surah;

    public FavoriteList(){

    }

    protected FavoriteList(Parcel in) {
        id = in.readInt();
        surah = in.readString();
        ayat = in.readString();
        number = in.readString();
        number_surah = in.readString();
    }

    public static final Creator<FavoriteList> CREATOR = new Creator<FavoriteList>() {
        @Override
        public FavoriteList createFromParcel(Parcel in) {
            return new FavoriteList(in);
        }

        @Override
        public FavoriteList[] newArray(int size) {
            return new FavoriteList[size];
        }
    };

    public String getNumber_surah() {
        return number_surah;
    }

    public void setNumber_surah(String number_surah) {
        this.number_surah = number_surah;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(surah);
        parcel.writeString(ayat);
        parcel.writeString(number);
    }
}

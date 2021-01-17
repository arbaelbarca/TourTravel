package com.arbaelbarca.tourtravel.Favorite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    public void addData(FavoriteList fa);

    @Query("select * from favoriteSurah")
    public List<FavoriteList> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteSurah WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteList favoriteList);
}

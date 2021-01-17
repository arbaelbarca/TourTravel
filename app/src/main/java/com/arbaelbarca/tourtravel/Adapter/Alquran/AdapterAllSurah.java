package com.arbaelbarca.tourtravel.Adapter.Alquran;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Adapter.AdapterDetailReadQuran;
import com.arbaelbarca.tourtravel.Favorite.FavoriteList;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.AlquranModel.DataItem;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Surah;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran.AyahsItemRead;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ResponseSurah;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterAllSurah extends RecyclerView.Adapter<AdapterAllSurah.ViewHolder> {
    Context context;
    List<DataItem> surahList;
    onClickRead onClickRead;
    setFavorite setFavorite;

    public AdapterAllSurah(Context context, List<DataItem> surahList) {
        this.context = context;
        this.surahList = surahList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_surah, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DataItem surah = surahList.get(i);

        viewHolder.txtNumber.setText(String.valueOf(surah.getNumber()));
        viewHolder.txtJudul.setText(surah.getEnglishName());
        viewHolder.txtJumlahAyat.setText(String.valueOf(surah.getNumberOfAyahs()) + " Ayat");
        viewHolder.txtLafal.setText(surah.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRead.clickDetail(view, i, surah);
            }
        });



    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber, txtJudul, txtJumlahAyat, txtLafal;
        ImageView imgSaveFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.txtNumberSurah);
            txtJudul = itemView.findViewById(R.id.txtJudulSurah);
            txtJumlahAyat = itemView.findViewById(R.id.txtAyatJumlah);
            txtLafal = itemView.findViewById(R.id.txtLafalSurah);

            imgSaveFav = itemView.findViewById(R.id.favSurah);

        }
    }

    public interface onClickRead {
        void clickDetail(View view, int pos, DataItem dataItem);
    }

    public void Onclick(onClickRead clickRead) {
        onClickRead = clickRead;
    }

    public interface setFavorite {
        void fav(int pos, DataItem a);
    }

    public void isFavorite(setFavorite favorite) {
        setFavorite = favorite;
    }
}

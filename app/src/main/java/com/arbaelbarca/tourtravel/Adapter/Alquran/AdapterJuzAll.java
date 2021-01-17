package com.arbaelbarca.tourtravel.Adapter.Alquran;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Model.AlquranModel.DataItem;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Data;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Surah;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Surahs;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterJuzAll extends RecyclerView.Adapter<AdapterJuzAll.ViewHolder> {
    Context context;
    List<Data> surahList;
    Surahs surahs;

    public AdapterJuzAll(Context context, List<Data> surahList, Surahs surahs) {
        this.context = context;
        this.surahList = surahList;
        this.surahs = surahs;
    }

    //    public void reCreate(Surahs surahs){
//        this.surahList.add(surahs);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_surah, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Data surah = surahList.get(i);

        for (Data s : surahList) {
            Log.d("responSurah", " " + s.getSurahs());
        }
//        viewHolder.txtNumber.setText(String.valueOf(surah.getJsonMember1()));
//        viewHolder.txtJudul.setText(surah.getEnglishName());
//        viewHolder.txtJumlahAyat.setText(String.valueOf(surah.getNumberOfAyahs()) + " Ayat");
//        viewHolder.txtLafal.setText(surah.getName());
    }


    @Override
    public int getItemCount() {
        return surahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber, txtJudul, txtJumlahAyat, txtLafal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.txtNumberSurah);
            txtJudul = itemView.findViewById(R.id.txtJudulSurah);
            txtJumlahAyat = itemView.findViewById(R.id.txtAyatJumlah);
            txtLafal = itemView.findViewById(R.id.txtLafalSurah);
        }
    }
}

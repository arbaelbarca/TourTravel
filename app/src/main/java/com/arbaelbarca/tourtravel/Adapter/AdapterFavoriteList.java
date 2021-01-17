package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Favorite.FavoriteList;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterFavoriteList extends RecyclerView.Adapter<AdapterFavoriteList.ViewHolder> {
    Context context;
    List<FavoriteList> favoriteLists;
    clickDetail clickDetail;

    public AdapterFavoriteList(Context context, List<FavoriteList> favoriteLists) {
        this.context = context;
        this.favoriteLists = favoriteLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_surah, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final FavoriteList fav = favoriteLists.get(i);
        viewHolder.txtJudul.setText(fav.getSurah());
        viewHolder.txtLafal.setText(fav.getAyat());
        viewHolder.txtNumber.setText(String.valueOf(fav.getId()));
        viewHolder.txtJumlahAyat.setText(fav.getNumber());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDetail.onActionClick(view, i, fav);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
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

    public interface clickDetail {
        void onActionClick(View v, int pos, FavoriteList favoriteList);
    }

    public void onClick(clickDetail detail) {
        clickDetail = detail;
    }
}

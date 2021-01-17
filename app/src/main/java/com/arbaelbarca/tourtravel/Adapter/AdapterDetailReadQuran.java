package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran.AyahsItemRead;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterDetailReadQuran extends RecyclerView.Adapter<AdapterDetailReadQuran.ViewHolder> {
    Context context;
    List<AyahsItemRead> itemReadList;
    setFavorite setFavorite;

//    List<String> stringList;


    public AdapterDetailReadQuran(Context context, List<AyahsItemRead> itemReadList) {
        this.context = context;
        this.itemReadList = itemReadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_read_quran, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final AyahsItemRead itemRead = itemReadList.get(i);
        viewHolder.txtNumber.setText(String.valueOf(itemRead.getNumberInSurah()));
        viewHolder.txtAyat.setText(itemRead.getText());
//        String joinALl = String.join(",", )
    }

    @Override
    public int getItemCount() {
        return itemReadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAyat, txtNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAyat = itemView.findViewById(R.id.txtAyatRead);
            txtNumber = itemView.findViewById(R.id.txtNumberAyat);


        }
    }

    public interface setFavorite {
        void fav(View v, int pos, AyahsItemRead a);
    }

    public void isFavorite(setFavorite favorite) {
        setFavorite = favorite;
    }


}

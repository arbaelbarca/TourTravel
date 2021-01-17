package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arbaelbarca.tourtravel.Model.PajakBiaya;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterTambahPajak extends RecyclerView.Adapter<AdapterTambahPajak.ViewHolder> {
    Context context;
    List<PajakBiaya> biayaList;

    public AdapterTambahPajak(Context context, List<PajakBiaya> biayaList) {
        this.context = context;
        this.biayaList = biayaList;
    }

    public void reCreate(int pos, PajakBiaya biaya) {
        this.biayaList.add(biaya);
        notifyItemInserted(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout__biayapajak, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PajakBiaya pajakBiaya = biayaList.get(i);
        viewHolder.textBiaya.setText(pajakBiaya.getBiaya());
        viewHolder.judulBiaya.setHint(pajakBiaya.getJudul());

    }

    @Override
    public int getItemCount() {
        return biayaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText textBiaya;
        TextInputLayout judulBiaya;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textBiaya = itemView.findViewById(R.id.txtBiayaPajak);
            judulBiaya = itemView.findViewById(R.id.judulBiaya);
        }
    }
}

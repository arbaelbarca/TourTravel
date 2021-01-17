package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.DetailUmroh;
import com.arbaelbarca.tourtravel.Model.AnggotaJamaah;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAddJamaah extends RecyclerView.Adapter<AdapterAddJamaah.ViewHolder> {
    Context context;
    List<AnggotaJamaah> jamaahList;
    List<ResultItem> items;
    viewHarga viewHarga;
    viewDetail viewDetail, viewDelete;

    public AdapterAddJamaah(Context context, List<AnggotaJamaah> jamaahList) {
        this.context = context;
        this.jamaahList = jamaahList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_anggotajamaah, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final AnggotaJamaah jamaah = jamaahList.get(i);

        if (jamaahList.size() > 0) {
            viewHolder.txtName.setText(jamaah.getName());
            viewHolder.txtPhone.setText(jamaah.getNohp());
            viewHolder.txtHargaAnggota.setText("Rp " + jamaah.getHargaAnggota());
            Log.d("responName", "n " + jamaah.getName());

        }

        if (!jamaah.getPhoto_ktp().equals("")) {
//            Glide.with(context)
//                    .load(jamaah.getPhoto_ktp())
//                    .into(viewHolder.imgAnggota);

            Picasso.with(context).load(jamaah.getLogBase64()).into(viewHolder.imgAnggota);

        }


        if (viewHarga != null) {
            viewHarga.onViewHarga(viewHolder.txtHargaAnggota);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDetail != null) {
                    viewDetail.onViewDetail(v, i, jamaah);
                }
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Apakah anda yakin ingin delete");


                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(i);
                    }
                }).setNegativeButton("Tidak ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.create().show();
            }
        });

        viewHolder.cbJamaah.setChecked(jamaah.isSeleted());
        viewHolder.cbJamaah.setTag(jamaahList.get(i));

        viewHolder.cbJamaah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = "";
                AnggotaJamaah anggotaJamaah = (AnggotaJamaah) viewHolder.cbJamaah.getTag();
                anggotaJamaah.setSeleted(viewHolder.cbJamaah.isChecked());
                jamaahList.get(i).setSeleted(viewHolder.cbJamaah.isChecked());
                for (int x = 0; x < jamaahList.size(); x++) {
                    if (jamaahList.get(x).isSeleted() == true) {
                        getName = jamaahList.get(x).getName();
                    }
                }
                Toast.makeText(context, "getCheckAnggota  " + getName, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return jamaahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPhone, txtHargaAnggota;
        ImageView imgAnggota, imgDelete;
        CheckBox cbJamaah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtNamePilot);
            txtPhone = itemView.findViewById(R.id.txtPhonePilot);
            txtHargaAnggota = itemView.findViewById(R.id.txtHargaAnggota);
            imgAnggota = itemView.findViewById(R.id.imgAdapterKTP);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cbJamaah = itemView.findViewById(R.id.checkboxJamaah);
        }
    }

    public void reCreate(AnggotaJamaah anggota) {
        jamaahList.add(anggota);
        notifyDataSetChanged();

    }

    public void addItem(int pos, AnggotaJamaah modelAddAnggota) {
        jamaahList.add(pos, modelAddAnggota);
        notifyItemInserted(pos);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        jamaahList.remove(pos);
        notifyItemRangeRemoved(pos, jamaahList.size());
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public interface viewHarga {
        void onViewHarga(TextView textview);

    }

    public interface viewDetail {
        void onViewDetail(View view, int pos, AnggotaJamaah jamaah);

//        void onDelete(View view, int pos, AnggotaJamaah jamaah);
    }

    public void setviewHarga(viewHarga harga) {
        viewHarga = harga;
    }

    public void setViewDetail(viewDetail detail) {
        viewDetail = detail;
    }


    public void setDelete(viewDetail detail) {
        viewDetail = detail;
    }
}

package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPaketItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.MemberAdditionItem;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterMemberHistory extends RecyclerView.Adapter<AdapterMemberHistory.ViewHolder> {

    Context context;
    List<MemberAdditionItem> infoPaketItems;

    public AdapterMemberHistory(Context context, List<MemberAdditionItem> infoPaketItems) {
        this.context = context;
        this.infoPaketItems = infoPaketItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_anggotajamaah, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MemberAdditionItem paketitem = infoPaketItems.get(i);
        viewHolder.txtName.setText(paketitem.getNama());

        if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
            viewHolder.checkBoxMember.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return infoPaketItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPhone, txtHargaAnggota;
        ImageView imgAnggota, imgDelete;
        CheckBox checkBoxMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtNamePilot);
            txtPhone = itemView.findViewById(R.id.txtPhonePilot);
            txtHargaAnggota = itemView.findViewById(R.id.txtHargaAnggota);
            checkBoxMember = itemView.findViewById(R.id.checkboxJamaah);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}

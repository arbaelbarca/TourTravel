package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Model.ModelLIstPaket.ResultItemPaket;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterListPaketVendor extends RecyclerView.Adapter<AdapterListPaketVendor.ViewHolder> {
    Context context;
    List<ResultItemPaket> itemPaketList;
    private int lastSelectedPosition = -1;
    public static String passHarga;
    clickHarga harga;
    clickRb clickButton;
    View mView;

    public AdapterListPaketVendor(Context context, List<ResultItemPaket> itemPaketList) {
        this.context = context;
        this.itemPaketList = itemPaketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_listpaket, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ResultItemPaket itemPaket = itemPaketList.get(i);

        viewHolder.txtName.setText(itemPaket.getNamaPaket());
        viewHolder.txtNameVendor.setText(itemPaket.getHarga_paket());
        viewHolder.txtDeskripsi.setText(itemPaket.getDeskripsiPaket());
        viewHolder.radioButton.setChecked(lastSelectedPosition == i);


        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lastSelectedPosition = viewHolder.getAdapterPosition();
                notifyDataSetChanged();
                harga.harga(v, i);
//                Toast.makeText(context, "Pilih " + itemPaket.getNamaPaket(), Toast.LENGTH_SHORT).show();

            }
        });

        if (viewHolder.radioButton.isChecked()) {
            itemPaketList.get(i).setChecked(lastSelectedPosition);
            Log.d("responChecked", " yess hehe " + itemPaketList.get(i).isChecked());
            String getIschecked = String.valueOf(lastSelectedPosition);
            if (getIschecked != null) {
                clickButton.clickRadioButton(lastSelectedPosition);

            }
        }


    }

    @Override
    public int getItemCount() {
        return itemPaketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtNameVendor, txtDeskripsi;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            txtName = itemView.findViewById(R.id.txtNamaPaket);
            txtNameVendor = itemView.findViewById(R.id.txtNamaVendor);
            txtDeskripsi = itemView.findViewById(R.id.txtDeskripsiVendor);
            radioButton = itemView.findViewById(R.id.radioCheck);


        }
    }


    public interface clickHarga {
        void harga(View view, int pos);

    }

    public interface clickRb {
        void clickRadioButton(int p);
    }

    public void clickHarga(clickHarga clickHarga) {
        harga = clickHarga;
    }


    public void clickPassData(clickRb clickHarga) {
        clickButton = clickHarga;
    }
}

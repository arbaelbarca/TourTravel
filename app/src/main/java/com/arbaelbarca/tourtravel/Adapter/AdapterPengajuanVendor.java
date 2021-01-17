package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResultItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPengajuanVendor extends RecyclerView.Adapter<AdapterPengajuanVendor.ViewHolder> {
    Context context;
    List<ResultItem> resultItemList;
    ActionClick actionClick;

    public AdapterPengajuanVendor(Context context, List<ResultItem> resultItemList) {
        this.context = context;
        this.resultItemList = resultItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_pengajuan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ResultItem resultItem = resultItemList.get(i);


        if (resultItem.getKtpDirut() != null) {
            Picasso.with(context)
                    .load(resultItem.getKtpDirut())
                    .centerCrop()
                    .fit()
                    .into(viewHolder.imgKtp);

        } else {
            viewHolder.imgKtp.setBackgroundResource(R.drawable.ic_user_no_photo);
        }

        viewHolder.txtTitle.setText(resultItem.getNamaPerusahaan());
        viewHolder.txtSk.setText(resultItem.getDirekturUtama());

        if (resultItem.getStatusPengajuan().equalsIgnoreCase("Diterima")) {
            viewHolder.txtStatus.setText("Diterima");
            viewHolder.rlbgStatus.setBackgroundResource(R.drawable.bg_setujui);
            viewHolder.txtStatus.setTextColor(Color.WHITE);
            viewHolder.rlbgStatus.setVisibility(View.VISIBLE);
        } else if (resultItem.getStatusPengajuan().equalsIgnoreCase("Menunggu")) {
            viewHolder.txtStatus.setText("Menunggu");
            viewHolder.rlbgStatus.setBackgroundResource(R.drawable.bg_review);
            viewHolder.txtStatus.setTextColor(Color.WHITE);
            viewHolder.rlbgStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtStatus.setText("Ditolak");
            viewHolder.txtStatus.setTextColor(Color.WHITE);
            viewHolder.rlbgStatus.setVisibility(View.VISIBLE);
            viewHolder.rlbgStatus.setBackgroundResource(R.drawable.bg_tolak);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionClick.Click(v, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgKtp;
        TextView txtTitle, txtSk, txtStatus;
        RelativeLayout rlbgStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgKtp = itemView.findViewById(R.id.imgKtp);
            txtTitle = itemView.findViewById(R.id.txtNamaVendor);
            txtSk = itemView.findViewById(R.id.txtTglSk);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            rlbgStatus = itemView.findViewById(R.id.rlcolorpengajuan);
        }
    }


    public interface ActionClick {
        void Click(View v, int p);
    }

    public void ClickAdapter(ActionClick click) {
        actionClick = click;
    }
}

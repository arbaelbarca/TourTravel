package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Model.AdminUmroh.AdminResult;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ResponseAdmin;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ResultItemAdmin;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ResponseNearby.ResultsItem;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAdminVendor extends RecyclerView.Adapter<AdapterAdminVendor.ViewHolder> {
    Context context;
    List<ResultItemAdmin> adminList;
    ClickDetail clickDetail;

    public AdapterAdminVendor(Context context, List<ResultItemAdmin> adminList) {
        this.context = context;
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_place, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ResultItemAdmin item = adminList.get(i);
        viewHolder.txtTitle.setText(item.getNamaVendor());
        viewHolder.txtSnippet.setText(item.getAlamatVendor());
        viewHolder.txtNoVendor.setText(item.getNoTlp());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDetail.clickDetail(v, i);
            }
        });

        if (!item.getLogo_vendor().equals(""))
            Glide.with(context)
                    .load(item.getLogo_vendor())
                    .into(viewHolder.imgVendor);

    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtSnippet, txtNoVendor, txtPaket,txtHarga;
        ImageView imgVendor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSnippet = itemView.findViewById(R.id.txtSnippetPlace);
            txtTitle = itemView.findViewById(R.id.txtTitlePlace);
            imgVendor = itemView.findViewById(R.id.imgVendor);
            txtNoVendor = itemView.findViewById(R.id.txtnotlpnVendor);
            txtPaket = itemView.findViewById(R.id.txtPaket);
            txtHarga = itemView.findViewById(R.id.txtHargaPaket);
        }
    }

    public interface ClickDetail {
        void clickDetail(View v, int pos);
    }

    public void ActionClick(ClickDetail detail) {
        clickDetail = detail;
    }
}

package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResponseHistory;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterHistoryTransaksi extends RecyclerView.Adapter<AdapterHistoryTransaksi.ViewHolder> {
    Context context;
    private List<ResultItem> historyList;
    private List<InfoVendorItem> vendorItemList;
    ClickHistory click;

    public AdapterHistoryTransaksi(Context context, List<ResultItem> historyList) {
        this.context = context;
        this.historyList = historyList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ResultItem history = historyList.get(i);

        for (int a = 0; a < history.getInfoVendor().size(); a++) {
            viewHolder.txtName.setText(history.getInfoVendor().get(a).getNamaVendor());
            viewHolder.txtAlamat.setText(history.getInfoVendor().get(a).getAlamatVendor());
        }

        for (int x = 0; x < history.getInfoPengguna().size(); x++) {
            viewHolder.txtTitleAdmin.setText("Pemesan : " + history.getInfoPengguna().get(x).getNamaLengkap());
        }

        if (history.getStatusKonfirmasi().equals("confirm")) {
            viewHolder.txtStatus.setText("Diterima");
            viewHolder.rlHistory.setBackgroundResource(R.drawable.bg_status_history);
        } else if (history.getStatusKonfirmasi().equals("no confirm")) {
            viewHolder.txtStatus.setText("Ditolak");
            viewHolder.rlHistory.setBackgroundResource(R.drawable.bg_status_ditolak);
        } else {
            viewHolder.txtStatus.setText("Direview");
            viewHolder.txtStatus.setTextColor(Color.BLACK);
            viewHolder.rlHistory.setBackgroundResource(R.drawable.bg_status_direview);
        }

        if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
            viewHolder.txtTitleAdmin.setVisibility(View.VISIBLE);

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.OnClick(v, i);
            }
        });

        viewHolder.txtTotal.setText("Rp " + String.valueOf(history.getTotal()));

        viewHolder.txtTanggalOrder.setText(history.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAlamat, txtStatus, txtTitleAdmin, txtTotal,txtTanggalOrder;
        RelativeLayout rlHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtTitleVendor);
            txtAlamat = itemView.findViewById(R.id.txtAlamatVendor);
            txtStatus = itemView.findViewById(R.id.txtStatusHistory);
            txtTotal = itemView.findViewById(R.id.txtTotalHistory);
            rlHistory = itemView.findViewById(R.id.rlStatusHistory);
            txtTitleAdmin = itemView.findViewById(R.id.txttileAdmin);
            txtTanggalOrder = itemView.findViewById(R.id.txtTanggalOrder);

        }

    }

    public interface ClickHistory {
        void OnClick(View v, int pos);
    }

    public void OnActionClick(ClickHistory clickHistory) {
        click = clickHistory;
    }
}

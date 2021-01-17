package com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Adapter.AdapterMemberHistory;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Fragment.FragmentHistory;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPaketItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPenggunaItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.memberAdditionItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.resultItemList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishingPelunasan extends Fragment {

    TextView txtNamaPaket, txtHargaPaket, txtDeskripsiPaket, txtNoAnggota;


    TextView txtName, txtPhone, txtAlamat, txtEmail, txtInvoice;

    RecyclerView rvListAnggota;


    AdapterMemberHistory adapterAddJamaah;
    ImageView imgStrukDp, imgGetStruk, imgInvoice;

    ArrayList<? extends InfoVendorItem> infoVendorItems = new ArrayList<>();
    InputStream imageStream;
    String logBase64;
    TextView txtKetUpload, txtStatus, txtDetailAdminTitle;
    RelativeLayout rlSimpan, rlTolak, rlSetujui, rlTablayout;
    ProgressDialog progressDialog;

    LinearLayout llBtnAdmin;
    FloatingActionButton createInvoice;
    LinearLayout llBiaya1, llBiaya2, llBiaya3;
    TextView txtJudul1, txtJudul2, txtJudul3, txtBiaya1, txtBiaya2, txtBiaya3;
    ExpandableRelativeLayout expandPaket;
    ExpandableLinearLayout expandAnggota;
    RelativeLayout rlExpandPaket, rlExpandAnggota;
    ImageView imgArrowPaket, imgArrowAnggota;

    public FinishingPelunasan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finishing_pelunasan, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        progressDialog = new ProgressDialog(getActivity());
        imgStrukDp = view.findViewById(R.id.imgStrukHistory);
        txtName = view.findViewById(R.id.txtNamaVendor);
        txtPhone = view.findViewById(R.id.txtPhoneVendor);
        txtAlamat = view.findViewById(R.id.txtEmail);
        txtEmail = view.findViewById(R.id.txtAlamatVendor);
        rvListAnggota = view.findViewById(R.id.rvList);
        txtKetUpload = view.findViewById(R.id.txtKetUploadStruk);
        txtStatus = view.findViewById(R.id.txtStatusHistory);
        rlSimpan = view.findViewById(R.id.rlSimpanStruk);
        imgGetStruk = view.findViewById(R.id.imgStruk);
        txtDetailAdminTitle = view.findViewById(R.id.txtTitleDetailAdmin);
        txtInvoice = view.findViewById(R.id.txtInvoice);
        llBiaya1 = view.findViewById(R.id.llBiaya1);
        llBiaya2 = view.findViewById(R.id.llBiaya2);
        llBiaya3 = view.findViewById(R.id.llBiaya3);
        txtJudul1 = view.findViewById(R.id.judulBiaya1);
        txtJudul2 = view.findViewById(R.id.judulBiaya2);
        txtJudul3 = view.findViewById(R.id.judulBiaya3);
        txtBiaya1 = view.findViewById(R.id.txtBiaya1);
        txtBiaya2 = view.findViewById(R.id.txtBiaya2);
        txtBiaya3 = view.findViewById(R.id.txtBiaya3);
        expandPaket = view.findViewById(R.id.expandablePaket);
        expandAnggota = view.findViewById(R.id.expandAnggota);
        rlExpandPaket = view.findViewById(R.id.rlExpandPaket);
        rlExpandAnggota = view.findViewById(R.id.rlExpandAnggota);
        imgArrowPaket = view.findViewById(R.id.imgArrowPaket);
        imgArrowAnggota = view.findViewById(R.id.imgArrowAnggota);
        imgInvoice = view.findViewById(R.id.imgPelunasan);


        getData(view);
    }

    private void getData(View view) {
        txtName.setText(resultItemList.get(0).getNamaVendor());
        txtPhone.setText(resultItemList.get(0).getNoTlp());
        txtAlamat.setText(resultItemList.get(0).getAlamatVendor());
        txtEmail.setText(resultItemList.get(0).getEmail());

        txtNamaPaket = view.findViewById(R.id.txtNamaPaket);
        txtHargaPaket = view.findViewById(R.id.txtPricePaket);
        txtDeskripsiPaket = view.findViewById(R.id.txtDeskripsiPaket);

        txtNamaPaket.setText(infoPaketItems.get(0).getNamaPaket());
        txtHargaPaket.setText("Rp " + infoPaketItems.get(0).getHargaPaket());
        txtDeskripsiPaket.setText(infoPaketItems.get(0).getDeskripsiPaket());
        txtDetailAdminTitle.setText("Pemesan : " + infoPenggunaItems.get(0).getNamaLengkap());


        conditional();

        if (memberAdditionItems.size() > 0) {
            adapterAddJamaah = new AdapterMemberHistory(getActivity(), memberAdditionItems);
            rvListAnggota.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvListAnggota.setHasFixedSize(true);
            rvListAnggota.setVisibility(View.VISIBLE);
            rvListAnggota.setAdapter(adapterAddJamaah);
        } else {
        }

    }

    private void conditional() {
        if (!FragmentHistory.item.getJudul_pajak_a().equals("")) {
            llBiaya1.setVisibility(View.VISIBLE);
            txtJudul1.setText(FragmentHistory.item.getJudul_pajak_a());
            txtBiaya1.setText(FragmentHistory.item.getPajak_a());
        } else if (!FragmentHistory.item.getJudul_pajak_b().equals("")) {
            llBiaya2.setVisibility(View.VISIBLE);
            txtJudul2.setText(FragmentHistory.item.getJudul_pajak_b());
            txtBiaya2.setText(FragmentHistory.item.getPajak_b());
        } else if (!FragmentHistory.item.getJudul_pajak_c().equals("")) {
            llBiaya3.setVisibility(View.VISIBLE);
            txtJudul3.setText(FragmentHistory.item.getJudul_pajak_c());
            txtBiaya3.setText(FragmentHistory.item.getPajak_c());
        }

        if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
            txtDetailAdminTitle.setVisibility(View.VISIBLE);

        }

        if (!FragmentHistory.item.getNoInvoice().equals("")) {
            txtInvoice.setText(FragmentHistory.item.getNoInvoice());
        }

        if (FragmentHistory.item.getStatusKonfirmasi().equals("confirm")) {
            txtStatus.setText("Diterima");
        } else if (FragmentHistory.item.getStatusKonfirmasi().equals("no confirm")) {
            txtStatus.setText("Ditolak");
        } else {
            txtStatus.setText("Direview");
        }


        if (FragmentHistory.item.getPhotoBukti().equals("") && FragmentHistory.item.getPhoto_invoice().equals("")) {
            txtKetUpload.setVisibility(View.VISIBLE);
            rlSimpan.setVisibility(View.VISIBLE);

        } else {
            imgGetStruk.setVisibility(View.VISIBLE);
            imgInvoice.setVisibility(View.VISIBLE);

            Picasso.with(getActivity())
                    .load(FragmentHistory.item.getPhotoBukti())
                    .into(imgGetStruk);

            if (!FragmentHistory.item.getPhoto_invoice().equals("")) {
                Picasso.with(getActivity())
                        .load(FragmentHistory.item.getPhoto_invoice())
                        .into(imgInvoice);
            }

        }
    }

}

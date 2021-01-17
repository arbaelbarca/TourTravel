package com.arbaelbarca.tourtravel.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Adapter.AdapterMemberHistory;
import com.arbaelbarca.tourtravel.Adapter.AdapterTambahPajak;
import com.arbaelbarca.tourtravel.Fragment.FragmentHistory;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.PajakBiaya;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPaketItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPenggunaItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.memberAdditionItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.resultItemList;

public class CreateInvoice extends AppCompatActivity {
    TextView txtNamaPaket, txtHargaPaket, txtDeskripsiPaket, txtNoAnggota;


    TextView txtName, txtPhone, txtAlamat, txtEmail;

    RecyclerView rvListAnggota;


    AdapterMemberHistory adapterAddJamaah;

    ImageView imgStruk, imgGetStruk;


    TextView txtKetUpload, txtStatus, txtDetailAdminTitle;
    RecyclerView rvPajak;
    ProgressDialog progressDialog;
    LinearLayout lladdBiaya;
    BottomSheetDialog sheetDialog;

    List<PajakBiaya> biayaList = new ArrayList<>();
    AdapterTambahPajak adapterTambahPajak;
    RelativeLayout rlCetak;
    String idHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        initial();

    }

    private void initial() {
        adapterTambahPajak = new AdapterTambahPajak(this, biayaList);
        progressDialog = new ProgressDialog(this);
        sheetDialog = new BottomSheetDialog(this);
        imgStruk = findViewById(R.id.imgStrukHistory);
        txtNoAnggota = findViewById(R.id.txtNoAnggota);
        txtName = findViewById(R.id.txtNamaVendor);
        txtPhone = findViewById(R.id.txtPhoneVendor);
        txtAlamat = findViewById(R.id.txtEmail);
        txtEmail = findViewById(R.id.txtAlamatVendor);
        rvListAnggota = findViewById(R.id.rvList);
        txtKetUpload = findViewById(R.id.txtKetUploadStruk);
        txtStatus = findViewById(R.id.txtStatusHistory);
        lladdBiaya = findViewById(R.id.lladdBiaya);
        imgGetStruk = findViewById(R.id.imgStruk);
        rlCetak = findViewById(R.id.rl_bottom);
        txtDetailAdminTitle = findViewById(R.id.txtTitleDetailAdmin);
        rvPajak = findViewById(R.id.rvCreatePajak);

        txtName.setText(resultItemList.get(0).getNamaVendor());
        txtPhone.setText(resultItemList.get(0).getNoTlp());
        txtAlamat.setText(resultItemList.get(0).getAlamatVendor());
        txtEmail.setText(resultItemList.get(0).getEmail());

        txtNamaPaket = findViewById(R.id.txtNamaPaket);
        txtHargaPaket = findViewById(R.id.txtPricePaket);
        txtDeskripsiPaket = findViewById(R.id.txtDeskripsiPaket);

        txtNamaPaket.setText(infoPaketItems.get(0).getNamaPaket());
        txtHargaPaket.setText("Rp " + infoPaketItems.get(0).getHargaPaket());
        txtDeskripsiPaket.setText(infoPaketItems.get(0).getDeskripsiPaket());
        txtDetailAdminTitle.setText("Pemesan : " + infoPenggunaItems.get(0).getNamaLengkap());

        if (FragmentHistory.item.getStatusKonfirmasi().equals("confirm")) {
            txtStatus.setText("Diterima");
        } else if (FragmentHistory.item.getStatusKonfirmasi().equals("no confirm")) {
            txtStatus.setText("Ditolak");
        } else {
            txtStatus.setText("Direview");
        }

        if (memberAdditionItems.size() > 0) {
            adapterAddJamaah = new AdapterMemberHistory(this, memberAdditionItems);
            rvListAnggota.setLayoutManager(new LinearLayoutManager(this));
            rvListAnggota.setHasFixedSize(true);
            rvListAnggota.setVisibility(View.VISIBLE);
            txtNoAnggota.setVisibility(View.GONE);
            rvListAnggota.setAdapter(adapterAddJamaah);
        } else {
            txtNoAnggota.setVisibility(View.VISIBLE);
        }

        if (FragmentHistory.item.getPhotoBukti().equals("")) {
            imgStruk.setVisibility(View.VISIBLE);
        } else {
            imgGetStruk.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(FragmentHistory.item.getPhotoBukti())
                    .into(imgGetStruk);
        }

        lladdBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBiayaPajak();
            }
        });

        idHistory = FragmentHistory.item.getId();

        rlCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (biayaList.size() > 0) {
                    saveCetak();
                } else {
                    Toast.makeText(getApplicationContext(), "Data biaya tidak boleh kosong", Toast.LENGTH_LONG).show();

                }
            }
        });

        Log.d("responBiayaList", " " + idHistory + "   " + new Gson().toJson(biayaList));


    }

    private void saveCetak() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String getDate = simpleDateFormat.format(calendar.getTime());

        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }

        String random_string = sb1.toString();

        String noInvoice = getDate + "/INV" + random_string;
        Log.d("responGenerate", " " + random_string);


        progressDialog.show();
        progressDialog.setMessage("Loading");
        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("json_pajak", new Gson().toJson(biayaList));
        hashMap.put("id", idHistory);
        hashMap.put("no_invoice", noInvoice);

        Call<ResponseBody> call = apiService.postUpdateHistory(hashMap);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(CreateInvoice.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getMessage();
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });


    }

    private void addBiayaPajak() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layoutbottom_add_biaya);
        final EditText txtAddBiaya = dialog.findViewById(R.id.txtAddBiaya);
        final EditText txtJudulBiaya = dialog.findViewById(R.id.txtJudulBiaya);

        RelativeLayout btnSimpan = dialog.findViewById(R.id.rl_bottom);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textBiaya = txtAddBiaya.getText().toString().trim();
                String textJudul = txtJudulBiaya.getText().toString().trim();

                if (textBiaya.isEmpty() || textJudul.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Form tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                    saveBiaya(textBiaya, textJudul);
                }
            }
        });

        dialog.show();
    }

    private void saveBiaya(String biaya, String judulBiaya) {
        adapterTambahPajak.reCreate(0, new PajakBiaya(biaya, judulBiaya));
        adapterTambahPajak.notifyDataSetChanged();

        rvPajak.setVisibility(View.VISIBLE);
        rvPajak.setLayoutManager(new LinearLayoutManager(this));
        rvPajak.setHasFixedSize(true);
        rvPajak.setAdapter(adapterTambahPajak);


    }
}

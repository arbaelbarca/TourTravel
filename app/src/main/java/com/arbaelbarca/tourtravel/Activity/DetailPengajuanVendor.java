package com.arbaelbarca.tourtravel.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResponseListPengajuan;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResultItem;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailPengajuanVendor extends AppCompatActivity {

    ImageView imgKtp;
    TextView txtNamaPengajua, txtNamaperusahaan, txtDirektur, txtNosk, txtTglsk, txtAlamat, txtNorek;

    String getNamap, getId, getNamPerusahaan, getDirektur, getNosk, getTglsk, getNorek, getAlamat, getImageKtp;
    RelativeLayout rlSetujui, rlTolak;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengajuan_vendor);

        init();

    }

    private void init() {
        getIntentInit();

        dialog = new ProgressDialog(this);

        imgKtp = findViewById(R.id.imgDetail);
        txtNamaPengajua = findViewById(R.id.txtNamaPengajuan);
        txtNamaperusahaan = findViewById(R.id.txtNamaVendor);
        txtDirektur = findViewById(R.id.txtDirekturUtama);
        txtNosk = findViewById(R.id.txtNoSk);
        txtTglsk = findViewById(R.id.txtTglSk);
        txtNorek = findViewById(R.id.txtNorek);
        txtAlamat = findViewById(R.id.txtAlamatVendor);
        rlSetujui = findViewById(R.id.rlSetujui);
        rlTolak = findViewById(R.id.rlDelete);

        txtNamaperusahaan.setText(getNamPerusahaan);
        txtNamaPengajua.setText(getNamap);
        txtDirektur.setText(getDirektur);
        txtNosk.setText(getNosk);
        txtTglsk.setText(getTglsk);
        txtAlamat.setText(getAlamat);
        txtNorek.setText(getNorek);

        Picasso.with(this)
                .load(getImageKtp)
                .into(imgKtp);

        rlSetujui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setujuiVendor(getId);
            }
        });

        rlTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tolakVendor(getId);
            }
        });
    }

    private void setujuiVendor(String idvendor) {

        dialog.setMessage("Loading");
        dialog.show();
        Retrofit retrofit = ServiceApiClient.postPengajuan(this);
        ApiService apiService = retrofit.create(ApiService.class);
        HashMap<String, String> listPengajuan = new HashMap<>();
        listPengajuan.put("id", idvendor);
        listPengajuan.put("status_pengajuan", "Diterima");


        Call<ResponseBody> call = apiService.postUpdateSetujui(listPengajuan);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("responSetujui", " yess " + response.code() + " array " + response.body());
                    dialog.dismiss();
                    finish();
                } else {
                    Log.d("responSetujui", " else " + response.code());
                    dialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("responSetujui", " onfilure " + t.getMessage());
                dialog.dismiss();
                finish();
            }
        });

    }

    private void tolakVendor(String idvendor) {

        dialog.setMessage("Loading");
        dialog.show();
        Retrofit retrofit = ServiceApiClient.postPengajuan(this);
        ApiService apiService = retrofit.create(ApiService.class);
        HashMap<String, String> listPengajuan = new HashMap<>();
        listPengajuan.put("id", idvendor);
        listPengajuan.put("status_pengajuan", "Ditolak");

        Call<ResponseBody> call = apiService.postUpdateSetujui(listPengajuan);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("responSetujui", " yess " + response.code());
                    dialog.dismiss();
                    finish();

                } else {
                    Log.d("responSetujui", " else " + response.code());
                    dialog.dismiss();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("responSetujui", " onfilure " + t.getMessage());
                dialog.dismiss();
                finish();

            }
        });

    }

    private void getIntentInit() {
        Intent intent = getIntent();
        getNamap = intent.getStringExtra("nama_pengajuan");
        getId = intent.getStringExtra("id");
        getNamPerusahaan = intent.getStringExtra("nama_perusahan");
        getDirektur = intent.getStringExtra("direktur");
        getNosk = intent.getStringExtra("no_sk");
        getTglsk = intent.getStringExtra("tgl_sk");
        getAlamat = intent.getStringExtra("alamat");
        getImageKtp = intent.getStringExtra("ktp");
        getNorek = intent.getStringExtra("norek");

        Log.d("responKtp", " k " + getImageKtp);

    }
}

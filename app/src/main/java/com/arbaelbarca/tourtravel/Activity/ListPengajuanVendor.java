package com.arbaelbarca.tourtravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.arbaelbarca.tourtravel.Adapter.AdapterPengajuanVendor;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResponseListPengajuan;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResultItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListPengajuanVendor extends AppCompatActivity {
    AdapterPengajuanVendor adapterPengajuanVendor;
    RecyclerView rvlist;
    List<ResultItem> list = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengajuan_vendor);

        init();
    }

    private void init() {
        rvlist = findViewById(R.id.rvListPengajuan);
        toolbar = findViewById(R.id.toolbar);


        getDataList();
    }

    void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Vendor");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void getDataList() {

        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseListPengajuan> call = apiService.getListPengajuan();

        call.enqueue(new Callback<ResponseListPengajuan>() {
            @Override
            public void onResponse(Call<ResponseListPengajuan> call, Response<ResponseListPengajuan> response) {
                if (response.isSuccessful()) {
                    Log.d("responSuccesList", "l " + response.code());

                    list = response.body().getResult();
                    adapterPengajuanVendor = new AdapterPengajuanVendor(ListPengajuanVendor.this, list);
                    rvlist.setLayoutManager(new LinearLayoutManager(ListPengajuanVendor.this));
                    rvlist.setHasFixedSize(true);
                    rvlist.setAdapter(adapterPengajuanVendor);

                    adapterPengajuanVendor.ClickAdapter(new AdapterPengajuanVendor.ActionClick() {
                        @Override
                        public void Click(View v, int p) {
                            ResultItem item = list.get(p);

                            Intent i = new Intent(ListPengajuanVendor.this, DetailPengajuanVendor.class);
                            i.putExtra("nama_perusahan", item.getNamaPerusahaan());
                            i.putExtra("nama_pengajuan", item.getNamaLengkap());
                            i.putExtra("direktur", item.getDirekturUtama());
                            i.putExtra("no_sk", item.getNoSk());
                            i.putExtra("tgl_sk", item.getTanggalSk());
                            i.putExtra("alamat", item.getAlamat());
                            i.putExtra("ktp", item.getKtpDirut());
                            i.putExtra("norek", item.getRekeningUang());
                            i.putExtra("id", item.getId());

                            startActivity(i);
                        }
                    });

                } else {
                    Log.d("responSuccesList", "else " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseListPengajuan> call, Throwable t) {
                Log.d("responSuccesList", "l " + t
                        .getMessage());
            }
        });


    }
}

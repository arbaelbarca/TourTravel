package com.arbaelbarca.tourtravel.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Adapter.AdapterAddJamaah;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.AnggotaJamaah;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.Model.ModelLIstPaket.ResultItemPaket;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.arbaelbarca.tourtravel.Activity.DetailUmroh.jamaahList;
import static com.arbaelbarca.tourtravel.Activity.Umroh.itemAdmin;

public class OrderUmroh extends AppCompatActivity {
    TextView txtName, txtPhone, txtAlamat, txtEmail;
    TextView txtAddName, txtAddPhone, txtHargaTotal, txtketAnggota;
    Toolbar toolbar;
    List<ResultItemPaket> itemPaketList;
    TextView txtNamaPaket, txtHargaPaket, txtDeskripsiPaket;
    RecyclerView rvListAnggota;
    AdapterAddJamaah adapterAddJamaah;
    RelativeLayout rlPesan;
    BottomSheetDialog bottomSheetDialog;
    int totalKeseluruhan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_umroh);

        init();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void init() {
        itemPaketList = new ArrayList<>();
        Bundle args = getIntent().getBundleExtra("passBundel");
        itemPaketList = (List<ResultItemPaket>) args.getSerializable("passData");
        Log.d("responGetArray", " yess " + new Gson().toJson(jamaahList));
        final int getIsChecked = getIntent().getIntExtra("passDatarb", 0);
        String getStringChecked = String.valueOf(getIsChecked);


        txtName = findViewById(R.id.txtNamaVendor);
        toolbar = findViewById(R.id.toolbar);
        txtPhone = findViewById(R.id.txtPhoneVendor);
        txtAlamat = findViewById(R.id.txtEmail);
        txtEmail = findViewById(R.id.txtAlamatVendor);
        rvListAnggota = findViewById(R.id.rvList);
        rlPesan = findViewById(R.id.rl_bottom);
        txtHargaTotal = findViewById(R.id.txtTotalKeseluruhan);
        txtketAnggota = findViewById(R.id.txtketAnggota);

        txtNamaPaket = findViewById(R.id.txtNamaPaket);
        txtHargaPaket = findViewById(R.id.txtPricePaket);
        txtDeskripsiPaket = findViewById(R.id.txtDeskripsiPaket);


        txtName.setText(itemAdmin.getNamaVendor());
        txtPhone.setText(itemAdmin.getNoTlp());
        txtAlamat.setText(itemAdmin.getAlamatVendor());
        txtEmail.setText(itemAdmin.getEmail());

        if (getStringChecked != null) {
            txtNamaPaket.setText(itemPaketList.get(getIsChecked).getNamaPaket());
            txtHargaPaket.setText(itemPaketList.get(getIsChecked).getHarga_paket());
            txtDeskripsiPaket.setText(itemPaketList.get(getIsChecked).getDeskripsiPaket());
        }

        initRvAnggota(getIsChecked);
        rlPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderData(getIsChecked);
            }
        });


    }

    private void sendOrderData(int isChecked) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);

        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, Integer> map = new HashMap<>();
        hashMap.put("id_pengguna", MainActivity.itemProfiles.getResult().getData().getId());
        hashMap.put("id_vendor", itemAdmin.getId());
        hashMap.put("id_paket", itemPaketList.get(isChecked).getId());
        hashMap.put("total", String.valueOf(totalKeseluruhan));
        hashMap.put("json_member", new Gson().toJson(jamaahList));

        Call<ResponseBody> call = apiService.postOrderCheckout(hashMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String getObject = object.getString("result");
                        Log.d("responSucces", "succes Order " + getObject);
                        dialog.dismiss();
                        succesSend();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialog.dismiss();
                    Log.d("responSucces", "else" + response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("responSucces", "succes onfailure " + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    private void succesSend() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_waiting_order);
        bottomSheetDialog.setCancelable(false);
        RelativeLayout rlOk = bottomSheetDialog.findViewById(R.id.rl_bottom);
        rlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        bottomSheetDialog.show();

    }

    private void initRvAnggota(int isCheck) {

        adapterAddJamaah = new AdapterAddJamaah(this, jamaahList);
        rvListAnggota.setLayoutManager(new LinearLayoutManager(this));
        rvListAnggota.setHasFixedSize(true);
        rvListAnggota.setAdapter(adapterAddJamaah);

        int hargaPaket = Integer.parseInt(itemPaketList.get(isCheck).getHarga_paket());
        int hargaAnggota = 0;
        for (AnggotaJamaah jamaah : jamaahList) {
            hargaAnggota += Integer.parseInt(jamaah.getHargaAnggota());
            Log.d("responHarga A ", " " + hargaAnggota);
        }

        final int finalHargaAnggota = hargaAnggota;
        adapterAddJamaah.setviewHarga(new AdapterAddJamaah.viewHarga() {
            @Override
            public void onViewHarga(TextView textview) {
                if (finalHargaAnggota != 0) {
                    textview.setVisibility(View.VISIBLE);
                }
            }
        });

        totalKeseluruhan = hargaPaket + hargaAnggota;
        Log.d("responKeseuluran", " " + totalKeseluruhan);
        txtHargaTotal.setText("Rp " + String.valueOf(totalKeseluruhan));
        txtketAnggota.setVisibility(View.VISIBLE);
        txtketAnggota.setText("Masing - masing anggota dengan DP Rp " + hargaAnggota);

    }
}

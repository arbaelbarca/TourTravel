package com.arbaelbarca.tourtravel.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity;
import com.arbaelbarca.tourtravel.Adapter.AdapterAddJamaah;
import com.arbaelbarca.tourtravel.Adapter.AdapterListPaketVendor;
import com.arbaelbarca.tourtravel.Model.AnggotaJamaah;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.Model.ModelLIstPaket.ResponseListPaket;
import com.arbaelbarca.tourtravel.Model.ModelLIstPaket.ResultItemPaket;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResponseListPengajuan;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.arbaelbarca.tourtravel.Activity.Umroh.itemAdmin;

public class DetailUmroh extends AppCompatActivity {

    TextView txtName, txtPhone, txtAlamat, txtEmail;
    TextView txtAddName, txtAddPhone;
    String getName, getPhone, getAlamat, getEmail;
    LinearLayout llAddJamaah;
    BottomSheetDialog bottomSheetDialog;
    AdapterAddJamaah adapterAddJamaah;
    public static ArrayList<AnggotaJamaah> jamaahList;
    RecyclerView rvListJamaah, rvListPaket;
    RelativeLayout rlSaveJamaah, rlCheckout;
    AnggotaJamaah anggotaJamaah;
    AdapterListPaketVendor adapterListPaketVendor;
    List<ResultItemPaket> itemPaketList = new ArrayList<>();
    ProgressBar progressPaket;
    Toolbar toolbar;
    TextView txtHargaTotal, txtTidakada;
    RelativeLayout rlUpload;
    InputStream imageStream;
    String logBase64;
    ImageView imgKtp;
    Uri imageCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_umroh);

        initial();

        initToolbar();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initial() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.item_layout_addanggotajamaah);
        txtAddName = bottomSheetDialog.findViewById(R.id.txtNamaLengkap);
        txtAddPhone = bottomSheetDialog.findViewById(R.id.ttPhoneAnggota);
        rlSaveJamaah = bottomSheetDialog.findViewById(R.id.rl_bottom);
        rlUpload = bottomSheetDialog.findViewById(R.id.rlUploadKTPAnggota);
        imgKtp = bottomSheetDialog.findViewById(R.id.imgKTPUpload);

        txtName = findViewById(R.id.txtNamaVendor);
        toolbar = findViewById(R.id.toolbar);
        txtHargaTotal = findViewById(R.id.txtHargaTotal);
        llAddJamaah = findViewById(R.id.llAddMitra);
        txtPhone = findViewById(R.id.txtPhoneVendor);
        txtAlamat = findViewById(R.id.txtEmail);
        txtEmail = findViewById(R.id.txtAlamatVendor);
        rvListJamaah = findViewById(R.id.rvList);
        rvListPaket = findViewById(R.id.rvListPaket);
        progressPaket = findViewById(R.id.progressPaket);
        rlCheckout = findViewById(R.id.rl_checkout);
        txtTidakada = findViewById(R.id.txtTidakada);

        txtName.setText(itemAdmin.getNamaVendor());
        txtPhone.setText(itemAdmin.getNoTlp());
        txtAlamat.setText(itemAdmin.getAlamatVendor());
        txtEmail.setText(itemAdmin.getEmail());

        initRvAnggota();
        getListPaket();

        llAddJamaah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        rlSaveJamaah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = txtAddName.getText().toString().trim();
                String getPhone = txtAddPhone.getText().toString().trim();

                if (getName.isEmpty() || getPhone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data dan image tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    bottomSheetDialog.dismiss();
                    addJamaah(getName, getPhone, String.valueOf(imageCamera), logBase64);
                    txtAddName.setText("");
                    txtAddPhone.setText("");
                }

            }
        });

        rlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCamera(DetailUmroh.this, 0);
            }
        });


    }

    private void getListPaket() {
        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id_vendor", itemAdmin.getId());
        Call<ResponseListPaket> call = apiService.getListPaket(hashMap);

        call.enqueue(new Callback<ResponseListPaket>() {
            @Override
            public void onResponse(Call<ResponseListPaket> call, Response<ResponseListPaket> response) {
                if (response.isSuccessful()) {
                    itemPaketList = response.body().getResult();
                    adapterListPaketVendor = new AdapterListPaketVendor(DetailUmroh.this, itemPaketList);
                    rvListPaket.setLayoutManager(new LinearLayoutManager(DetailUmroh.this));
                    rvListPaket.setHasFixedSize(true);
                    rvListPaket.setAdapter(adapterListPaketVendor);
                    progressPaket.setVisibility(View.GONE);
                    rvListPaket.setVisibility(View.VISIBLE);


                    adapterListPaketVendor.clickHarga(new AdapterListPaketVendor.clickHarga() {
                        @Override
                        public void harga(View view, final int pos) {
                            txtHargaTotal.setText(itemPaketList.get(pos).getHarga_paket());


                        }
                    });

                    adapterListPaketVendor.clickPassData(new AdapterListPaketVendor.clickRb() {
                        @Override
                        public void clickRadioButton(final int p) {
                            rlCheckout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    passDataRB(p);
                                }
                            });
                        }
                    });


                } else {
                    progressPaket.setVisibility(View.GONE);
                    Log.d("responPaket", " else ");
                }
            }

            @Override
            public void onFailure(Call<ResponseListPaket> call, Throwable t) {
                progressPaket.setVisibility(View.GONE);
                Log.d("responPaket", " else " + t.getMessage());
            }
        });
    }

    private void passDataRB(int pos) {
        Intent intent = new Intent(DetailUmroh.this, OrderUmroh.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("passData", (Serializable) itemPaketList);
        intent.putExtra("passBundel", bundle);
        intent.putExtra("passDatarb", itemPaketList.get(pos).isChecked());
        startActivity(intent);
    }

    private void initRvAnggota() {
        jamaahList = new ArrayList<>();


    }

    private void addJamaah(String name, String phone, String image, String logBase64) {
        adapterAddJamaah = new AdapterAddJamaah(this, jamaahList);
        adapterAddJamaah.addItem(0, new AnggotaJamaah(name, phone, logBase64, "100", image, false));
        adapterAddJamaah.notifyDataSetChanged();
        rvListJamaah.setVisibility(View.VISIBLE);
        txtTidakada.setVisibility(View.GONE);

        rvListJamaah.setLayoutManager(new LinearLayoutManager(this));
        rvListJamaah.setHasFixedSize(true);
        rvListJamaah.setVisibility(View.VISIBLE);
        rvListJamaah.setAdapter(adapterAddJamaah);

        adapterAddJamaah.setViewDetail(new AdapterAddJamaah.viewDetail() {
            @Override
            public void onViewDetail(View view, int pos, AnggotaJamaah jamaah) {
                bottomSheetDialog.show();
                txtAddName.setText(jamaah.getName());
                txtAddPhone.setText(jamaah.getNohp());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File imageFile = imageFiles.get(0);

                try {
                    File compressedImageFile = new Compressor(DetailUmroh.this).compressToFile(imageFile);
                    if (compressedImageFile != null) {
                        File ba1 = compressedImageFile;
//                        photoPath = compressedImageFile.getAbsolutePath();
                        imageCamera = Uri.fromFile(ba1);
                        Log.d("responUri", " " + imageCamera);
                        try {
                            imageStream = getContentResolver().openInputStream(imageCamera);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            Picasso.with(DetailUmroh.this).load(ba1).into(imgKtp);
                            logBase64 = encodeImage(selectedImage);
                            Log.d("responBase64", " " + logBase64);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }
}

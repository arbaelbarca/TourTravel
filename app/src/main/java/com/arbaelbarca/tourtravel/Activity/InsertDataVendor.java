package com.arbaelbarca.tourtravel.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.Profile.EditProfile;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.google.protobuf.Api;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InsertDataVendor extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_paket)
    CircleImageView imgPaket;
    @BindView(R.id.et_name_paket)
    EditText etNamePaket;
    @BindView(R.id.et_harga_paket)
    EditText etHargaPaket;
    @BindView(R.id.et_deskripsi_paket)
    EditText etDeskripsiPaket;
    @BindView(R.id.ll_save)
    RelativeLayout llSave;

    String logBase64;
    InputStream imageStream;
    BottomSheetDialog bottomSheetDialog;
    TextView txtKetSucces;
    RelativeLayout rlSave;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_vendor);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_waiting_order);
        txtKetSucces = bottomSheetDialog.findViewById(R.id.txtKetSucces);
        rlSave = bottomSheetDialog.findViewById(R.id.rl_bottom);

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNama = etNamePaket.getText().toString().trim();
                String txtDeskripsi = etDeskripsiPaket.getText().toString().trim();
                String txtHarga = etHargaPaket.getText().toString().trim();
                if (txtNama.isEmpty() || txtDeskripsi.isEmpty() || txtHarga.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_LONG).show();
                } else {
                    postPaket("14", txtNama, txtDeskripsi, txtHarga, logBase64);
                }

            }
        });

        imgPaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(InsertDataVendor.this, 0);
            }
        });

    }

    private void postPaket(String idVendor, String namePaket, String deskPaket, String hargaPaket, String photoPaket) {
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id_vendor", idVendor);
        hashMap.put("nama_paket", namePaket);
        hashMap.put("deskripsi_paket", deskPaket);
        hashMap.put("harga_paket", hargaPaket);
        hashMap.put("photo_paket", photoPaket);
        Call<ResponseBody> call = apiService.PostPaketVendor(hashMap);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                succesUpload();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void succesUpload() {
        progressDialog.dismiss();
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);
        txtKetSucces.setText("Data sudah masuk, silahkan cek di list data paket");

        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

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
                if (imageFile != null) {
                    String photoPath = imageFile.getAbsolutePath();
                    Uri imageCamera = Uri.fromFile(imageFile);
                    try {
                        imageStream = getContentResolver().openInputStream(imageCamera);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        imgPaket.setImageBitmap(selectedImage);
                        logBase64 = encodeImage(selectedImage);
                        Log.d("responBase64", " " + logBase64);
                        Picasso.with(InsertDataVendor.this).load(imageFile).into(imgPaket);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imagByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagByte, Base64.DEFAULT);
    }
}

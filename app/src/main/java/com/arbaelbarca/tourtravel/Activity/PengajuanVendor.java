package com.arbaelbarca.tourtravel.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.Profile.EditProfile;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PengajuanVendor extends AppCompatActivity {
    EditText etNama, etNosk, etTglSk, etDirektur, etAlamat, etNoRek;
    private int mYear, mMonth, mDay, mHour, mMinute;
    LinearLayout llKtp, llGetKtp;
    RelativeLayout llKirim;
    IntentIntegrator intentIntegrator;
    private int GALLERY = 1, CAMERA = 111;
    int PICK_IMAGE_REQUEST = 111;

    int IMAGE_REQUST = 2;
    Uri imageUri, imageCamera;
    Bitmap thumbnail;
    InputStream imageStream;
    String logBase64;
    ImageView imgKtp;
    ProgressDialog dialog;
    String getnameProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_vendor);

        init();
    }

    private void init() {

        getnameProfile = getIntent().getStringExtra("name");

        dialog = new ProgressDialog(this);
        etNama = findViewById(R.id.et_namavendor);
        etNosk = findViewById(R.id.et_nosk);
        etTglSk = findViewById(R.id.et_tglsk);
        etDirektur = findViewById(R.id.et_direktur);
        etAlamat = findViewById(R.id.et_alamatvendor);
        etNoRek = findViewById(R.id.et_norek);
        llKtp = findViewById(R.id.llUploadKtp);
        llKirim = findViewById(R.id.ll_save);
        llGetKtp = findViewById(R.id.llKtp);
        imgKtp = findViewById(R.id.imgKtp);

        llKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadKtp();
            }
        });

        etTglSk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataPicker();
            }
        });

        llKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPengajuan();
            }
        });

    }


    private void uploadKtp() {
        final CharSequence sequence[] = new CharSequence[]{"Camera", "Galery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Pilih Upload");
        dialog.setItems(sequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sequence[which].toString().equals("Camera")) {
                    EasyImage.openCamera(PengajuanVendor.this, 0);
                } else {
                    EasyImage.openGallery(PengajuanVendor.this, 0);
                }
            }
        }).show();
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
                        logBase64 = encodeImage(selectedImage);
                        llGetKtp.setVisibility(View.VISIBLE);
                        Log.d("responBase64", " " + logBase64);
                        Picasso.with(PengajuanVendor.this).load(imageFile).fit().centerCrop().into(imgKtp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imagByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagByte, Base64.DEFAULT);
    }

    private void postPengajuan() {
        dialog.show();
        dialog.setMessage("loading");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("nama_perusahaan", etNama.getText().toString());
        loginUser.put("no_sk", etNosk.getText().toString());
        loginUser.put("tanggal_sk", etTglSk.getText().toString());
        loginUser.put("direktur_utama", etDirektur.getText().toString());
        loginUser.put("alamat", etAlamat.getText().toString());
        loginUser.put("rekening_uang", etNoRek.getText().toString());
        loginUser.put("ktp_dirut", logBase64);
        loginUser.put("nama_lengkap", getnameProfile);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.postPengajuanVendor(loginUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("responSucces", "Pengajuan " + response.code());
                    finish();
                } else {
                    dialog.dismiss();
                    Log.d("responSucces", "Else " + response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Log.d("responSucces", "On failure " + t.getMessage());

            }
        });
    }

    void getDataPicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etTglSk.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
}

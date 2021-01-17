package com.arbaelbarca.tourtravel.Activity.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.OpenCvCamera.OpenCameraView;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Fragment.FragmentAkun;
import com.arbaelbarca.tourtravel.Model.ModelProfile.Data;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResponsePengguna;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResultItem;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.arbaelbarca.tourtravel.Fragment.FragmentAkun.resultItemList;

public class EditProfile extends AppCompatActivity {

    EditText etNama, etTlpn, etEMail, etAlamat;
    String getName, getEmail, getAlamat, getNotlpn, getIduser, getImage;
    private int GALLERY = 1, CAMERA = 111;
    int PICK_IMAGE_REQUEST = 111;

    int IMAGE_REQUST = 2;
    Uri imageUri, imageCamera;
    Bitmap thumbnail;

    ImageView imgAvatar;
    RelativeLayout rlUpload, llSave;
    ProgressDialog progressDialog;
    String logBase64;
    InputStream imageStream;
    String stringPath;
    File file;
    ResponsePengguna dataProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);

//        dataProfile = (ResponsePengguna) getIntent().getSerializableExtra("dataProfile");

        etNama = findViewById(R.id.et_name);
        etTlpn = findViewById(R.id.et_phone);
        etEMail = findViewById(R.id.et_email);
        etAlamat = findViewById(R.id.et_alamat);
        imgAvatar = findViewById(R.id.iv_avatar);
        llSave = findViewById(R.id.ll_save);
        rlUpload = findViewById(R.id.circleUpload);

        etNama.setText(resultItemList.get(0).getNamaLengkap());
        etAlamat.setText(resultItemList.get(0).getAlamatRumah());
        etTlpn.setText(resultItemList.get(0).getNoTlp());
        etEMail.setText(resultItemList.get(0).getEmail());

        Glide.with(this)
                .load(resultItemList.get(0).getPhotoPengguna())
                .into(imgAvatar);

        rlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), OpenCameraView.class));
                changeUpload();
            }
        });

        Log.d("responItemlist", " " + resultItemList.get(0).getId());

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                String tlpn = etTlpn.getText().toString();
                String email = etEMail.getText().toString();

                if (logBase64 != null) {
                    updateDate(name, email, tlpn, alamat, resultItemList.get(0).getId());
                } else {
                    updateDate(name, email, tlpn, alamat, resultItemList.get(0).getId());
                }


            }
        });
    }


    private void changeUpload() {
        final CharSequence sequence[] = new CharSequence[]{"Camera", "Galery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Pilih Upload");
        dialog.setItems(sequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sequence[which].toString().equals("Camera")) {
                    EasyImage.openCamera(EditProfile.this, 0);
                } else {
                    EasyImage.openGallery(EditProfile.this, 0);
                }
            }
        }).show();
    }

    void takeCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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
                    imageCamera = Uri.fromFile(imageFile);
                    stringPath = getRealPathFromURIPath(imageCamera, EditProfile.this);
                    file = new File(stringPath);
                    try {
                        imageStream = getContentResolver().openInputStream(imageCamera);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imgAvatar.setImageBitmap(selectedImage);
                        logBase64 = encodeImage(selectedImage);
                        Log.d("responBase64", " " + logBase64 + "uri " + imageCamera);
                        Log.d("responBase64", " file " + file);

                        Picasso.with(EditProfile.this).load(imageFile).into(imgAvatar);
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

    void updateProfileUri() {
        progressDialog.show();
        progressDialog.setMessage("Loading");
        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("photo_pengguna", file.getName(), requestBody);
        Call<ResponseBody> call = apiService.postUpdateTestUri(part);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("responSucces", " " + response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });

    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    void updateDate(String username, String email, String notlpn, String alamat,
                    String idUser) {
        progressDialog.show();
        progressDialog.setMessage("Loading");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .build();


        RequestBody nama_lengkap = toRequestBody(username);
        RequestBody iduserBody = toRequestBody(idUser);
        RequestBody emailBody = toRequestBody(email);
        RequestBody notlpnBody = toRequestBody(notlpn);
        RequestBody alamatBody = toRequestBody(alamat);
        RequestBody latLngBody = toRequestBody("");
        RequestBody statusJual = toRequestBody("no");

        HashMap<String, RequestBody> loginUser = new HashMap<>();
        loginUser.put("nama_lengkap", nama_lengkap);
        loginUser.put("username", nama_lengkap);
        loginUser.put("alamat_rumah", alamatBody);
        loginUser.put("email", emailBody);
        loginUser.put("no_tlp", notlpnBody);
        loginUser.put("latlong", latLngBody);
        loginUser.put("status_penjual", statusJual);
        loginUser.put("id", iduserBody);

        MultipartBody.Part part = null;
        if (file != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData("photo_pengguna", file.getName(), requestBody);
        }
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.postUpdateUser(part, loginUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("responStatus", " S " + response.body().string());
                        finish();
                        progressDialog.dismiss();
                        showToas("Succes Upload");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();
                    Log.d("responSucces ", "gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("responOnFailure", " fail " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    void showToas(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}

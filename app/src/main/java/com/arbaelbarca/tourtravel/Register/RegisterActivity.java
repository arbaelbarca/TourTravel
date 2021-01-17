package com.arbaelbarca.tourtravel.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Login.LoginActivity;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.AdminResult;
import com.arbaelbarca.tourtravel.Model.ModelLoginUser;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText txtNama, txtEmail, txtPassword, txtTlpn;
    Button btnDaftar;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser user;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmailReg);
        txtPassword = findViewById(R.id.txtPasswordReg);
        txtTlpn = findViewById(R.id.txtNotlp);
        btnDaftar = findViewById(R.id.btnRegister);


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txtNama.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String noTlpn = txtTlpn.getText().toString();
                if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || noTlpn.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masih ada yg kosong", Toast.LENGTH_LONG).show();
                } else if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Minimal password 5 caracter", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(nama, email, password, noTlpn);
                }
            }
        });

    }

//    void register(final String username, final String email, String password, final String noTlpn) {
//        progressDialog.show();
//        progressDialog.setMessage("Loading");
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = auth.getCurrentUser();
//                            assert user != null;
//                            String userId = user.getUid();
//
//                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
//
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            hashMap.put("id", userId);
//                            hashMap.put("username", username);
//                            hashMap.put("imageurl", "default");
//                            hashMap.put("nomortlpn", noTlpn);
//                            hashMap.put("email", email);
//                            hashMap.put("pass", txtPassword.getText().toString());
//
//
//                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        finish();
//                                        progressDialog.dismiss();
//                                    }
//                                }
//                            });
//
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "gagal daftar", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }

    void registerUser(String username, String email, String notlpn, String password) {
        progressDialog.show();
        progressDialog.setMessage("Loading");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("nama_lengkap", username);
        loginUser.put("username", username);
        loginUser.put("password", password);
        loginUser.put("alamat_rumah", "");
        loginUser.put("email", email);
        loginUser.put("no_tlp", notlpn);
        loginUser.put("latlong", "");
        loginUser.put("status_penjual", "no");

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.postPengguna(loginUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("responStatus", " S " + response.body().string());
                        Log.d("responSucces ", "Mantabs ");
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                        showToas("Succes Register");
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

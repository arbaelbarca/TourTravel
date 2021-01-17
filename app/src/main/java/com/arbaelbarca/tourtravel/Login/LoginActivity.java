package com.arbaelbarca.tourtravel.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.MainActivity;

import com.arbaelbarca.tourtravel.Model.ModelLoginUser;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ResponseProfile;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.Register.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

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
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    TextView txtDaftar;
    Button btnLogin;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseUser user;
    CheckBox checkPass;
    DatabaseReference reference;
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
    static int REQ_GOOGLE = 111;
    GoogleSignInAccount account;
    public static String DATA_GOOGLE = "data";
    CheckBox checkBox;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        init();
    }

    private void init() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.key_web_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        txtDaftar = findViewById(R.id.txtDaftar);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.cbShowPassword);
        signInButton = findViewById(R.id.btnloginGoogle);
        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, REQ_GOOGLE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                masuk(email, password);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (!isChecked) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        sessionLogin();
    }

    private void masuk(String email, String password) {
        progressDialog.show();
        progressDialog.setMessage("Loading");

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data tidak boleh kosong ", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
            login(email, password);
//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Gagal Masuk", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            });
        }

    }

    private void login(String email, String password) {
        progressDialog.show();
        progressDialog.setMessage("Loading");

        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        ApiService apiService = retrofit.create(ApiService.class);

        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("username", email);
        loginUser.put("password", password);

        Call<ResponseProfile> call = apiService.getLogin(loginUser);

        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResult().getStatus();

                    if (status.equals("Berhasil Login")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        String id = response.body().getResult().getData().getId();
                        String token = response.body().getResult().getData().getToken();
                        String tokenRandom = response.body().getResult().getData().getTokenRandom();
                        getSaveData(id, token, tokenRandom);
                        progressDialog.dismiss();
                    } else {
                        showToas("Login gagal kesahaln kata sandi/email");
                        progressDialog.dismiss();
                    }
//
                } else {
                    Log.d("responSuccesLogin", " else pertama ");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                showToas("Mohon maaf, terjadi kesalahan server");
                Log.d("responSuccesLogin", " Gagal " + t.getMessage());
                progressDialog.dismiss();
            }
        });


    }

    private void getSaveData(String id, String token, String tokenRandom) {

        editor.putString("token_random", tokenRandom);
        editor.putString("token", token);
        editor.putString("iduser", id);


        editor.apply();
    }


    void sessionLogin() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String getName = sharedPreferences.getString("iduser", null);
        Log.d("responGetName", " get " + getName);

        if (user != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (getName != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GOOGLE)

            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                account = task.getResult(ApiException.class);
                onLoggedIn(account);
//                authLogin(account);
                Log.w("responEror", "Emaill :" + account.getEmail());
                Log.w("responEror", "Emaill :" + account.getIdToken());
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w("responEror", "signInResult:failed code=" + e.getStatusCode());
            }

    }

    private void onLoggedIn(final GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),
                null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = auth.getCurrentUser();
                            updateUI(user, googleSignInAccount);
                        } else {
                            Log.d("responElse ", " Credential ");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("responElse ", " oNfailure " + e.getMessage());

            }
        });


    }

    private void updateUI(FirebaseUser user, GoogleSignInAccount signInAccount) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(DATA_GOOGLE, signInAccount);
            startActivity(intent);
            finish();
        } else {

        }
    }

    void authLogin(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(DATA_GOOGLE, googleSignInAccount);
        startActivity(intent);
        finish();
    }

    void showToas(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sessionLogin();
//
//        updateUI(user, account);

//        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (alreadyloggedAccount != null) {
//            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
////            onLoggedIn(alreadyloggedAccount, account.getIdToken());
//            authLogin(account);
//
//        } else {
//            Log.d("", "Not logged in");
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionLogin();
    }
}

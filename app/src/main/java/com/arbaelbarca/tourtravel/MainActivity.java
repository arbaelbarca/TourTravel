package com.arbaelbarca.tourtravel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.arbaelbarca.tourtravel.Activity.ListPengajuanVendor;
import com.arbaelbarca.tourtravel.Activity.Profile.EditProfile;
import com.arbaelbarca.tourtravel.Favorite.FavoriteDatabase;
import com.arbaelbarca.tourtravel.Fragment.FragmentAkun;
import com.arbaelbarca.tourtravel.Fragment.FragmentAlquranHome;
import com.arbaelbarca.tourtravel.Fragment.FragmentHistory;
import com.arbaelbarca.tourtravel.Fragment.FragmentHome;
import com.arbaelbarca.tourtravel.Fragment.FragmentJadwal;
import com.arbaelbarca.tourtravel.Login.LoginActivity;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.AdminResult;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ResultItemAdmin;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResponsePengguna;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ResponseProfile;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigationView;
    FrameLayout frameLayout;
    Toolbar toolbar;
    public static GoogleSignInAccount getDataGoogle;
    public static ResponseProfile itemProfiles;
    public static ResponseProfile resultItemProfile;
    public static String getIdUser, token, tokenRandom;
    public static String getAdminUser;
    public static FavoriteDatabase favoriteDatabase;
    List<ResultItemAdmin> itemAdminList = new ArrayList<>();
    public static String idVendor;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beranda");
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();


        init();
    }

    private void init() {
        getDataGoogle = getIntent().getParcelableExtra(LoginActivity.DATA_GOOGLE);
//        Log.d("respongetData", " d " + getDataGoogle.getEmail());

        loadHome(new FragmentHome());
        dexterPermissionLocMulti();
        navigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frameLayout);

        removeNavigationShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
        getDataProfile();
        getDataVendor();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.ic_home:
                fragment = new FragmentHome();

                break;
            case R.id.ic_Jadwal:
                fragment = new FragmentJadwal();

                break;
            case R.id.ic_Toko:
                fragment = new FragmentAlquranHome();
                break;

            case R.id.ic_Akun:
                fragment = new FragmentAkun();
                break;

            case R.id.ic_history:
                fragment = new FragmentHistory();
                break;

            default:
        }
        return loadHome(fragment);
    }

    @SuppressLint("RestrictedApi")
    void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        menuView.buildMenuView();
    }

    private boolean loadHome(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_select_up, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    void dexterPermissionLocMulti() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
//                            Intent intent = new Intent(getActivity(), TambahReport.class);
//                            intent.putExtra("username", Beranda.modeluser.getUsername());
//                            startActivity(intent);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    void getDataProfile() {
        getIdUser = LoginActivity.sharedPreferences.getString("iduser", null);
        tokenRandom = LoginActivity.sharedPreferences.getString("token_random", null);
        token = LoginActivity.sharedPreferences.getString("token", null);

        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);
        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("id", getIdUser);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponsePengguna> call = apiService.getIdDataProfile(loginUser);

        call.enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, Response<ResponsePengguna> response) {
                if (response.body().getResult() != null) {
                    getAdminUser = response.body().getResult().get(0).getNamaLengkap();
                    Log.d("succes Dapat", "yess " + response.code());

                } else {
                    showDialog("Mohon maaf , terjadi kesalahan pada server");
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    void getDataVendor() {
        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<AdminResult> call = apiService.getAdmin("req-vendor.php", "read");

        call.enqueue(new Callback<AdminResult>() {
            @Override
            public void onResponse(Call<AdminResult> call, Response<AdminResult> response) {
                itemAdminList = response.body().getResult();

                if (itemAdminList.size() != 0) {
                    for (ResultItemAdmin itemAdmin : itemAdminList) {
                        idVendor = itemAdmin.getId();
                        Log.d("responGetdata Vendor", " yes " + idVendor);
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
//                finish();
            }
        });

        builder.create().show();
    }
}

package com.arbaelbarca.tourtravel.Fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Activity.InsertDataVendor;
import com.arbaelbarca.tourtravel.Activity.ListPengajuanVendor;
import com.arbaelbarca.tourtravel.Activity.PengajuanVendor;
import com.arbaelbarca.tourtravel.Activity.Profile.EditProfile;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Login.LoginActivity;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ModelUser;
import com.arbaelbarca.tourtravel.Model.ModelProfile.Data;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResponsePengguna;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResultItem;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ResponseProfile;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAkun extends Fragment {

    Button btnKeluar;
    ProgressDialog dialog;
    FirebaseAuth auth;
    GoogleSignInAccount account;
    GoogleSignInClient client;
    String email;
    TextView txtEmail, txtUsername, txtStatusOk, txtButton;
    FirebaseUser user;
    LinearLayout llLogout;
    String getUser, getEmail, getIdUser, getNama;

    LinearLayout llEditProfile, llPengjuan, listPengajuan, llPengajuanAdmin;
    ImageView imgAvatar, imgCeklist;
    ProgressDialog progressDialog;
    RelativeLayout rlPengjaun;
    LinearLayout llinputPaket;

    public static List<ResultItem> resultItemList = new ArrayList<>();

    public FragmentAkun() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_akun, container, false);

        dialog = new ProgressDialog(getActivity());
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(getActivity());
        init(view);
        return view;
    }

    private void init(View view) {

        llinputPaket = view.findViewById(R.id.llinputPaket);
        txtEmail = view.findViewById(R.id.txtEmailAkun);
        txtUsername = view.findViewById(R.id.txtUsername);
        llLogout = view.findViewById(R.id.llLogout);
        imgAvatar = view.findViewById(R.id.img_avatar);
        llEditProfile = view.findViewById(R.id.llUbahProfile);
        listPengajuan = view.findViewById(R.id.llListPengajuan);
        llPengajuanAdmin = view.findViewById(R.id.llPengajuanAdmin);
        llPengjuan = view.findViewById(R.id.llPengajuan);
        rlPengjaun = view.findViewById(R.id.rlPengajuan);
        txtStatusOk = view.findViewById(R.id.txtStatusSucces);
        imgCeklist = view.findViewById(R.id.imgCeklist);
        txtButton = view.findViewById(R.id.tv_save);

        getDataProfile();


        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Apakah anda yakin ingin keluar !!!");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        rlPengjaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PengajuanVendor.class);
                intent.putExtra("name", getNama);
                startActivity(intent);
            }
        });


        llinputPaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InsertDataVendor.class));
            }
        });

    }

    void getDataProfile() {

        progressDialog.show();
        progressDialog.setMessage("Loading");
        getIdUser = LoginActivity.sharedPreferences.getString("iduser", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("id", getIdUser);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponsePengguna> call = apiService.getDataProfile(loginUser);
        call.enqueue(new Callback<ResponsePengguna>() {
            @Override
            public void onResponse(Call<ResponsePengguna> call, final Response<ResponsePengguna> response) {
                String getNama = response.body().getResult().get(0).getNamaLengkap();
                String getEmail = response.body().getResult().get(0).getEmail();
                String getImage = response.body().getResult().get(0).getPhotoPengguna();

                txtUsername.setText(getNama);
                txtEmail.setText(getEmail);
                progressDialog.dismiss();

                if (getImage != null) {
                    Glide.with(getActivity())
                            .load(getImage)
                            .into(imgAvatar);
                }

                llEditProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultItemList = response.body().getResult();
                        Intent intent = new Intent(getActivity(), EditProfile.class);
                        startActivity(intent);
                    }
                });

                if (getNama.equalsIgnoreCase("Admin") || getNama.equalsIgnoreCase("admin")) {
                    llPengajuanAdmin.setVisibility(View.VISIBLE);
                    imgCeklist.setVisibility(View.VISIBLE);

                    listPengajuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ListPengajuanVendor.class);
                            startActivity(intent);
                        }
                    });
                } else if (getNama.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || getNama.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
                    llPengajuanAdmin.setVisibility(View.GONE);
                } else {
                    llPengajuanAdmin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePengguna> call, Throwable t) {
                t.printStackTrace();
                t.getMessage();
                progressDialog.dismiss();

            }
        });


    }


    void logout() {
        dialog.show();
        dialog.setMessage("Loading");
//        if (auth != null) {
//            auth.getInstance().signOut();
//            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//            getActivity().finish();
//            dialog.dismiss();
//            Log.d("responAuthLogout", "yess");
//        }

        if (getEmail != null) {
            dialog.dismiss();
            Log.d("responLoginbiasa", "yess");
            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            getActivity().finish();
        }


    }


}

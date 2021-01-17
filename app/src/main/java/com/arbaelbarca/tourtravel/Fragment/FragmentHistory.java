package com.arbaelbarca.tourtravel.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity;
import com.arbaelbarca.tourtravel.Adapter.AdapterHistoryTransaksi;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Login.LoginActivity;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPaketItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPenggunaItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.MemberAdditionItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResponseHistory;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistory extends Fragment {

    RecyclerView rvHistory;
    AdapterHistoryTransaksi adapterHistoryTransaksi;
    List<InfoPaketItem> infoPaketItemList = new ArrayList<>();
    List<InfoPenggunaItem> infoPenggunaItemList = new ArrayList<>();
    List<MemberAdditionItem> memberAdditionItemList = new ArrayList<>();

    ArrayList<ResultItem> historyList = new ArrayList<>();
    ProgressBar progresHistory;
    Button btnExpand;
    ExpandableLinearLayout expandHistory;
    public static ResultItem item;

    public FragmentHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_history, container, false);

        initial(view);
        return view;
    }

    private void initial(View view) {
        rvHistory = view.findViewById(R.id.rvHistory);
        btnExpand = view.findViewById(R.id.klikExpand);
        expandHistory = view.findViewById(R.id.expandHistory);
        progresHistory = view.findViewById(R.id.progressHistory);


        getDataHistory(MainActivity.getIdUser);


        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandHistory.toggle();
                expandHistory.initLayout();
                expandHistory.setInRecyclerView(true);
            }
        });
    }

    private void getDataHistory(String id) {
        progresHistory.setVisibility(View.VISIBLE);
        Retrofit retrofit = ServiceApiClient.getApiListVendor(getActivity());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseHistory> call;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idpengguna", id);
        hashMap.put("token", MainActivity.token);

        if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
            call = apiService.getListHistory(hashMap);
        } else if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.ADMIN_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.ADMIN_AKUN_SMALL)) {
            call = apiService.getListAdminHistory();
        } else {
            call = apiService.getListHistory(hashMap);
        }

        call.enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                progresHistory.setVisibility(View.GONE);
                assert response.body() != null;
                historyList = response.body().getResult();

                if (historyList.size() > 0) {
                    adapterHistoryTransaksi = new AdapterHistoryTransaksi(getActivity(), historyList);
                    rvHistory.setHasFixedSize(true);
                    rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvHistory.setAdapter(adapterHistoryTransaksi);
                    rvHistory.setVisibility(View.VISIBLE);
                    getSaveToken(response.body().getToken_random());
                    Log.d("responHistory", " " + new Gson().toJson(historyList));

                    adapterHistoryTransaksi.OnActionClick(new AdapterHistoryTransaksi.ClickHistory() {
                        @Override
                        public void OnClick(View v, int pos) {
                            item = historyList.get(pos);
                            Intent intent = new Intent(getActivity(), DetailHistoryActivity.class);
                            intent.putExtra("dataVendor", (Serializable) item.getInfoVendor());
                            intent.putExtra("dataPaket", (Serializable) item.getInfoPaket());
                            intent.putExtra("dataAnggota", (Serializable) item.getMemberAddition());
                            intent.putExtra("dataPengguna", (Serializable) item.getInfoPengguna());
                            intent.putExtra("pos", pos);
                            startActivity(intent);
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                progresHistory.setVisibility(View.GONE);
                t.printStackTrace();
                Log.d("responHistory", " else " + t.getMessage());
            }
        });
    }

    private void getSaveToken(String tokenRandom) {
        MainActivity.editor.putString("token_random", tokenRandom);
        MainActivity.editor.commit();
    }

}

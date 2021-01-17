package com.arbaelbarca.tourtravel.Fragment.FragmentAlquran;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arbaelbarca.tourtravel.Adapter.Alquran.AdapterAllSurah;
import com.arbaelbarca.tourtravel.Adapter.Alquran.AdapterJuzAll;
import com.arbaelbarca.tourtravel.Adapter.Alquran.AdapterJuzNumber;
import com.arbaelbarca.tourtravel.Model.AlquranModel.DataItem;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Data;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.ResponseJuz;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.Surahs;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ResponseSurah;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {

    ProgressDialog progressDialog;
    AdapterAllSurah adapterAllSurah;
    RecyclerView rvSurah, rvNumberJuz;
    List<DataItem> surahList = new ArrayList<>();

    AdapterJuzNumber adapterJuzNumber;
    List<String> stringList = new ArrayList<>();
    ProgressBar progressBar;
    AdapterJuzAll adapterJuzAll;

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_juz, container, false);
        progressDialog = new ProgressDialog(getActivity());

        initialAll(view);

        return view;

    }

    private void initialAll(View view) {
        rvSurah = view.findViewById(R.id.rvAllSurah);
        rvNumberJuz = view.findViewById(R.id.rvNumberJuz);
        progressBar = view.findViewById(R.id.progressRvJuz);
        getSuratAll(false);
        getNumberJuz();
    }

    private void getNumberJuz() {
        int[] nums = new int[31];
        for (int i = 1; i < nums.length; i++) {
            Log.d("responNumber", "" + i);
            String getNumber = String.valueOf(i);
            stringList.add(getNumber);
        }

        adapterJuzNumber = new AdapterJuzNumber(getActivity(), stringList);
        rvNumberJuz.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvNumberJuz.setHasFixedSize(true);
        rvNumberJuz.setAdapter(adapterJuzNumber);

        adapterJuzNumber.click(new AdapterJuzNumber.ActionClick() {
            @Override
            public void clickActionJuz(View view, int pos) {
                String getListNumber = stringList.get(pos);
//                getJuz(getListNumber);
            }
        });
    }

//    private void getJuz(String number) {
//        progressBar.setVisibility(View.VISIBLE);
//        Retrofit retrofit = ServiceApiClient.getApiAlquran(getActivity());
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        Call<ResponseJuz> call;
//        call = apiService.getLisJuzKategori(number);
//
//        call.enqueue(new Callback<ResponseJuz>() {
//            @Override
//            public void onResponse(Call<ResponseJuz> call, Response<ResponseJuz> response) {
//                progressBar.setVisibility(View.GONE);
//                Surahs surahsList = response.body().getData().getSurahs();
//                Data dataList = response.body().getData();
//
//                adapterJuzAll = new AdapterJuzAll(getActivity(), dataList, surahsList);
//                rvSurah.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rvSurah.setHasFixedSize(true);
//                rvSurah.setAdapter(adapterJuzAll);
//                getSuratAll(true);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseJuz> call, Throwable t) {
//                t.getMessage();
//                t.printStackTrace();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//
//
//    }


    private void getSuratAll(boolean isCek) {
        progressDialog.show();
        progressDialog.setMessage("Loading Juz");
        progressDialog.setCancelable(false);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = ServiceApiClient.getApiAlquran(getActivity());
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseSurah> call = null;

        if (isCek) {

        } else {
            call = apiService.getListAllSurah();

        }

        call.enqueue(new Callback<ResponseSurah>() {
            @Override
            public void onResponse(Call<ResponseSurah> call, Response<ResponseSurah> response) {
                progressDialog.dismiss();
                surahList = response.body().getData();
                adapterAllSurah = new AdapterAllSurah(getActivity(), surahList);
                rvSurah.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvSurah.setHasFixedSize(true);
                rvSurah.setAdapter(adapterAllSurah);
                rvSurah.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseSurah> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
}

package com.arbaelbarca.tourtravel.Fragment.FragmentAlquran;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.arbaelbarca.tourtravel.Activity.DetailQuranRead;
import com.arbaelbarca.tourtravel.Adapter.Alquran.AdapterAllSurah;
import com.arbaelbarca.tourtravel.Model.AlquranModel.DataItem;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ResponseSurah;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.google.protobuf.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlquranFragment extends Fragment {


    ProgressDialog progressDialog;
    AdapterAllSurah adapterAllSurah;
    RecyclerView rvSurah;
    public static List<DataItem> surahList = new ArrayList<>();

    public AlquranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alquran, container, false);

        progressDialog = new ProgressDialog(getActivity());
        initialAll(view);

        return view;
    }

    private void initialAll(View view) {
        rvSurah = view.findViewById(R.id.rvAllSurah);
        getSuratAll();
    }

    private void getSuratAll() {
        progressDialog.show();
        progressDialog.setMessage("Loading Alquran");
        progressDialog.setCancelable(false);
        Retrofit retrofit = ServiceApiClient.getApiAlquran(getActivity());
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseSurah> call = apiService.getListAllSurah();

        call.enqueue(new Callback<ResponseSurah>() {
            @Override
            public void onResponse(Call<ResponseSurah> call, Response<ResponseSurah> response) {
                progressDialog.dismiss();
                surahList = response.body().getData();
                adapterAllSurah = new AdapterAllSurah(getActivity(), surahList);
                rvSurah.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvSurah.setHasFixedSize(true);
                rvSurah.setAdapter(adapterAllSurah);
                int resId = R.anim.layout_animation;
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
                rvSurah.setLayoutAnimation(animationController);
                rvSurah.scheduleLayoutAnimation();
                rvSurah.getAdapter().notifyDataSetChanged();
//                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//                itemAnimator.setAddDuration(1000);
//                itemAnimator.setRemoveDuration(500);
//                rvSurah.setItemAnimator(itemAnimator);

                adapterAllSurah.Onclick(new AdapterAllSurah.onClickRead() {
                    @Override
                    public void clickDetail(View view, int pos, DataItem dataItem) {
                        Intent intent = new Intent(getActivity(), DetailQuranRead.class);
                        intent.putExtra("data", dataItem);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSurah> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                t.printStackTrace();
            }
        });

    }

}

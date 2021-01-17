package com.arbaelbarca.tourtravel.Fragment.FragmentAlquran;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arbaelbarca.tourtravel.Activity.DetailQuranRead;
import com.arbaelbarca.tourtravel.Adapter.AdapterFavoriteList;
import com.arbaelbarca.tourtravel.Favorite.FavoriteList;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    AdapterFavoriteList adapterFavoriteList;

    ProgressBar progressBarFav;
    RecyclerView rvFavorite;

    Unbinder unbinder;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        progressBarFav = view.findViewById(R.id.progressBarFav);
        rvFavorite = view.findViewById(R.id.rvFavorite);

        getDataFav();

        return view;

    }

    private void getDataFav() {
        progressBarFav.setVisibility(View.VISIBLE);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<FavoriteList> favoriteLists = MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();

        if (favoriteLists != null){
            adapterFavoriteList = new AdapterFavoriteList(getActivity(), favoriteLists);
            progressBarFav.setVisibility(View.GONE);
            rvFavorite.setVisibility(View.VISIBLE);
            rvFavorite.setAdapter(adapterFavoriteList);

            adapterFavoriteList.onClick(new AdapterFavoriteList.clickDetail() {
                @Override
                public void onActionClick(View v, int pos, FavoriteList favoriteList) {
                    Intent intent = new Intent(getActivity(), DetailQuranRead.class);
                    intent.putExtra("dataFav", favoriteList);
                    startActivity(intent);
                }
            });
        }

    }

}

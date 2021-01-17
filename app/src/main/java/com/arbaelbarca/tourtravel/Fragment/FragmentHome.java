package com.arbaelbarca.tourtravel.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.Umroh;
import com.arbaelbarca.tourtravel.Adapter.MenuAdapter;
import com.arbaelbarca.tourtravel.Adapter.MyPager;
import com.arbaelbarca.tourtravel.Adapter.PlacesAutoCompleteAdapter;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Model.ModelMenu;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ResponseNearby.ResponsePlace;
import com.arbaelbarca.tourtravel.ResponseNearby.ResultsItem;
import com.arbaelbarca.tourtravel.Utils.GpsLocation;
import com.arbaelbarca.tourtravel.Utils.TrackGPS;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    MyPager myPager;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    MenuAdapter menuAdapter;
    RecyclerView rvMenu;
    ModelMenu menu;
    List<ModelMenu> menuList = new ArrayList<>();
    ExpandableRelativeLayout expandableRelativeLayout;


    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        init(view);

//        getMyLok();

        return view;


    }


    private void init(View view) {
        myPager = new MyPager(getActivity());
        viewPager = view.findViewById(R.id.view_pager);
        rvMenu = view.findViewById(R.id.rvMenuKategori);
        circleIndicator = view.findViewById(R.id.circle);
        viewPager.setAdapter(myPager);
        circleIndicator.setViewPager(viewPager);
        expandableRelativeLayout = view.findViewById(R.id.expandLinear);

        getMenu();
    }

    private void getMenu() {
        menuAdapter = new MenuAdapter(getActivity(), menuList);

        menu = new ModelMenu();
        menu.setId(0);
        menu.setTitle("Umroh");
        menu.setImage(R.drawable.kaaba);
        menuAdapter.recreate(menu);

        menu = new ModelMenu();
        menu.setId(1);
        menu.setTitle("Aqiqoh");
        menu.setImage(R.drawable.kaaba);
        menuAdapter.recreate(menu);


        menu = new ModelMenu();
        menu.setId(2);
        menu.setTitle("Qurban");
        menu.setImage(R.drawable.goat);
        menuAdapter.recreate(menu);


        menu = new ModelMenu();
        menu.setId(3);
        menu.setTitle("Umroh");
        menu.setImage(R.drawable.kaaba);
        menuAdapter.recreate(menu);

        menu = new ModelMenu();
        menu.setId(4);
        menu.setTitle("Umroh");
        menu.setImage(R.drawable.kaaba);
        menuAdapter.recreate(menu);

        menu = new ModelMenu();
        menu.setId(5);
        menu.setTitle("Umroh");
        menu.setImage(R.drawable.kaaba);
        menuAdapter.recreate(menu);

        rvMenu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMenu.setHasFixedSize(true);
        rvMenu.setAdapter(menuAdapter);


        menuAdapter.Click(new MenuAdapter.ActionClick() {
            @Override
            public void clickAction(View view, int pos) {
                menu = menuList.get(pos);

                if (menu.getId() == 0) {
                    startActivity(new Intent(getActivity(), Umroh.class));
                } else if (menu.getId() == 1) {
//                    expandableRelativeLayout.toggle();
                }
            }
        });

    }


}

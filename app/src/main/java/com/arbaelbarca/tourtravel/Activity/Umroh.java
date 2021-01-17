package com.arbaelbarca.tourtravel.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Adapter.AdapterAdminVendor;
import com.arbaelbarca.tourtravel.Adapter.AdapterPlaceNearby;
import com.arbaelbarca.tourtravel.Adapter.ItemClickPlace;
import com.arbaelbarca.tourtravel.Adapter.PlacesAutoCompleteAdapter;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.AdminResult;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ResponseAdmin;
import com.arbaelbarca.tourtravel.Model.AdminUmroh.ResultItemAdmin;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ResponseNearby.ResponsePlace;
import com.arbaelbarca.tourtravel.ResponseNearby.ResultsItem;
import com.arbaelbarca.tourtravel.Utils.GpsLocation;
import com.arbaelbarca.tourtravel.Utils.TrackGPS;
import com.google.android.gms.common.ConnectionResult;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Umroh extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {
    SupportMapFragment supportMapFragment;
    GpsLocation gpsLocation;
    TrackGPS gps;
    double latitude = 0.0;
    double longitude = 0.0;
    GoogleMap map;
    List<ResultsItem> placeList = new ArrayList<>();
    List<ResultItemAdmin> listAdmin = new ArrayList<>();

    AdapterPlaceNearby adapterPlaceNearby;
    AdapterAdminVendor adapterAdminVendor;
    RecyclerView rvMaps, rvSearch;
    EditText txtSearch;
    PlacesAutoCompleteAdapter autoCompleteAdapter;
    GoogleApiClient googleApiClient;
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(-0, 0), new LatLng(0, 0));
    private PlaceBuffer placesBuffer;
    public static ResultItemAdmin itemAdmin;

    Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umroh);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        gps = new TrackGPS(this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();

    }

    private void init() {
        rvMaps = findViewById(R.id.rvMaps);
        txtSearch = findViewById(R.id.txtSearch);
        rvSearch = findViewById(R.id.rvSearch);
        getMyLok();
        buildGoogleApiClient();
        googleApiClient.connect();
        getAdminVendor();


    }

    private void getMyLok() {
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        String latlot = latitude + "," + longitude;
        LatLng latLng = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        searchPlaces();
//        getRadiusArea("json?", latlot, "1000.0", "true", googleMap);

    }

    private void getAdminVendor() {

        Retrofit retrofit = ServiceApiClient.getApiListVendor(this);

        ApiService apiService = retrofit.create(ApiService.class);
        Call<AdminResult> call = apiService.getAdmin("req-vendor.php", "read");
        call.enqueue(new Callback<AdminResult>() {
            @Override
            public void onResponse(Call<AdminResult> call, Response<AdminResult> response) {
                if (response.isSuccessful()) {
                    listAdmin = response.body().getResult();
                    Log.d("responAdmin", " yes " + response.body());

                    for (int i = 0; i < listAdmin.size(); i++) {

//                        double lat = Double.parseDouble(listAdmin.get(i).getLatlong());
                        String[] spliLat = listAdmin.get(i).getLatlong().split(",");
                        double lat = Double.parseDouble(spliLat[0]);
                        double lot = Double.parseDouble(spliLat[1]);
                        Log.d("responSplit ", " S " + spliLat[0] + " l " + spliLat[1]);
//                        double lot = listAdmin.get(i).getGeometry().getLocation().getLng();
                        String title = listAdmin.get(i).getEmail();
                        String snippet = listAdmin.get(i).getAlamatVendor();
                        LatLng latLng = new LatLng(lat, lot);
                        map.addMarker(new MarkerOptions().position(latLng).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.mecca)).title(title).snippet(snippet));

                    }

//                    mCircle = map.addCircle(new CircleOptions()
//                            .center(new LatLng(latitude, longitude))//
//                            .radius(Double.parseDouble(radius))
//                            .strokeColor(Color.RED)
//                            .strokeWidth(3f)
//                            .fillColor(Color.argb(79, 150, 50, 50)));

                    adapterAdminVendor = new AdapterAdminVendor(Umroh.this, listAdmin);
                    rvMaps.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    rvMaps.setHasFixedSize(true);
                    rvMaps.setVisibility(View.VISIBLE);
                    rvMaps.setAdapter(adapterAdminVendor);

                    adapterAdminVendor.ActionClick(new AdapterAdminVendor.ClickDetail() {
                        @Override
                        public void clickDetail(View v, int pos) {
                            itemAdmin = listAdmin.get(pos);
                            Intent intent = new Intent(getApplicationContext(), DetailUmroh.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    Log.d("responAdmin", " Failed ");
                }
            }

            @Override
            public void onFailure(Call<AdminResult> call, Throwable t) {
                Log.d("responAdmin", " On Failure" + t.getMessage());

            }
        });


    }

//    private void getRadiusArea(String url, final String latlong, final String radius, String sensor, final GoogleMap map) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Cons.URL_Nearby)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<ResponsePlace> call = apiService.getRadius(url, latlong, radius, sensor, "travel_agency", Cons.Key_Maps);
//
//        call.enqueue(new Callback<ResponsePlace>() {
//            @Override
//            public void onResponse(Call<ResponsePlace> call, ResponseListPengajuan<ResponsePlace> response) {
//                if (response.isSuccessful()) {
//                    placeList = response.body().getResults();
//                    Log.d("responSuccessNerby", " yes " + response.body().getStatus());
//                    Log.d("responResult", "r " + new Gson().toJson(placeList));
//                    Log.d("responResult", "body " + response.body().getResults());
//
//                    for (int i = 0; i < placeList.size(); i++) {
//
//                        double lat = placeList.get(i).getGeometry().getLocation().getLat();
//                        double lot = placeList.get(i).getGeometry().getLocation().getLng();
//                        String title = placeList.get(i).getName();
//                        String snippet = placeList.get(i).getVicinity();
//                        LatLng latLng = new LatLng(lat, lot);
//                        map.addMarker(new MarkerOptions().position(latLng).icon(
//                                BitmapDescriptorFactory.fromResource(R.drawable.mecca)).title(title).snippet(snippet));
//
//                    }
//
////                    mCircle = map.addCircle(new CircleOptions()
////                            .center(new LatLng(latitude, longitude))//
////                            .radius(Double.parseDouble(radius))
////                            .strokeColor(Color.RED)
////                            .strokeWidth(3f)
////                            .fillColor(Color.argb(79, 150, 50, 50)));
//
//
//                    adapterPlaceNearby = new AdapterPlaceNearby(Umroh.this, placeList);
//                    rvMaps.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    rvMaps.setHasFixedSize(true);
//                    rvMaps.setVisibility(View.VISIBLE);
//                    rvMaps.setAdapter(adapterPlaceNearby);
//
//                } else {
//                    Log.d("responSuccessNerby", " Failed ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePlace> call, Throwable t) {
//                Log.d("responSuccessNerby", " onFailure " + t.getMessage());
//
//            }
//        });
//
//
//    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    private void searchPlaces() {
        autoCompleteAdapter = new PlacesAutoCompleteAdapter(getApplicationContext(), R.layout.searchview_adapter,
                googleApiClient, BOUNDS_INDIA, null);

        rvSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(autoCompleteAdapter);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && googleApiClient.isConnected()) {
                    autoCompleteAdapter.getFilter().filter(s.toString());
                    rvSearch.setVisibility(View.VISIBLE);
                    rvMaps.setVisibility(View.GONE);
                    supportMapFragment.getView().setVisibility(View.GONE);


                } else if (!googleApiClient.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Eror COnected", Toast.LENGTH_SHORT).show();
//                    Log.e(Constants.PlacesTag, Constants.API_NOT_CONNECTED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rvSearch.addOnItemTouchListener(new ItemClickPlace(
                this, new ItemClickPlace.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final PlacesAutoCompleteAdapter.PlaceAutocomplete item = autoCompleteAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i("TAG", "Autocomplete item selected: " + item.description);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(googleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getCount() == 1) {
                            LatLng latLong = places.get(0).getLatLng();
                            Log.d("responLatLng", "arba  " + latLong);

                            placesBuffer = places;

                            map.addMarker(new MarkerOptions().position(latLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_point_map)).
                                    title(places.get(0).getName().toString()));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15));
                            supportMapFragment.getView().setVisibility(View.VISIBLE);
                            mCircle = map.addCircle(new CircleOptions()
                                    .center(latLong)//
                                    .radius(1000.0)
                                    .strokeColor(Color.RED)
                                    .strokeWidth(3f)
                                    .fillColor(Color.argb(79, 150, 50, 50)));


                            String latlot = places.get(0).getLatLng().latitude + "," + places.get(0).getLatLng().longitude;


                            getDetectionPlace("json?", latlot, "1000.0", "true", map);


                        } else {
                            Toast.makeText(getApplicationContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.i("TAG", "Clicked: " + item.description);
                Log.i("TAG", "Called getPlaceById to get Place details for " + item.placeId);
            }
        }
        ));
    }

    void getDetectionPlace(String url, final String latlong, final String radius, String sensor, final GoogleMap map) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Cons.URL_Nearby)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponsePlace> call = apiService.getRadius(url, latlong, radius, sensor, "travel_agency", Cons.Key_Maps);
        call.enqueue(new Callback<ResponsePlace>() {
            @Override
            public void onResponse(Call<ResponsePlace> call, Response<ResponsePlace> response) {
                if (response.isSuccessful()) {
                    placeList = response.body().getResults();
                    Log.d("responSuccessNerby", " yes " + response.body().getStatus());
                    Log.d("responResult", "r " + new Gson().toJson(placeList));
                    Log.d("responResult", "body " + response.body().getResults());

                    for (int i = 0; i < placeList.size(); i++) {

                        double lat = placeList.get(i).getGeometry().getLocation().getLat();
                        double lot = placeList.get(i).getGeometry().getLocation().getLng();
                        String title = placeList.get(i).getName();
                        String snippet = placeList.get(i).getVicinity();
                        LatLng latLng = new LatLng(lat, lot);
                        map.addMarker(new MarkerOptions().position(latLng).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.mecca)).title(title).snippet(snippet));
                    }

                    rvSearch.setVisibility(View.GONE);
                    adapterPlaceNearby = new AdapterPlaceNearby(Umroh.this, placeList);
                    rvMaps.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvMaps.setHasFixedSize(true);
                    rvMaps.setVisibility(View.VISIBLE);
                    rvMaps.setAdapter(adapterPlaceNearby);


                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponsePlace> call, Throwable t) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

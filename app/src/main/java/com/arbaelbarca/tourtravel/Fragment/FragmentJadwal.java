package com.arbaelbarca.tourtravel.Fragment;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Model.ModelJadwalSolat.Data;
import com.arbaelbarca.tourtravel.Model.ModelJadwalSolat.ResponseJadwal;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.Utils.GpsLocation;
import com.arbaelbarca.tourtravel.Utils.TrackGPS;
import com.arbaelbarca.tourtravel.lib.CompassSensorManager;
import com.arbaelbarca.tourtravel.lib.CompassView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJadwal extends Fragment {


    GpsLocation gpsLocation;
    double latitude = 0;
    double longitude = 0;
    TrackGPS trackGPS;
    ProgressDialog progressDialog;
    ImageButton nextDate, previosData;
    TextClock currentTimeTextView;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String getDate;
    TextView txtDate;
    TextView txtTimeSubuh, txtDuhur, txtAshar, txtMagrib, txtIsya, txtLokasi, txtTerbit;
    private int mYear, mMonth, mDay, mHour, mMinute, mDayRead;
    RelativeLayout rlTimeSolat;
    Data dataList;
    HorizontalCalendar horizontalCalendar;
    LinearLayout llCompass;
    private static final int GRID_SIZE = 10;

    private CompassSensorManager compassSensorManager;

    public FragmentJadwal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_jadwal, container, false);
        gpsLocation = new GpsLocation(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        trackGPS = new TrackGPS(getActivity());

        compassSensorManager = new CompassSensorManager(getActivity());

        initial(view);

        return view;
    }

    private void initial(View view) {

        txtDate = view.findViewById(R.id.txtDate);
        rlTimeSolat = view.findViewById(R.id.rlTime);
        txtTimeSubuh = view.findViewById(R.id.time_subuh);
        txtDuhur = view.findViewById(R.id.time_zuhur);
        txtAshar = view.findViewById(R.id.time_ashar);
        txtMagrib = view.findViewById(R.id.time_maghrib);
        txtTerbit = view.findViewById(R.id.time_terbit);
        txtIsya = view.findViewById(R.id.time_isya);
        txtLokasi = view.findViewById(R.id.txtLokasi);
        llCompass = view.findViewById(R.id.llCompasView);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getDate = simpleDateFormat.format(calendar.getTime());

        getJadwalSolat(getDate, view);
        getLocation();

        rlTimeSolat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataPicker(view);
            }
        });


        getDatePicker(view);

    }

    private void getCompasView(View view, double lat, double lot) {
        LinearLayout contentView = view.findViewById(R.id.llCompasView);
//        for (int x = 0; x <= GRID_SIZE; x++) {
//

//        }
        LinearLayout xLineLayout = new LinearLayout(getActivity());
        contentView.addView(xLineLayout);
//        for (int y = 0; y <= GRID_SIZE; y++) {
        xLineLayout.addView(generateCompassView(lat, lot));

//        }

    }

    private CompassView generateCompassView(double lat, double lot) {
        Location moscowLocation = new Location("");
        moscowLocation.setLatitude(latitude);
        moscowLocation.setLongitude(longitude);

        Location krakowLocation = new Location("");
        krakowLocation.setLatitude(lat);
        krakowLocation.setLongitude(lot);

        CompassView compassView = new CompassView(getActivity());

//        Drawable dr = getResources().getDrawable(R.drawable.compass);
//        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));

        compassView.init(compassSensorManager, moscowLocation, krakowLocation, R.drawable.compass_bg);
        return compassView;
    }

    @Override
    public void onResume() {
        super.onResume();
        compassSensorManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        compassSensorManager.onPause();
    }

    private void getDatePicker(final View rootView) {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calenderView)
                .range(startDate, endDate)
                .datesNumberOnScreen(6)
                .build();

//        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(getActivity(), R.id.calenderView)
//                .range(startDate, endDate)
//                .datesNumberOnScreen(5)
//                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                Log.d("responSelect", "yes1 " + DateFormat.getInstance().format(date.getFirstDayOfWeek()));
                Log.d("responSelect", "yes2 " + DateFormat.getInstance().format(date.getTimeInMillis()));
                Log.d("responSelect", "yes3 " + DateFormat.getInstance().format(date.getTime()));
                String[] getDate = DateFormat.getInstance().format(date.getTime()).split(" ");
                String dateSplit = getDate[0];
                String[] getCalender = dateSplit.split("/");
                String getDay = getCalender[0];
                String getMonth = getCalender[1];
                String getYear = getCalender[2];
                String allDate = "20" + getYear + "-" + getMonth + "-" + getDay;
                Log.d("responSelect", "yesaaa " + allDate);

                getJadwalSolat(allDate, rootView);


            }
        });


    }

    private void getDataPicker(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy"); //Date and time
                String currentDate = sdf.format(calendar.getTime());

                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(year, month + 1, dayOfMonth);
                String replaceTime = simpledateformat.format(date);
                Log.d("responTimeDialog ", " D " + replaceTime + currentDate);
                String getTime = year + "-" + (month + 1) + "-" + dayOfMonth;
                getJadwalSolat(getTime, view);
                txtDate.setText("" + replaceTime + "," + currentDate + "");

            }
        }, mYear, mMonth, mDay);
        dialog.show();

    }

    private void getJadwalSolat(String getTime, final View view) {
        progressDialog.show();
        progressDialog.setMessage("Loading");
//        if (gpsLocation != null) {
//            latitude = gpsLocation.getLatitude();
//            longitude = gpsLocation.getLongitude();
//        }

        progressDialog.setCancelable(false);
        if (trackGPS.canGetLocation()) {
            latitude = trackGPS.getLatitude();
            longitude = trackGPS.getLongitude();
        }

        Retrofit retrofit = ServiceApiClient.getApiJadwal(getActivity());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseJadwal> call = apiService. getJadwalSolat("pray/", String.valueOf(latitude), String.valueOf(longitude), getTime);

        call.enqueue(new Callback<ResponseJadwal>() {
            @Override
            public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
                if (response.isSuccessful()) {
                    dataList = response.body().getData();
                    Log.d("responSucces", "yes " + response.body().getStatus());
                    progressDialog.dismiss();
                    getSolat(dataList, response);
                    double getLat = Double.parseDouble(response.body().getLocation().getLatitude());
                    double getLot = Double.parseDouble(response.body().getLocation().getLongitude());
//                    getCompasView(view, getLat, getLot);

                } else {
                    Log.d("responSucces", "else " + response.code());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseJadwal> call, Throwable t) {
                t.printStackTrace();
                t.getMessage();
                progressDialog.dismiss();
            }
        });
    }

    private void getSolat(Data dataList, Response<ResponseJadwal> response) {
        if (dataList != null) {
            txtTimeSubuh.setText(dataList.getFajr());
            txtDuhur.setText(dataList.getDhuhr());
            txtAshar.setText(dataList.getAsr());
            txtMagrib.setText(dataList.getMaghrib());
            txtTerbit.setText(dataList.getSunrise());
            txtIsya.setText(dataList.getIsha());
        }

    }

    private void getLocation() {
        progressDialog.show();
        progressDialog.setMessage("Mencari Lokasi");
        progressDialog.setCancelable(false);
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(getActivity(), "Butuh Permission Location", 10, perms);
        } else {

            // GET CURRENT LOCATION
            FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Do it all with location
                        Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                        // Display in Toast
                        Geocoder gcd3 = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses;

                        try {
                            addresses = gcd3.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                //while(locTextView.getText().toString()=="Location") {
                                Log.d("respon Cek lokasi", "1 :" + addresses.get(0).getLocality().toString());
                                String lokasi = addresses.get(0).getAddressLine(0).toString();

                                if (lokasi != null) {
                                    txtLokasi.setText("Wilayah : " + lokasi);
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Swipe Layar Untuk Refresh", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }

                                // }
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

        }
    }
}

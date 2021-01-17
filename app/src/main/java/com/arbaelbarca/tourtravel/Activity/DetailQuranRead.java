package com.arbaelbarca.tourtravel.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.arbaelbarca.tourtravel.Adapter.AdapterDetailReadQuran;
import com.arbaelbarca.tourtravel.Adapter.Alquran.AdapterAllSurah;
import com.arbaelbarca.tourtravel.Favorite.FavoriteList;
import com.arbaelbarca.tourtravel.Fragment.FragmentAlquran.AlquranFragment;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.AlquranModel.DataItem;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran.AyahsItemRead;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran.ResponseRead;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailQuranRead extends AppCompatActivity implements JcPlayerManagerListener {

    AdapterDetailReadQuran adapterDetailReadQuran;
    List<AyahsItemRead> itemReadLists = new ArrayList<>();

    @BindView(R.id.progressBarRead)
    ProgressBar progressBarRead;
    @BindView(R.id.rvDetailRead)
    RecyclerView rvDetailRead;

    DataItem dataItem;


    @BindView(R.id.playAudio)
    JcPlayerView playAudio;

    ArrayList<JcAudio> listaudio = new ArrayList<>();

    @BindView(R.id.txtIndoJudul)
    TextView txtIndoJudul;
    @BindView(R.id.spinnerSurat)
    Spinner spinnerSurat;
    @BindView(R.id.txtAyatJudul)
    TextView txtAyatJudul;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    List<String> stringList = new ArrayList<>();
    @BindView(R.id.llPilihSurah)
    LinearLayout llPilihSurah;

    AdapterAllSurah adapterAllSurah;
    @BindView(R.id.txtSelectSurah)
    TextView txtSelectSurah;
    @BindView(R.id.favSurah)
    ImageView favSurah;
    @BindView(R.id.animation_love)
    LottieAnimationView animationLove;
    FavoriteList favoriteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_quran_read);
        ButterKnife.bind(this);

        dataItem = getIntent().getParcelableExtra("data");
        favoriteList = getIntent().getParcelableExtra("dataFav");

        initialRead();


    }

    private void initialRead() {
        if (dataItem != null) {
            getReadAlquran(String.valueOf(dataItem.getNumber()));
            if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(dataItem.getNumber()) == 1)
                favSurah.setImageResource(R.drawable.ic_favorite_black_24dp);
            else
                favSurah.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        } else if (favoriteList != null) {
            getReadAlquran(String.valueOf(favoriteList.getId()));
            if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(Integer.parseInt(favoriteList.getNumber())) == 1)
                favSurah.setImageResource(R.drawable.ic_favorite_black_24dp);
            else
                favSurah.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }

//        initSpinner();


        llPilihSurah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialogSurah();
            }
        });

        favSurah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteList favoriteList = new FavoriteList();
                favoriteList.setId(dataItem.getNumber());
                favoriteList.setAyat(dataItem.getName());
                favoriteList.setSurah(dataItem.getEnglishName());
                favoriteList.setNumber(String.valueOf(dataItem.getNumberOfAyahs()));


                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(favoriteList.getId()) != 1) {
                    favSurah.setImageResource(R.drawable.ic_favorite_black_24dp);
                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);
                } else {
                    favSurah.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);
                }

            }
        });

//        animationLove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FavoriteList favoriteList = new FavoriteList();
//                favoriteList.setId(favoriteList.getId());
//                favoriteList.setAyat(dataItem.getName());
//                favoriteList.setSurah(dataItem.getEnglishName());
//                getFav(true, favoriteList, favoriteList.getId());
//            }
//        });


    }

//    void getFav(boolean isLove, FavoriteList favoriteList, int id) {
//        if (isLove) {
//            if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id) != 1) {
//                MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);
//                Log.d("responSave", " yeess ");
//                animationLove.setAnimation("lova_popup.json");
//                animationLove.playAnimation();
//            } else {
//                animationLove.setAnimation("lova_popup.json");
//                animationLove.cancelAnimation();
//                MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);
//            }
//        } else {
//
//        }
//
//    }


    private void popupDialogSurah() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_spinner_surah, null);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view);
        dialog.show();

        RecyclerView rvSpinner = dialog.findViewById(R.id.rvSpinnerSurah);
        ImageView imgClose = dialog.findViewById(R.id.imgCloseSpinner);

        getDataSurah(rvSpinner, dialog);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void getDataSurah(RecyclerView recyclerView, final Dialog dialog) {

        if (dataItem != null) {
            if (dataItem.getName() != null) {
                txtIndoJudul.setText(dataItem.getEnglishName());
                txtAyatJudul.setText("(" + dataItem.getName() + ")");
            }
        } else if (favoriteList != null) {
            if (favoriteList.getSurah() != null) {
                txtIndoJudul.setText(favoriteList.getSurah());
                txtAyatJudul.setText("(" + favoriteList.getAyat() + ")");
            }
        }


        adapterAllSurah = new AdapterAllSurah(this, AlquranFragment.surahList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterAllSurah);

        adapterAllSurah.Onclick(new AdapterAllSurah.onClickRead() {
            @Override
            public void clickDetail(View view, int pos, DataItem dataItem) {
                dialog.dismiss();
                txtSelectSurah.setText(dataItem.getEnglishName() + " (" + dataItem.getName() + ")");
                getReadAlquran(String.valueOf(dataItem.getNumber()));
                txtIndoJudul.setText(dataItem.getEnglishName());
                txtAyatJudul.setText("(" + dataItem.getName() + ")");
            }
        });

    }

    private void initSpinner() {
        for (int i = 0; i < AlquranFragment.surahList.size(); i++) {
            stringList.add(AlquranFragment.surahList.get(i).getEnglishName() + " (" + AlquranFragment.surahList.get(i).getName() + ")");
            txtIndoJudul.setText(dataItem.getEnglishName());
            txtAyatJudul.setText("(" + dataItem.getName() + ")");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSurat.setAdapter(adapter);
        spinnerSurat.setPrompt("Pilih Surah");

        spinnerSurat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = adapterView.getItemAtPosition(i).toString();
//                    Toast.makeText(getApplicationContext(), "Select " + selectedName, Toast.LENGTH_LONG).show();
                getReadAlquran(selectedName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void getReadAlquran(String idquran) {
        progressBarRead.setVisibility(View.VISIBLE);
        rvDetailRead.setVisibility(View.GONE);
        playAudio.setVisibility(View.GONE);
        Retrofit retrofit = ServiceApiClient.getApiAlquran(this);
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseRead> call = apiService.getReadDetailQuran(idquran);

        call.enqueue(new Callback<ResponseRead>() {
            @Override
            public void onResponse(Call<ResponseRead> call, Response<ResponseRead> response) {
                if (response.isSuccessful()) {
                    itemReadLists = response.body().getData().getAyahs();
                    List<String> listRead = new ArrayList<>();


                    for (int i = 0; i < itemReadLists.size(); i++) {
                        listRead.add(itemReadLists.get(i).getText());
                        String[] getList = listRead.toArray(new String[0]);

                        Log.d("responQuran", " " + getList);

                    }


                    adapterDetailReadQuran = new AdapterDetailReadQuran(DetailQuranRead.this, itemReadLists);
                    rvDetailRead.setHasFixedSize(true);
                    rvDetailRead.setLayoutManager(new LinearLayoutManager(DetailQuranRead.this));
                    rvDetailRead.setAdapter(adapterDetailReadQuran);
                    rvDetailRead.setVisibility(View.VISIBLE);
                    progressBarRead.setVisibility(View.GONE);
                    playAudio.setVisibility(View.VISIBLE);

                    for (int i = 0; i < itemReadLists.size(); i++) {
                        listaudio.add(JcAudio.createFromURL("Ayat (" + itemReadLists.get(i).getNumberInSurah() + ")", itemReadLists.get(i).getAudio()));
                        playAudio.initPlaylist(listaudio, null);
                    }

//                    getDetailPage();

                    if (dataItem != null) {
                        txtIndoJudul.setText(dataItem.getEnglishName());
                        txtAyatJudul.setText("(" + dataItem.getName() + ")");
                        txtSelectSurah.setText(dataItem.getEnglishName() + " (" + dataItem.getName() + ")");
                    } else if (favoriteList != null){
                        txtIndoJudul.setText(favoriteList.getSurah());
                        txtAyatJudul.setText("(" + favoriteList.getAyat() + ")");
                        txtSelectSurah.setText(favoriteList.getSurah() + " (" + favoriteList.getAyat() + ")");
                    }


                } else {
                    progressBarRead.setVisibility(View.GONE);
                    response.body().getStatus();
                }

            }

            @Override
            public void onFailure(Call<ResponseRead> call, Throwable t) {
                t.getMessage();
                progressBarRead.setVisibility(View.GONE);

            }
        });
    }

    private void getDetailPage(String idPage) {
        Retrofit retrofit = ServiceApiClient.getApiAlquran(this);
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseRead> call = apiService.getPageQuran(idPage);

//        call.enqueue(new );
    }


    @Override
    protected void onPause() {
        super.onPause();
        playAudio.createNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playAudio.kill();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(JcStatus jcStatus) {

    }

    @Override
    public void onJcpError(Throwable throwable) {

    }

    @Override
    public void onPaused(JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(JcStatus jcStatus) {
    }

    @Override
    public void onPreparedAudio(JcStatus jcStatus) {

    }

    @Override
    public void onStopped(JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(JcStatus jcStatus) {
        Log.d("response d ", "Song duration = " + jcStatus.getDuration()
                + "\n song position = " + jcStatus.getCurrentPosition());
    }
}

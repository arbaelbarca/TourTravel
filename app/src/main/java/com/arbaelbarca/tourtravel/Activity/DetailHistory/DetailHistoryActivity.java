package com.arbaelbarca.tourtravel.Activity.DetailHistory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.PengajuanVendor;
import com.arbaelbarca.tourtravel.Adapter.AdapterAddJamaah;
import com.arbaelbarca.tourtravel.Adapter.AdapterHistoryTransaksi;
import com.arbaelbarca.tourtravel.Adapter.AdapterMemberHistory;
import com.arbaelbarca.tourtravel.Adapter.TabAdapter;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.DetailAllFragment;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FinishingPelunasan;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FragmentAnggotaJamaah;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FragmentPelunasan;
import com.arbaelbarca.tourtravel.Fragment.FragmentHistory;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.AnggotaJamaah;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPaketItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPenggunaItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.MemberAdditionItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.Utils.CustomViewPager;
import com.google.gson.Gson;
import com.shuhart.stepview.StepView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailHistoryActivity extends AppCompatActivity {


    TabLayout tabLayout;
    CustomViewPager viewPager;
    TabAdapter tabAdapter;
    Toolbar toolbar;

    public static ArrayList<InfoVendorItem> resultItemList;
    public static ArrayList<InfoPaketItem> infoPaketItems = new ArrayList<>();
    public static ArrayList<MemberAdditionItem> memberAdditionItems = new ArrayList<>();
    public static ArrayList<InfoPenggunaItem> infoPenggunaItems = new ArrayList<>();
    public static ResultItem dataItem;
    StepView stepViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);


        resultItemList = new ArrayList<>();

        resultItemList = (ArrayList<InfoVendorItem>) getIntent().getSerializableExtra("dataVendor");
        infoPaketItems = (ArrayList<InfoPaketItem>) getIntent().getSerializableExtra("dataPaket");
        memberAdditionItems = (ArrayList<MemberAdditionItem>) getIntent().getSerializableExtra("dataAnggota");
        infoPenggunaItems = (ArrayList<InfoPenggunaItem>) getIntent().getSerializableExtra("dataPengguna");

        dataItem = getIntent().getParcelableExtra("data");


        initTabLayout();
        initToolbar();
        initStepView();

    }

    private void initStepView() {
        stepViewDetail = findViewById(R.id.stepViewDetail);

        stepViewDetail.getState()
                .steps(new ArrayList<String>() {{
                    add("Pemesan");
                    add("Pelunasan");
                    add("Selesai");
                }})
                .animationDuration(200)
                .commit();


        stepViewDetail.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                if (step == 0) {
                    stepViewDetail.done(false);
                    stepViewDetail.go(step, true);
                    stepViewDetail.getState()
                            .selectedTextColor(Color.WHITE)
                            .commit();
                    chooseTab(0);
                } else if (step == 1) {
                    if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
                        chooseTab(1);
                        stepViewDetail.go(step, true);
                        stepViewDetail.getState()
                                .doneTextColor(Color.BLACK)
                                .selectedTextColor(Color.WHITE)
                                .commit();
                    } else {
                        if (FragmentHistory.item.getPhotoBukti().equals("")) {
                            Toast.makeText(getApplicationContext(), "Mohon maaf tidak bisa di klik, silahkan upload poto dp terlebih dahulu", Toast.LENGTH_LONG).show();
                        } else {
                            chooseTab(1);
                            stepViewDetail.go(step, true);
                            stepViewDetail.getState()
                                    .doneTextColor(Color.BLACK)
                                    .selectedTextColor(Color.WHITE)
                                    .commit();
                        }
                    }

                } else if (step == 2) {
                    if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
                        chooseTab(1);
                        stepViewDetail.go(step, true);
                        stepViewDetail.getState()
                                .doneTextColor(Color.BLACK)
                                .selectedTextColor(Color.WHITE)
                                .commit();
                    } else {
                        if (FragmentHistory.item.getPhoto_invoice().equals("")) {
                            Toast.makeText(getApplicationContext(), "Mohon maaf tidak bisa di klik, silahkan upload poto invoice terlebih dahulu", Toast.LENGTH_LONG).show();
                        } else {
                            chooseTab(2);
                            stepViewDetail.go(step, true);
                            stepViewDetail.getState()
                                    .doneTextColor(Color.BLACK)
                                    .selectedTextColor(Color.WHITE)
                                    .commit();
                        }
                    }

                }
            }
        });
    }

    private void initTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new DetailAllFragment(), "Detail Pemesan");
        if (memberAdditionItems.size() > 5) {
            tabAdapter.addFragment(new FragmentAnggotaJamaah(), "Detail Anggota");
        }
        tabAdapter.addFragment(new FragmentPelunasan(), "Detail Pelunasan");
        tabAdapter.addFragment(new FinishingPelunasan(), "Selesai");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void chooseTab(int position) {
        viewPager.setCurrentItem(position);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail History");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //
}

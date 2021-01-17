package com.arbaelbarca.tourtravel.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arbaelbarca.tourtravel.Adapter.TabAdapter;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.DetailAllFragment;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FinishingPelunasan;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FragmentAnggotaJamaah;
import com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment.FragmentPelunasan;
import com.arbaelbarca.tourtravel.Fragment.FragmentAlquran.AlquranFragment;
import com.arbaelbarca.tourtravel.Fragment.FragmentAlquran.FavoriteFragment;
import com.arbaelbarca.tourtravel.Fragment.FragmentAlquran.JuzFragment;
import com.arbaelbarca.tourtravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlquranHome extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter tabAdapter;

    public FragmentAlquranHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_toko, container, false);
        initialAll(view);
        return view;


    }

    private void initialAll(View view) {
        initTabLayout(view);

    }

    private void initTabLayout(View view) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager());
        tabAdapter.addFragment(new AlquranFragment(), "Alquran");
        tabAdapter.addFragment(new JuzFragment(), "Juz");
        tabAdapter.addFragment(new FavoriteFragment(), "Bookmark");


        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}

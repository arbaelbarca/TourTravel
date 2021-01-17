package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arbaelbarca.tourtravel.R;

public class MyPager extends PagerAdapter {
    Context context;

    public MyPager(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);

        ImageView imgPager = view.findViewById(R.id.imgPager);
        imgPager.setImageDrawable(container.getResources().getDrawable(getImageAt(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    private int getImageAt(int position) {
        switch (position) {
            case 0:
                return R.drawable.image1;
            case 1:
                return R.drawable.image2;
            case 2:
                return R.drawable.image3;
            default:
                return R.drawable.image2;
        }
    }
}

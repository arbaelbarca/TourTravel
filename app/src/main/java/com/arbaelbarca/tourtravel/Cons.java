package com.arbaelbarca.tourtravel;

import android.os.Environment;

import java.io.File;

public class Cons {
    public static String URL_Nearby = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static String Key_Maps = "AIzaSyBpih80dTMhfLqMwjBpeiaReGJ2qpfJu6E";
    public static String Url_admin = "http://vendor.appazzura.net/";
    public static String URL_JADWALSOLAT = "https://time.siswadi.com/";
    public static String URL_ALQURAN = "http://api.alquran.cloud/v1/";

    public static String VENDOR_AKUN_BIG = "Vendor";
    public static String VENDOR_AKUN_SMALL = "vendor";

    public static String ADMIN_AKUN_BIG = "Admin";
    public static String ADMIN_AKUN_SMALL = "admin";


    public static final String SCAN_IMAGE_LOCATION = Environment.getExternalStorageDirectory() + File.separator + "OpenScanner";

}

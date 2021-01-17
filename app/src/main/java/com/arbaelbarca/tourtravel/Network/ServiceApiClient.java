package com.arbaelbarca.tourtravel.Network;

import android.content.Context;

import com.arbaelbarca.tourtravel.Cons;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ServiceApiClient {

    public static Retrofit retrofit;

    public static Retrofit getApiListVendor(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .client(provideOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getApiJadwal(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.URL_JADWALSOLAT)
                .client(provideOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getApiAlquran(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.URL_ALQURAN)
                .client(provideOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit postPengajuan(Context context) {

        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.Url_admin)
                .client(provideOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private static OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(new ChuckerInterceptor(context))
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}

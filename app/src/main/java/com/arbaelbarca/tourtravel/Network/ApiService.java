package com.arbaelbarca.tourtravel.Network;


import com.arbaelbarca.tourtravel.Model.AdminUmroh.AdminResult;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelJuz.ResponseJuz;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ModelReadQuran.ResponseRead;
import com.arbaelbarca.tourtravel.Model.AlquranModel.ResponseSurah;
import com.arbaelbarca.tourtravel.Model.CheckoutOrderUmroh;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResponseHistory;
import com.arbaelbarca.tourtravel.Model.ModelJadwalSolat.ResponseJadwal;
import com.arbaelbarca.tourtravel.Model.ModelLIstPaket.ResponseListPaket;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ModelPenggunaProfile.ResponsePengguna;
import com.arbaelbarca.tourtravel.Model.ModelProfile.ResponseProfile;
import com.arbaelbarca.tourtravel.ResponseNearby.ResponsePlace;
import com.arbaelbarca.tourtravel.ReturnListPengajuan.ResponseListPengajuan;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    String key = "AAAA9g2DV9c:APA91bHi1l5CvpnYTKb4diMI2KiRvCK5hjm6RsDgScTTS51fMWp5-t8tXp-BcM81YL-p5TGrNVZbCAovAjBVxhYDufdCqOZBhWaACzJT59uemBNeh2AsQZ2LoGqIyrcCn9W0mhEEo1BI";

    @Headers({"Authorization: key=AAAA9g2DV9c:APA91bHi1l5CvpnYTKb4diMI2KiRvCK5hjm6RsDgScTTS51fMWp5-t8tXp-BcM81YL-p5TGrNVZbCAovAjBVxhYDufdCqOZBhWaACzJT59uemBNeh2AsQZ2LoGqIyrcCn9W0mhEEo1BI",
            "Content-Type:application/json"})
//    @POST("fcm/send")
//    Call<ResponseBody> sendChatNotification(@Body RequestNotificaton requestNotificaton);

//    @GET("tampil_berita.php")
//    Call<ResponseBerita> berita();

    @GET
    Call<ResponsePlace> getRadius(
            @Url String url,
            @Query("location") String lok,
            @Query("radius") String radius,
            @Query("sensor") String sensor,
            @Query("type") String type,
            @Query("key") String key

    );

    @GET
    Call<AdminResult> getAdmin(
            @Url String url,
            @Query("req") String read
    );

    @POST("req-pengguna.php?req=create")
    @FormUrlEncoded
    Call<ResponseBody> postPengguna(
            @FieldMap HashMap<String, String> userLog

    );

    @POST("req-vendor-pengaju.php?req=create")
    @FormUrlEncoded
    Call<ResponseBody> postPengajuanVendor(
            @FieldMap HashMap<String, String> userLog

    );

    @Multipart
    @POST("req-pengguna.php?req=update")
    Call<ResponseBody> postUpdateUser(
            @Part MultipartBody.Part file,
            @PartMap() Map<String, RequestBody> partMap
//            @PartMap HashMap<String, String> userLog

    );

    @Multipart
    @POST("req-file.php")
    Call<ResponseBody> postUpdateTestUri(
            @Part MultipartBody.Part file
    );

    @POST("req-pengguna.php?req=login")
    @FormUrlEncoded
    Call<ResponseProfile> getLogin(
            @FieldMap HashMap<String, String> userLog

    );

    @POST("req-pengguna.php?req=read")
    @FormUrlEncoded
    Call<ResponsePengguna> getDataProfile(
            @FieldMap HashMap<String, String> userLog

    );


    @POST("req-pengguna.php?req=read")
    @FormUrlEncoded
    Call<ResponsePengguna> getIdDataProfile(
            @FieldMap HashMap<String, String> userLog

    );

    @GET("req-vendor-pengaju.php?req=read")
    Call<ResponseListPengajuan> getListPengajuan(
    );


    @POST("req-vendor-paket.php?req=read")
    @FormUrlEncoded
    Call<ResponseListPaket> getListPaket(
            @FieldMap HashMap<String, String> userLog

    );

    @POST("req-vendor-paket.php?req=create")
    @FormUrlEncoded
    Call<ResponseBody> PostPaketVendor(
            @FieldMap HashMap<String, String> userLog

    );


    //    @Headers("Content-Type: application/json")

    //    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("req-vendor-pengaju.php?req=update")
    @FormUrlEncoded
    Call<ResponseBody> postUpdateSetujui(
            @FieldMap HashMap<String, String> listPengajuan

    );

    @POST("req-vendor-transaksi.php?req=create")
    @FormUrlEncoded
    Call<ResponseBody> postOrderCheckout(
            @FieldMap HashMap<String, String> listPengajuan

    );

    @POST("req-vendor-transaksi.php?req=read")
    @FormUrlEncoded
    Call<ResponseHistory> getListHistory(
            @FieldMap HashMap<String, String> hashMap
    );

    @GET("req-vendor-transaksi.php?req=read")
    Call<ResponseHistory> getListAdminHistory(
    );

    @POST("req-vendor-transaksi.php?req=update")
    @FormUrlEncoded
    Call<ResponseBody> postUpdateHistory(
            @FieldMap HashMap<String, String> hashMap
    );

    @GET
    Call<ResponseJadwal> getJadwalSolat(
            @Url String url,
            @Query("lat") String lat,
            @Query("lng") String lng,
//            @Query("address") String address,
            @Query("datetime") String datetime

    );

    //Alquran Api

    @GET("surah")
    Call<ResponseSurah> getListAllSurah(
    );

    @GET("juz/{number}/id.indonesian")
    Call<ResponseJuz> getLisJuzKategori(
            @Path("number") String number
    );

    @GET("surah/{id_quran}/ar.alafasy")
    Call<ResponseRead> getReadDetailQuran(
            @Path("id_quran") String idQuran
    );

    @GET("page/{id_page}/quran-uthmani")
    Call<ResponseRead> getPageQuran(
            @Path("id_page") String idPage
    );
}

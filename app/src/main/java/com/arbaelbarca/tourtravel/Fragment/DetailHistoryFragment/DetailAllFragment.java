package com.arbaelbarca.tourtravel.Fragment.DetailHistoryFragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.CreateInvoice;
import com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity;
import com.arbaelbarca.tourtravel.Adapter.AdapterMemberHistory;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.Fragment.FragmentHistory;
import com.arbaelbarca.tourtravel.MainActivity;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPaketItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoPenggunaItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.InfoVendorItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.MemberAdditionItem;
import com.arbaelbarca.tourtravel.Model.HistoryTransaksi.ResultItem;
import com.arbaelbarca.tourtravel.Network.ApiService;
import com.arbaelbarca.tourtravel.Network.ServiceApiClient;
import com.arbaelbarca.tourtravel.R;
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

import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPaketItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.infoPenggunaItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.memberAdditionItems;
import static com.arbaelbarca.tourtravel.Activity.DetailHistory.DetailHistoryActivity.resultItemList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailAllFragment extends Fragment {


    TextView txtNamaPaket, txtHargaPaket, txtDeskripsiPaket, txtNoAnggota;


    TextView txtName, txtPhone, txtAlamat, txtEmail;

    RecyclerView rvListAnggota;

    int getPosition = 0;
    AdapterMemberHistory adapterAddJamaah;
    String getNameVendor;
    ResultItem resultItem;
    ImageView imgStruk, imgGetStruk;

    ArrayList<? extends InfoVendorItem> infoVendorItems = new ArrayList<>();
    InputStream imageStream;
    String logBase64;
    TextView txtKetUpload, txtStatus, txtDetailAdminTitle;
    RelativeLayout rlSimpan, rlTolak, rlSetujui, rlTablayout;
    ProgressDialog progressDialog;

    LinearLayout llBtnAdmin;
    FloatingActionButton createInvoice;
    EasyImage easyImage;
    private int GALLERY = 1, CAMERA = 111;
    Uri imageUri, imageCamera;
    Bitmap thumbnail;

    public DetailAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_all, container, false);

        Log.d("responGetadat", " " + infoPaketItems);

        initial(view);
        return view;
    }


    private void initial(View view) {

        progressDialog = new ProgressDialog(getActivity());
        imgStruk = view.findViewById(R.id.imgStrukHistory);
        txtNoAnggota = view.findViewById(R.id.txtNoAnggota);
        createInvoice = view.findViewById(R.id.floatCreateInvoice);
        txtName = view.findViewById(R.id.txtNamaVendor);
        txtPhone = view.findViewById(R.id.txtPhoneVendor);
        txtAlamat = view.findViewById(R.id.txtEmail);
        txtEmail = view.findViewById(R.id.txtAlamatVendor);
        rvListAnggota = view.findViewById(R.id.rvList);
        txtKetUpload = view.findViewById(R.id.txtKetUploadStruk);
        txtStatus = view.findViewById(R.id.txtStatusHistory);
        rlSimpan = view.findViewById(R.id.rlSimpanStruk);
        imgGetStruk = view.findViewById(R.id.imgStruk);
        txtDetailAdminTitle = view.findViewById(R.id.txtTitleDetailAdmin);
        llBtnAdmin = view.findViewById(R.id.llbtnAdmin);
        rlSetujui = view.findViewById(R.id.rlSetujui);
        rlTolak = view.findViewById(R.id.rlTolak);
        rlTablayout = view.findViewById(R.id.rlTabLayou);

        txtName.setText(resultItemList.get(0).getNamaVendor());
        txtPhone.setText(resultItemList.get(0).getNoTlp());
        txtAlamat.setText(resultItemList.get(0).getAlamatVendor());
        txtEmail.setText(resultItemList.get(0).getEmail());

        txtNamaPaket = view.findViewById(R.id.txtNamaPaket);
        txtHargaPaket = view.findViewById(R.id.txtPricePaket);
        txtDeskripsiPaket = view.findViewById(R.id.txtDeskripsiPaket);

        txtNamaPaket.setText(infoPaketItems.get(0).getNamaPaket());
        txtHargaPaket.setText("Rp " + infoPaketItems.get(0).getHargaPaket());
        txtDeskripsiPaket.setText(infoPaketItems.get(0).getDeskripsiPaket());
        txtDetailAdminTitle.setText("Pemesan : " + infoPenggunaItems.get(0).getNamaLengkap());

        if (MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_BIG) || MainActivity.getAdminUser.equalsIgnoreCase(Cons.VENDOR_AKUN_SMALL)) {
            txtDetailAdminTitle.setVisibility(View.VISIBLE);
            rlSimpan.setVisibility(View.GONE);
            llBtnAdmin.setVisibility(View.VISIBLE);

            rlSetujui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData("", FragmentHistory.item.getId(), "confirm");
                }
            });

            rlTolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData("", FragmentHistory.item.getId(), "no confirm");
                }
            });
        }

        if (FragmentHistory.item.getStatusKonfirmasi().equals("confirm")) {
            txtStatus.setText("Diterima");
        } else if (FragmentHistory.item.getStatusKonfirmasi().equals("no confirm")) {
            txtStatus.setText("Ditolak");
        } else {
            txtStatus.setText("Direview");
        }

        if (memberAdditionItems.size() > 0) {
            adapterAddJamaah = new AdapterMemberHistory(getActivity(), memberAdditionItems);
            rvListAnggota.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvListAnggota.setHasFixedSize(true);
            rvListAnggota.setVisibility(View.VISIBLE);
            txtNoAnggota.setVisibility(View.GONE);
            rvListAnggota.setAdapter(adapterAddJamaah);
        } else {
            txtNoAnggota.setVisibility(View.VISIBLE);
        }

        if (FragmentHistory.item.getPhotoBukti().equals("")) {
            imgStruk.setVisibility(View.VISIBLE);
            txtKetUpload.setVisibility(View.VISIBLE);
            rlSimpan.setVisibility(View.VISIBLE);
        } else {
            imgGetStruk.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(FragmentHistory.item.getPhotoBukti())
                    .into(imgGetStruk);
        }

        imgStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uploadStruk();
                takeCamera();
            }
        });

        rlSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(logBase64, FragmentHistory.item.getId(), "");
            }
        });

        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buatInvoice();
            }
        });


    }

    private void buatInvoice() {
        Intent intent = new Intent(getActivity(), CreateInvoice.class);
        startActivity(intent);
    }


    private void updateData(String poto, String id, String confirm) {
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        Retrofit retrofit = ServiceApiClient.getApiListVendor(getActivity());
        ApiService apiService = retrofit.create(ApiService.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("photo_bukti", poto);
        hashMap.put("id", id);

        if (!confirm.equals("")) {
            hashMap.put("status_konfirmasi", confirm);
        }
        Call<ResponseBody> call = apiService.postUpdateHistory(hashMap);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getMessage();
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    private void uploadStruk() {
        final CharSequence sequence[] = new CharSequence[]{"Camera", "Galery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Pilih Upload");
        dialog.setItems(sequence, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sequence[which].toString().equals("Camera")) {
                    EasyImage.openCamera(getActivity(),0);
                } else {
                    EasyImage.openGallery(getActivity(), 0);
                }

            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
//            @Override
//            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
//                File imageFile = imageFiles.get(0);
//                try {
//                    File compressedImageFile = new Compressor(getActivity()).compressToFile(imageFile);
//                    if (compressedImageFile != null) {
//                        File ba1 = compressedImageFile;
////                        photoPath = compressedImageFile.getAbsolutePath();
//                        Uri imageCamera = Uri.fromFile(ba1);
//                        try {
//                            imageStream = getActivity().getContentResolver().openInputStream(imageCamera);
//                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                            Picasso.with(getActivity()).load(ba1).into(imgStruk);
//                            logBase64 = encodeImage(selectedImage);
//                            txtKetUpload.setVisibility(View.GONE);
//                            imgStruk.setVisibility(View.VISIBLE);
//                            imgGetStruk.setVisibility(View.GONE);
//                            Log.d("responBase64", " " + logBase64 + " uri "+imageCamera);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                            Log.d("responBase64", "ee " + e.getMessage());
//
//                        }
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.d("responBase64", "ee " + e.getMessage());
//
//                }
//
//
//            }
//        });

        if (requestCode == CAMERA) {
            imageCamera = data.getData();
            if (imageCamera != null) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                imgStruk.setImageBitmap(thumbnail);

                try {
                    imageStream = getActivity().getContentResolver().openInputStream(imageCamera);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgStruk.setImageBitmap(thumbnail);
                    imgStruk.setVisibility(View.VISIBLE);
                    imgGetStruk.setVisibility(View.GONE);
                    logBase64 = encodeImage(selectedImage);
                    Log.d("responBase ", " B " + logBase64);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imagByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagByte, Base64.DEFAULT);
    }

    void takeCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

}

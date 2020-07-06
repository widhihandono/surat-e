package com.surat.surate_app;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.surat.surate_app.Adapter.Jenis_Dokumen_Adapter;
import com.surat.surate_app.Adapter.List_Dokumen_Adapter;
import com.surat.surate_app.Adapter.MyAdapter;
import com.surat.surate_app.Adapter.MyAdapter_1;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_User;
import com.surat.surate_app.Model.Ent_cekImei;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.Model.Ent_sifat_surat;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Model.Ent_total_surat;
import com.surat.surate_app.Model.onMenuClick;
import com.surat.surate_app.SQLite.Crud;
import com.surat.surate_app.Util.NotificationUtils;
import com.surat.surate_app.Util.SharedPref;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Utama_Activity extends AppCompatActivity implements Jenis_Dokumen_Adapter.JenisAdapterCallback {
Ent_sifat_surat mEntsifatsurat;
private RecyclerView.LayoutManager layoutManager;
private List_Dokumen_Adapter listDokumenAdapter;
private Api_Interface api_interface;
SharedPref sharedPref;
int height=0,width=0;
TextView version,tvLogout,tvLoadMore,tvUbahPass;
Snackbar bar;
    RecyclerView recyclerView_1,mRecyclerView,rvDocument;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};

    int permsRequestCode = 200;
    SwipeRefreshLayout swLayout;
    Crud crudSqlite;

    String id_jenis="1";

    List<Ent_surat> pagingSurat = new ArrayList<>();
    int offset = 0,row_count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__utama_);
        getSupportActionBar().hide();

        FirebaseApp.initializeApp(this);
        sharedPref = new SharedPref(this);
        api_interface = Api_Class.getClient().create(Api_Interface.class);
        crudSqlite = new Crud(this);


        mRecyclerView = findViewById(R.id.recyclerview);
        recyclerView_1 = findViewById(R.id.recyclerview_1);
        rvDocument = findViewById(R.id.rvDocument);
        layoutManager = new LinearLayoutManager(this);
        rvDocument.setLayoutManager(layoutManager);
        swLayout = findViewById(R.id.swLayout);
        version = findViewById(R.id.version);
        tvLogout = findViewById(R.id.tvLogout);
        tvLoadMore = findViewById(R.id.tvLoadMore);
        tvUbahPass = findViewById(R.id.tvUbahPass);

        version.setText("1.7");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        if(sharedPref.sp.getBoolean(SharedPref.SP_SUDAH_LOGIN,false) == false)
        {
            startActivity(new Intent(Menu_Utama_Activity.this,Login_Activity.class));
            finish();
        }

        FirebaseMessaging.getInstance().subscribeToTopic(sharedPref.sp.getString("role",""));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }


        cek_imei();
        cek_versi();
//        showDialogCekImei(getUniqueIMEIId());
        GridLayoutManager mGridLayoutManager_1 = new GridLayoutManager(Menu_Utama_Activity.this, 4);
        recyclerView_1.setLayoutManager(mGridLayoutManager_1);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(Menu_Utama_Activity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        show_total();
        show_documents(offset,row_count);


        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.StopNotificationSound();

        swLayout.setColorSchemeResources(R.color.colorPrimary);

        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swLayout.setRefreshing(false);
                        show_total();
                        show_documents(0,10);
                        finish();
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);
                    }
                },4000);
            }
        });


        tvLogout.setOnClickListener(l->{
            showDialogLogout();
        });

        tvLoadMore.setOnClickListener(l->{
            offset = offset + 10;
            show_documents(offset,row_count);
        });

        tvUbahPass.setOnClickListener(l->{
            ChangePass();
        });

    }

    public void ChangePass() {
        LayoutInflater layoutInflater= this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_edit_text_dialog, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Ganti Password");

        alertDialog.setCancelable(false);

        CheckBox cbPassLama = view.findViewById(R.id.cbPassLama);
        EditText etOldPass = (EditText) view.findViewById(R.id.etOldPass);

        CheckBox cbPassBaru = view.findViewById(R.id.cbPassBaru);
        EditText etNewPass = (EditText) view.findViewById(R.id.etNewPass);

        cbPassLama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    etOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        cbPassBaru.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    etNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Ganti", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Call<Ent_User> changePass = api_interface.Change_pass(sharedPref.sp.getString("username",""),etOldPass.getText().toString(),etNewPass.getText().toString());
                changePass.enqueue(new Callback<Ent_User>() {
                    @Override
                    public void onResponse(Call<Ent_User> call, Response<Ent_User> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().getResponse() == 1)
                            {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(sharedPref.sp.getString("role",""));
                                sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,false);
                                sharedPref.saveSPString("role","");
                                sharedPref.saveSPString("username","");
                                startActivity(new Intent(Menu_Utama_Activity.this,Login_Activity.class));
                                finish();
                            }
                            else if(response.body().getResponse() == 0)
                            {
                                showSnackbar_text(response.body().getPesan());
                            }
                            else if(response.body().getResponse() == 2)
                            {
                                showDialogCekImei(response.body().getPesan());
                            }
                            else if(response.body().getResponse() == 3)
                            {
                                showSnackbar_text(response.body().getPesan());
                            }

                        }
                        else
                        {
                            showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                        }
                    }

                    @Override
                    public void onFailure(Call<Ent_User> call, Throwable t) {
                        showSnackbar("Mohon maaf, sedang ada gangguan pada server. Tunggu beberapa saat lagi","Refresh");
                    }
                });

                dialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }


    private void show_documents(int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("bupati"))
        {
            Call<List<Ent_surat>> callDocuments = api_interface.show_all_dokumen_before_disposisi(offset, row_count);
            callDocuments.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().size() == 0 || response.body().size() < row_count)
                        {
                            tvLoadMore.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            tvLoadMore.setVisibility(View.VISIBLE);
                        }

                        pagingSurat.addAll(response.body());
                        listDokumenAdapter = new List_Dokumen_Adapter(Menu_Utama_Activity.this,pagingSurat);
                        rvDocument.setAdapter(listDokumenAdapter);
                    }
                    else
                    {
                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                    }
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.e("menu_utama","show documents");
                    showSnackbar("Network failed","Restart");
                }
            });
        }

    }

    private void cek_imei()
    {
        Call<Ent_cekImei> callCekImei = api_interface.cekImei(getUniqueIMEIId());
        callCekImei.enqueue(new Callback<Ent_cekImei>() {
            @Override
            public void onResponse(Call<Ent_cekImei> call, Response<Ent_cekImei> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 2)
                    {
                        showDialogCekImei(response.body().getPesan());
                    }
                    else
                    {
                        if(response.body().getResponse() == 1)
                        {
                            Log.i("Cek_imei","Lanjutkan");
                        }
                        else
                        {
                            showDialogCekImei(response.body().getPesan());
                        }
                    }

                }
                else
                {
                    showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                }
            }

            @Override
            public void onFailure(Call<Ent_cekImei> call, Throwable t) {
                Log.e("menu_utama","show documents");
                showSnackbar("Network failed","Restart");
            }
        });
    }

    private void show_total()
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<Ent_total_surat> callTotal = api_interface.showTotalSurat("3");
            callTotal.enqueue(new Callback<Ent_total_surat>() {
                @Override
                public void onResponse(Call<Ent_total_surat> call, Response<Ent_total_surat> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResponse() == 2)
                        {
                            showDialogCekImei(response.body().getPesan());
                        }
                        else
                        {
                            List<Ent_sifat_surat> mSifatSuratList_1 = new ArrayList<>();
                            if(sharedPref.sp.getString("role","ajudan").equals("ajudan"))
                            {
                                mEntsifatsurat = new Ent_sifat_surat(7,"Diteruskan",response.body().getSudah_diteruskan(),
                                        R.drawable.pen_white_32px,"#802299","sudah_diteruskan");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(8,"Terdisposisi",response.body().getDispsosi(),
                                        R.drawable.pen_white_32px,"#802299","terdisposisi");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(9,"Surat Masuk",response.body().getBefore_disposisi(),
                                        R.drawable.belum_dispo_not_filled_32px,"#3E51B1","surat_masuk");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(10,"Ditolak",response.body().getDitolak_bupati(),
                                        R.drawable.ditolak,"#673BB7","ditolak");
                                mSifatSuratList_1.add(mEntsifatsurat);

                                MyAdapter_1 myAdapter_1 = new MyAdapter_1(Menu_Utama_Activity.this, mSifatSuratList_1);
                                recyclerView_1.setAdapter(myAdapter_1);
                            }
                            else
                            {
                                mEntsifatsurat = new Ent_sifat_surat(7,"Terdisposisi Belum Kirim",crudSqlite.getData_surat().size(),
                                        R.drawable.pen_white_32px,"#802299","belum_kirim");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(8,"Terdisposisi",response.body().getDispsosi(),
                                        R.drawable.pen_white_32px,"#802299","terdisposisi");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(9,"Belum disposisi",response.body().getBefore_disposisi(),
                                        R.drawable.belum_dispo_not_filled_32px,"#3E51B1","belum_disposisi");
                                mSifatSuratList_1.add(mEntsifatsurat);
                                mEntsifatsurat = new Ent_sifat_surat(10,"Total",response.body().getTotal(),
                                        R.drawable.mail,"#673BB7","total");
                                mSifatSuratList_1.add(mEntsifatsurat);

                                MyAdapter_1 myAdapter_1 = new MyAdapter_1(Menu_Utama_Activity.this, mSifatSuratList_1);
                                recyclerView_1.setAdapter(myAdapter_1);
                            }


                            //==============================Bawah====================================================================

                            List<Ent_sifat_surat> mSifatSuratList = new ArrayList<>();
                            mEntsifatsurat = new Ent_sifat_surat(1,"Amat Segera",response.body().getAmat_segera(),
                                    R.drawable.amat_segera_32px,"#673BB7","Amat_Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(2,"Segera",response.body().getSegera(),
                                    R.drawable.bell,"#FF453C","Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(3,"Rahasia",response.body().getRahasia(),
                                    R.drawable.mail,"#3E51B1","Rahasia");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(4,"Penting",response.body().getPenting(),
                                    R.drawable.rahasia_32px,"#D81B60","Penting");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(5,"Biasa",response.body().getBiasa(),
                                    R.drawable.bukan_rahasia_32px,"#033C70","Biasa");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(6,"Tembusan",response.body().getTembusan(),
                                    R.drawable.bell,"#096459","Tembusan");
                            mSifatSuratList.add(mEntsifatsurat);



                            MyAdapter myAdapter = new MyAdapter(Menu_Utama_Activity.this, mSifatSuratList);
                            mRecyclerView.setAdapter(myAdapter);
                        }

                    }
                    else
                    {
                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                    }


                }

                @Override
                public void onFailure(Call<Ent_total_surat> call, Throwable t) {
                    Log.e("menu_utama","Gagal koneksi");
                    showSnackbar("Network failed","Restart");
                }
            });
        }
        else
        {
            Call<Ent_total_surat> callTotal = api_interface.showTotalSurat("4");
            callTotal.enqueue(new Callback<Ent_total_surat>() {
                @Override
                public void onResponse(Call<Ent_total_surat> call, Response<Ent_total_surat> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResponse() == 2)
                        {
                            showDialogCekImei(response.body().getPesan());
                        }
                        else
                        {
                            List<Ent_sifat_surat> mSifatSuratList_1 = new ArrayList<>();
                            mEntsifatsurat = new Ent_sifat_surat(7,"Terdisposisi Belum Kirim",crudSqlite.getData_surat().size(),
                                    R.drawable.pen_white_32px,"#802299","belum_kirim");
                            mSifatSuratList_1.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(8,"Terdisposisi",response.body().getDispsosi(),
                                    R.drawable.pen_white_32px,"#802299","terdisposisi");
                            mSifatSuratList_1.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(9,"Belum disposisi",response.body().getBefore_disposisi(),
                                    R.drawable.belum_dispo_not_filled_32px,"#3E51B1","belum_disposisi");
                            mSifatSuratList_1.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(10,"Total",response.body().getTotal(),
                                    R.drawable.mail,"#673BB7","total");
                            mSifatSuratList_1.add(mEntsifatsurat);

                            MyAdapter_1 myAdapter_1 = new MyAdapter_1(Menu_Utama_Activity.this, mSifatSuratList_1);
                            recyclerView_1.setAdapter(myAdapter_1);

                            //==============================Bawah====================================================================

                            List<Ent_sifat_surat> mSifatSuratList = new ArrayList<>();
                            mEntsifatsurat = new Ent_sifat_surat(1,"Amat Segera",response.body().getAmat_segera(),
                                    R.drawable.amat_segera_32px,"#673BB7","Amat_Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(2,"Segera",response.body().getSegera(),
                                    R.drawable.bell,"#FF453C","Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(3,"Rahasia",response.body().getRahasia(),
                                    R.drawable.rahasia_32px,"#3E51B1","Rahasia");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(4,"Penting",response.body().getPenting(),
                                    R.drawable.mail,"#D81B60","Penting");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(5,"Biasa",response.body().getBiasa(),
                                    R.drawable.bukan_rahasia_32px,"#033C70","Biasa");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(6,"Tembusan",response.body().getTembusan(),
                                    R.drawable.bell,"#096459","Tembusan");
                            mSifatSuratList.add(mEntsifatsurat);



                            MyAdapter myAdapter = new MyAdapter(Menu_Utama_Activity.this, mSifatSuratList);
                            mRecyclerView.setAdapter(myAdapter);
                        }

                    }
                    else
                    {
                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                    }


                }

                @Override
                public void onFailure(Call<Ent_total_surat> call, Throwable t) {
                    Log.e("menu_utama","Gagal koneksi");
                    showSnackbar("Network failed","Restart");
                }
            });
        }

    }


    private void showDialogLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin ingin Logout ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk Logout")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(sharedPref.sp.getString("role",""));
                        sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,false);
                        sharedPref.saveSPString("role","");
                        sharedPref.saveSPString("username","");

                        startActivity(new Intent(Menu_Utama_Activity.this,Login_Activity.class));

                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }


    private void cek_versi()
    {
        Call<Ent_cekImei> callCekVersi = api_interface.cek_versi();

        callCekVersi.enqueue(new Callback<Ent_cekImei>() {
            @Override
            public void onResponse(Call<Ent_cekImei> call, Response<Ent_cekImei> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 2)
                    {
                        showDialogCekImei(response.body().getPesan());
                    }
                    else
                    {
                        if(Float.parseFloat(response.body().getVersi()) > 1.7)
                        {
                            showDialogCekVersion(response.body().getPesan());
                        }
                        else
                        {
                            Log.i("version","Memenuhi");
                        }
                    }

                }
                else
                {
                    showSnackbar("Mohon maaf,ada gangguan pada server","Refresh");
                    Toast.makeText(Menu_Utama_Activity.this,"Mohon maaf,ada gangguan pada server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Ent_cekImei> call, Throwable t) {
                showSnackbar("Mohon maaf,ada gangguan pada server","Refresh");
                Toast.makeText(Menu_Utama_Activity.this,"Network failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showSnackbar(String text, String action)
    {

        bar = Snackbar.make(findViewById(R.id.sb_menu_utama),text, Snackbar.LENGTH_INDEFINITE);
        bar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
                startActivity(new Intent(Menu_Utama_Activity.this,Menu_Utama_Activity.class));
                finish();
            }
        });
        bar.show();

    }

    private void showSnackbar_text(String text)
    {

        bar = Snackbar.make(findViewById(R.id.sb_menu_utama),text, Snackbar.LENGTH_INDEFINITE);
        bar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
            }
        });
        bar.show();

    }

    @Override
    public void onBackPressed() {
        showDialogKeluar();
    }

    private void showDialogCekVersion(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Lanjutkan Download",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://edisposisi.magelangkab.go.id/download")));
                        finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }


    private void showDialogCekImei(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    private void showDialogKeluar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin ingin keluar ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk keluar")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                      finishAffinity();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    public String getUniqueIMEIId() {

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }
        String imei = telephonyManager.getDeviceId();
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.e("imei", "=" + imei);
        Log.e("android_id", "=" + android_id);
        if (imei != null && !imei.isEmpty()) {
            return imei;
        } else {

            return android_id;
        }
    }


    @Override
    public void onRowJenisDokumenClicked(String id_jenis_dokumen) {
        id_jenis = id_jenis_dokumen;
        show_total();
        show_documents(0,3);
    }
}

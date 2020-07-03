package com.surat.surate_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Penolakan_Bupati_Activity extends AppCompatActivity {
Api_Interface api_interface;
private TextView tvDitolak;
private EditText etAlasan;
SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penolakan__bupati_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Penolakan Dokumen");

        api_interface = Api_Class.getClient().create(Api_Interface.class);
        sharedPref = new SharedPref(this);

        tvDitolak = findViewById(R.id.tvDitolak);
        etAlasan = findViewById(R.id.etAlasan);

        tvDitolak.setOnClickListener(l->{
            showDialog_Penolakan();
        });


    }

    private void tolak_dokumen(int id_disposisi,String alasan,String status)
    {
        Call<Ent_surat> tolak_dokumen = api_interface.tolak_dokumen(id_disposisi,alasan,status);
        tolak_dokumen.enqueue(new Callback<Ent_surat>() {
            @Override
            public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 1)
                    {
                        Toast.makeText(Penolakan_Bupati_Activity.this,"Dokumen berhasil ditolak",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Penolakan_Bupati_Activity.this,Menu_Utama_Activity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Penolakan_Bupati_Activity.this,"Dokumen gagal ditolak",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Penolakan_Bupati_Activity.this,"Internal Server Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Ent_surat> call, Throwable t) {
                Toast.makeText(Penolakan_Bupati_Activity.this,"Network Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDialog_Penolakan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin Ingin Menolak Dokumen ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk Tolak Dokumen")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {

                        if(sharedPref.sp.getString("role","").equals("ajudan"))
                        {
                            tolak_dokumen(getIntent().getExtras().getInt("id_disposisi"),etAlasan.getText().toString(),"8");
                        }
                        else
                        {
                            tolak_dokumen(getIntent().getExtras().getInt("id_disposisi"),etAlasan.getText().toString(),"7");
                        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

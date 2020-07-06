package com.surat.surate_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.surat.surate_app.Adapter.TabPager_Terdisposisi_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Fragment.Fg_disposisi_terdisposisi;
import com.surat.surate_app.Fragment.Fg_dokumen_terdisposisi;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.NotSwipableViewPager;

import java.io.File;

import retrofit2.Call;

public class Detail_Dokumen_Terdisposisi_Activity extends AppCompatActivity implements Fg_disposisi_terdisposisi.OnFragmentInteractionListener,
        Fg_dokumen_terdisposisi.OnFragmentInteractionListener {
PDFView pdfView;
String encode;
View.OnClickListener onClickListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__dokumen_);
        getSupportActionBar().setTitle("Detail Dokumen Terdisposisi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        pdfView = findViewById(R.id.pdfViewer);
//
//        pdfView.fromAsset("contoh.pdf").load();
        NotSwipableViewPager viewPager =  findViewById(R.id.pager);
        TabPager_Terdisposisi_Adapter myPagerAdapter = new TabPager_Terdisposisi_Adapter(getSupportFragmentManager());
        myPagerAdapter.addFragment(getIntent().getExtras().getString("dari"),
                                    getIntent().getExtras().getString("nomor_surat"),
                                    getIntent().getExtras().getString("tanggal_surat"),
                                    getIntent().getExtras().getString("perihal"),
                                    getIntent().getExtras().getString("tanggal_diterima"),
                                    getIntent().getExtras().getString("nomor_agenda"),
                                    getIntent().getExtras().getInt("id_disposisi"),
                                    getIntent().getExtras().getString("path_disposisi"),
                                    getIntent().getExtras().getString("path"),
                                    getIntent().getExtras().getString("path_file"),
                                    getIntent().getExtras().getString("status"),
                                    getIntent().getExtras().getInt("id_dokumen"),
                                    getIntent().getExtras().getString("tanggal_kirim_tu_umum"),
                                    getIntent().getExtras().getString("tanggal_kirim_tu_bupati"),
                                    getIntent().getExtras().getString("tanggal_kirim_bupati"),
                                    getIntent().getExtras().getString("time_dokumen"),
                                    getIntent().getExtras().getString("id_jenis_dokumen"),
                                    getIntent().getExtras().getString("jenis_dokumen"),
                                    getIntent().getExtras().getInt("id_sifat_dokumen"),
                                    getIntent().getExtras().getString("sifat_dokumen"),
                                    getIntent().getExtras().getString("image_disposisi")
                                    );
        myPagerAdapter.addFragment(getIntent().getExtras().getString("path_file"),getIntent().getExtras().getString("path"));
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}

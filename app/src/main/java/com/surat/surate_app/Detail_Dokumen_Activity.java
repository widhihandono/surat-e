package com.surat.surate_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.tabs.TabLayout;
import com.surat.surate_app.Adapter.TabPager_Adapter;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Fragment.Fg_disposisi;
import com.surat.surate_app.Fragment.Fg_dokumen;
import com.surat.surate_app.Util.NotSwipableViewPager;

public class Detail_Dokumen_Activity extends AppCompatActivity implements Fg_disposisi.OnFragmentInteractionListener,
        Fg_dokumen.OnFragmentInteractionListener {
PDFView pdfView;
String encode;
View.OnClickListener onClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__dokumen_);
        getSupportActionBar().setTitle("Detail Dokumen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        pdfView = findViewById(R.id.pdfViewer);
//
//        pdfView.fromAsset("contoh.pdf").load();
        NotSwipableViewPager viewPager =  findViewById(R.id.pager);
        TabPager_Adapter myPagerAdapter = new TabPager_Adapter(getSupportFragmentManager());
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
                                    getIntent().getExtras().getString("sifat_dokumen")
                                    );
        myPagerAdapter.addFragment(getIntent().getExtras().getString("path_file"),getIntent().getExtras().getString("path"));
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tolak,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Detail_Dokumen_Activity.this,Menu_Utama_Activity.class));
                finish();
                return true;
            case R.id.btnTolak:
                Intent intent = new Intent(this,Penolakan_Bupati_Activity.class);
                intent.putExtra("id_disposisi",getIntent().getExtras().getInt("id_disposisi",0));
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Detail_Dokumen_Activity.this,Menu_Utama_Activity.class));
        Animatoo.animateFade(Detail_Dokumen_Activity.this);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}

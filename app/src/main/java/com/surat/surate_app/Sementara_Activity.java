package com.surat.surate_app;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.surat.surate_app.Adapter.TabPager_sifat_jenis_Adapter;
import com.surat.surate_app.Fragment.fg_jenis;
import com.surat.surate_app.Fragment.fg_sifat;

public class Sementara_Activity extends AppCompatActivity implements fg_sifat.OnFragmentInteractionListener,
        fg_jenis.OnFragmentInteractionListener{
ViewPager vPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sementara_);
        getSupportActionBar().hide();

        vPager =  findViewById(R.id.pager);
        TabPager_sifat_jenis_Adapter myPagerAdapter = new TabPager_sifat_jenis_Adapter(getSupportFragmentManager());
        myPagerAdapter.addFragment();
        myPagerAdapter.addFragment();
        vPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(vPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

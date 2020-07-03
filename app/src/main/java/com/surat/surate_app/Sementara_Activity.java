package com.surat.surate_app;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.surat.surate_app.Adapter.TabPager_lokal_Adapter;
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
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(vPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

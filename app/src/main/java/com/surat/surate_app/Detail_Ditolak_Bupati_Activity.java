package com.surat.surate_app;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.surat.surate_app.Adapter.TabPager_Ditolak_Adapter;
import com.surat.surate_app.Adapter.TabPager_Terdisposisi_Adapter;
import com.surat.surate_app.Fragment.Fg_alasan;
import com.surat.surate_app.Fragment.Fg_dokumen;
import com.surat.surate_app.Fragment.Fg_dokumen_terdisposisi;
import com.surat.surate_app.Util.NotSwipableViewPager;

public class Detail_Ditolak_Bupati_Activity extends AppCompatActivity implements Fg_dokumen.OnFragmentInteractionListener,Fg_alasan.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__ditolak__bupati_);

        getSupportActionBar().setTitle("Detail Ditolak Bupati");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotSwipableViewPager viewPager =  findViewById(R.id.pager);
        TabPager_Ditolak_Adapter myPagerAdapter = new TabPager_Ditolak_Adapter(getSupportFragmentManager());
        myPagerAdapter.addFragment_ditolak(getIntent().getExtras().getInt("id_disposisi"),(getIntent().getExtras().getString("alasan_ditolak_bupati")));
        myPagerAdapter.addFragment(getIntent().getExtras().getString("path_file"),getIntent().getExtras().getString("path"));
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
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

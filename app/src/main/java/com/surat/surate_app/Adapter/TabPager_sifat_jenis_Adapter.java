package com.surat.surate_app.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.surat.surate_app.Fragment.Fg_disposisi_terdisposisi;
import com.surat.surate_app.Fragment.Fg_dokumen_terdisposisi;
import com.surat.surate_app.Fragment.fg_jenis;
import com.surat.surate_app.Fragment.fg_sifat;

public class TabPager_sifat_jenis_Adapter extends FragmentPagerAdapter {
    private String dari,nomor_surat,tanggal_surat,perihal,diterimaTgl,
            no_agenda,path_file,status,tgl_kirim_tu_umum,tgl_kirim_tu_bupati,
            tgl_kirim_bupati,time_dokumen,jenis_dokumen,sifat_dokumen,image_disposisi,id_jenis_dokumen,path_disposisi,path;
    private int id_disposisi,id_dokumen,id_sifat_dokumen;
    public TabPager_sifat_jenis_Adapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment()
    {
    }

    public void addFragment(String path_file,String path)
    {
        this.path_file = path_file;
        this.path = path;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: return fg_sifat.newInstance();
            case 1: return fg_jenis.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "Sifat Surat";
            case 1: return "Jenis Surat";
            default: return null;
        }
    }
}

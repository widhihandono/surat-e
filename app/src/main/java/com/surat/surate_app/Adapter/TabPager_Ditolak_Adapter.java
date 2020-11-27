package com.surat.surate_app.Adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.surat.surate_app.Fragment.Fg_alasan;
import com.surat.surate_app.Fragment.Fg_disposisi_terdisposisi;
import com.surat.surate_app.Fragment.Fg_dokumen;
import com.surat.surate_app.Fragment.Fg_dokumen_terdisposisi;

public class TabPager_Ditolak_Adapter extends FragmentPagerAdapter {
    private String dari,nomor_surat,tanggal_surat,perihal,diterimaTgl,
            no_agenda,path_file,status,tgl_kirim_tu_umum,tgl_kirim_tu_bupati,
            tgl_kirim_bupati,time_dokumen,jenis_dokumen,sifat_dokumen,image_disposisi,id_jenis_dokumen,path_disposisi,path,alasan_ditolak_bupati;
    private int id_disposisi,id_dokumen,id_sifat_dokumen;
    public TabPager_Ditolak_Adapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment_ditolak(int id_disposisi,String alasan_ditolak_bupati)
    {

        this.id_disposisi = id_disposisi;
        this.alasan_ditolak_bupati = alasan_ditolak_bupati;
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
            case 0: return Fg_alasan.newInstance(this.id_disposisi,this.alasan_ditolak_bupati);
            case 1: return Fg_dokumen.newInstance(this.path_file,this.path);
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
            case 0: return "Alasan Ditolak Bupati";
            case 1: return "Dokumen";
            default: return null;
        }
    }
}

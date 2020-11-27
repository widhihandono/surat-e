package com.surat.surate_app.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.surat.surate_app.Fragment.Fg_disposisi_terdisposisi;
import com.surat.surate_app.Fragment.Fg_dokumen_terdisposisi;

public class TabPager_Terdisposisi_Adapter extends FragmentPagerAdapter {
    private String dari,nomor_surat,tanggal_surat,perihal,diterimaTgl,
            no_agenda,path_file,status,tgl_kirim_tu_umum,tgl_kirim_tu_bupati,
            tgl_kirim_bupati,time_dokumen,jenis_dokumen,sifat_dokumen,image_disposisi,id_jenis_dokumen,path_disposisi,path;
    private int id_disposisi,id_dokumen,id_sifat_dokumen;
    public TabPager_Terdisposisi_Adapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(String dari,String nomor_surat,String tanggal_surat,
                            String perihal,String diterimaTgl,String no_agenda,
                            int id_disposisi,String path_disposisi,String path,String path_file,
                            String status,int id_dokumen,String tgl_kirim_tu_umum,
                            String tgl_kirim_tu_bupati,String tgl_kirim_bupati,String time_dokumen,
                            String id_jenis_dokumen,String jenis_dokumen,int id_sifat_dokumen,String sifat_dokumen,String image_disposisi)
    {
        this.dari = dari;
        this.nomor_surat = nomor_surat;
        this.tanggal_surat = tanggal_surat;
        this.perihal = perihal;
        this.diterimaTgl = diterimaTgl;
        this.no_agenda = no_agenda;
        this.id_disposisi = id_disposisi;
        this.path_disposisi = path_disposisi;
        this.path = path;
        this.path_file = path_file;
        this.status = status;
        this.id_dokumen = id_dokumen;
        this.tgl_kirim_tu_umum = tgl_kirim_tu_umum;
        this.tgl_kirim_tu_bupati = tgl_kirim_tu_bupati;
        this.tgl_kirim_bupati = tgl_kirim_bupati;
        this.time_dokumen = time_dokumen;
        this.id_jenis_dokumen = id_jenis_dokumen;
        this.jenis_dokumen = jenis_dokumen;
        this.id_sifat_dokumen = id_sifat_dokumen;
        this.sifat_dokumen = sifat_dokumen;
        this.image_disposisi = image_disposisi;
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
            case 0: return Fg_dokumen_terdisposisi.newInstance(this.path_file,this.path);
            case 1: return Fg_disposisi_terdisposisi.newInstance(this.dari,this.nomor_surat,this.tanggal_surat,this.perihal,
                                                    this.diterimaTgl,this.no_agenda,this.id_disposisi,this.path_disposisi,this.path,this.path_file,
                                                    this.status,this.id_dokumen,this.tgl_kirim_tu_umum,
                                                    this.tgl_kirim_tu_bupati,this.tgl_kirim_bupati,this.time_dokumen,
                                                    this.id_jenis_dokumen,this.jenis_dokumen,this.id_sifat_dokumen,this.sifat_dokumen,this.image_disposisi);
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
            case 0: return "Surat Terdisposisi";
            case 1: return "Disposisi Terdisposisi";
            default: return null;
        }
    }
}

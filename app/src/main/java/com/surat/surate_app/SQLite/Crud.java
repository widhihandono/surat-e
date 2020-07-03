package com.surat.surate_app.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class Crud {
    Helper helper;
    SharedPref sharedPref;

    public Crud(Context context) {
        helper = new Helper(context);
        sharedPref = new SharedPref(context);
    }

    public boolean InsertData(String dari,String nomor_surat,String tanggal_surat,
                           String perihal,String diterimaTgl,String no_agenda,
                           int id_disposisi,String path_disposisi,String path,String path_file,String status,int id_dokumen,String tgl_kirim_tu_umum,
                           String tgl_kirim_tu_bupati,String tgl_kirim_bupati,String time_dokumen,
                           String id_jenis_dokumen,String jenis_dokumen,int id_sifat_dokumen,String sifat_dokumen,String gambar)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_DOKUMEN,id_dokumen);
        contentValues.put(Helper.ID_DISPOSISI,id_disposisi);
        contentValues.put(Helper.ID_JENIS_DOKUMEN,id_jenis_dokumen);
        contentValues.put(Helper.ID_SIFAT_DOKUMEN,id_sifat_dokumen);
        contentValues.put(Helper.DARI,dari);
        contentValues.put(Helper.NOMOR_SURAT,nomor_surat);
        contentValues.put(Helper.TANGGAL_SURAT,tanggal_surat);
        contentValues.put(Helper.PERIHAL,perihal);
        contentValues.put(Helper.DITERIMATGL,diterimaTgl);
        contentValues.put(Helper.NO_AGENDA,no_agenda);
        contentValues.put(Helper.PATH_DISPOSISI,path_disposisi);
        contentValues.put(Helper.PATH,path);
        contentValues.put(Helper.PATH_FILE,path_file);
        contentValues.put(Helper.STATUS,status);
        contentValues.put(Helper.TGL_KIRIM_TU_UMUM,tgl_kirim_tu_umum);
        contentValues.put(Helper.TGL_KIRIM_TU_BUPATI,tgl_kirim_tu_bupati);
        contentValues.put(Helper.TGL_KIRIM_BUPATI,tgl_kirim_bupati);
        contentValues.put(Helper.TIME_DOKUMEN,time_dokumen);
        contentValues.put(Helper.JENIS_DOKUMEN,jenis_dokumen);
        contentValues.put(Helper.SIFAT_DOKUMEN,sifat_dokumen);
        contentValues.put(Helper.GAMBAR,gambar);

        long id = dbb.insert(Helper.TABLE_DISPOSISI,null,contentValues);
        if(id > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    public List<Ent_surat> getData_surat()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.UID,Helper.ID_DOKUMEN,Helper.ID_DISPOSISI,Helper.ID_JENIS_DOKUMEN,Helper.ID_SIFAT_DOKUMEN,
                            Helper.DARI,Helper.NOMOR_SURAT,Helper.TANGGAL_SURAT,Helper.PERIHAL,Helper.DITERIMATGL,
                            Helper.NO_AGENDA,Helper.PATH_DISPOSISI,Helper.PATH,Helper.PATH_FILE,Helper.STATUS,Helper.TGL_KIRIM_TU_UMUM,Helper.TGL_KIRIM_TU_BUPATI,
                            Helper.TGL_KIRIM_BUPATI,Helper.TIME_DOKUMEN,Helper.JENIS_DOKUMEN,Helper.SIFAT_DOKUMEN,Helper.GAMBAR};
        Cursor cursor = db.query(Helper.TABLE_DISPOSISI,coloumn,null,null,null,null,null);
        List<Ent_surat> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_surat es = new Ent_surat();
            es.setId(cursor.getInt(cursor.getColumnIndex(Helper.UID)));
            es.setId_dokumen(cursor.getInt(cursor.getColumnIndex(Helper.ID_DOKUMEN)));
            es.setId_disposisi(cursor.getInt(cursor.getColumnIndex(Helper.ID_DISPOSISI)));
            es.setId_jenis_dokumen(cursor.getString(cursor.getColumnIndex(Helper.ID_JENIS_DOKUMEN)));
            es.setId_sifat_dokumen(cursor.getInt(cursor.getColumnIndex(Helper.ID_SIFAT_DOKUMEN)));
            es.setDari(cursor.getString(cursor.getColumnIndex(Helper.DARI)));
            es.setNomor(cursor.getString(cursor.getColumnIndex(Helper.NOMOR_SURAT)));
            es.setTanggal_surat(cursor.getString(cursor.getColumnIndex(Helper.TANGGAL_SURAT)));
            es.setPerihal(cursor.getString(cursor.getColumnIndex(Helper.PERIHAL)));
            es.setTanggal_diterima(cursor.getString(cursor.getColumnIndex(Helper.DITERIMATGL)));
            es.setNomor_agenda(cursor.getString(cursor.getColumnIndex(Helper.NO_AGENDA)));
            es.setPath_disposisi(cursor.getString(cursor.getColumnIndex(Helper.PATH_DISPOSISI)));
            es.setPath(cursor.getString(cursor.getColumnIndex(Helper.PATH)));
            es.setPath_file(cursor.getString(cursor.getColumnIndex(Helper.PATH_FILE)));
            es.setStatus(cursor.getString(cursor.getColumnIndex(Helper.STATUS)));
            es.setTanggal_kirim_tu_umum(cursor.getString(cursor.getColumnIndex(Helper.TGL_KIRIM_TU_UMUM)));
            es.setTanggal_kirim_tu_bupati(cursor.getString(cursor.getColumnIndex(Helper.TGL_KIRIM_TU_BUPATI)));
            es.setTanggal_kirim_bupati(cursor.getString(cursor.getColumnIndex(Helper.TGL_KIRIM_BUPATI)));
            es.setTime_dokumen(cursor.getString(cursor.getColumnIndex(Helper.TIME_DOKUMEN)));
            es.setJenis_dokumen(cursor.getString(cursor.getColumnIndex(Helper.JENIS_DOKUMEN)));
            es.setSifat_dokumen(cursor.getString(cursor.getColumnIndex(Helper.SIFAT_DOKUMEN)));
            es.setGambar(cursor.getString(cursor.getColumnIndex(Helper.GAMBAR)));

//            int cid = cursor.getInt(cursor.getColumnIndex(Helper.UID));
//            String nomor = cursor.getString(cursor.getColumnIndex(Helper.NOMOR));
//            String alamat = cursor.getString(cursor.getColumnIndex(Helper.ALAMAT));
//            String kategori = cursor.getString(cursor.getColumnIndex(Helper.KATEGORI));

                listPresence.add(es);

        }
        return listPresence;
    }


    public boolean hapus_dokumen_all()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_DISPOSISI,null,null) > 0;
    }

    public boolean hapus_dokumen_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_DISPOSISI,helper.ID_DOKUMEN+"="+id,null) > 0;
    }

    public boolean update_dokumen_by_id(String id,String gambar)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(helper.GAMBAR,gambar);
        return db.update(Helper.TABLE_DISPOSISI,cv,helper.ID_DOKUMEN+"="+id,null) > 0;
    }


}

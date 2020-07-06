package com.surat.surate_app.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Helper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "surate_db";
    public static final String TABLE_DISPOSISI = "disposisi";
    public static final int DATABASE_Version = 2;
    public static final String UID = "id";

    public static final String DARI = "dari",
                            NOMOR_SURAT = "nomor_surat",
                            TANGGAL_SURAT = "tanggal_surat",
                            PERIHAL = "perihal",
                            DITERIMATGL = "diterimaTgl",
                            NO_AGENDA = "no_agenda",
                            PATH_DISPOSISI = "path_disposisi",
                            PATH = "path",
                            PATH_FILE = "path_file",
                            STATUS =    "status",
                            TGL_KIRIM_TU_UMUM = "tgl_kirim_tu_umum",
                            TGL_KIRIM_TU_BUPATI = "tgl_kirim_tu_bupati",
                            TGL_KIRIM_BUPATI = "tgl_kirim_bupati",
                            TIME_DOKUMEN = "time_dokumen",
                            JENIS_DOKUMEN = "jenis_dokumen",
                            SIFAT_DOKUMEN = "sifat_dokumen",
                            GAMBAR = "gambar";
    public static final String ID_DISPOSISI = "id_disposisi",
                                ID_DOKUMEN = "id_dokumen",
                                ID_JENIS_DOKUMEN = "id_jenis_dokumen",
                                ID_SIFAT_DOKUMEN = "id_sifat_dokumen";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_DISPOSISI+
            "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ID_DOKUMEN+" int, "+
            ID_DISPOSISI+" int, "+ID_JENIS_DOKUMEN+" int, "+ID_SIFAT_DOKUMEN+" int,"+DARI+" VARCHAR(150),"+NOMOR_SURAT+" VARCHAR(100)," +
            TANGGAL_SURAT+" VARCHAR(30),"+PERIHAL+" TEXT,"+DITERIMATGL+" VARCHAR(30),"+NO_AGENDA+" VARCHAR(50),"+
            PATH_DISPOSISI+" VARCHAR(200),"+PATH+" VARCHAR(200),"+PATH_FILE+" TEXT,"+STATUS+" VARCHAR(20),"+TGL_KIRIM_TU_UMUM+" VARCHAR(20),"+TGL_KIRIM_TU_BUPATI+" VARCHAR(20)," +
            TGL_KIRIM_BUPATI+" VARCHAR(20),"+TIME_DOKUMEN+" VARCHAR(20),"+JENIS_DOKUMEN+" VARCHAR(50),"+SIFAT_DOKUMEN+" VARCHAR(20)," +
            GAMBAR+" TEXT);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_DISPOSISI;

    public Context context;

    public Helper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);

        }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            Toast.makeText(context,"OnUpgrade",Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

}

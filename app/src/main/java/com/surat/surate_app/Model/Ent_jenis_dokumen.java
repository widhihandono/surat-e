package com.surat.surate_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_jenis_dokumen {

    @SerializedName("id_jenis_dokumen")
    @Expose
    private String id_jenis_dokumen;

    @SerializedName("jenis_dokumen")
    @Expose
    private String jenis_dokumen;

    @SerializedName("jumlah_dokumen")
    @Expose
    private String jumlah_dokumen;

    public String getJumlah_dokumen() {
        return jumlah_dokumen;
    }

    public void setJumlah_dokumen(String jumlah_dokumen) {
        this.jumlah_dokumen = jumlah_dokumen;
    }

    public String getId_jenis_dokumen() {
        return id_jenis_dokumen;
    }

    public void setId_jenis_dokumen(String id_jenis_dokumen) {
        this.id_jenis_dokumen = id_jenis_dokumen;
    }

    public String getJenis_dokumen() {
        return jenis_dokumen;
    }

    public void setJenis_dokumen(String jenis_dokumen) {
        this.jenis_dokumen = jenis_dokumen;
    }
}

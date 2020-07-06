package com.surat.surate_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_cekImei {

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("versi")
    @Expose
    private String versi;

    @SerializedName("pesan")
    @Expose
    private String pesan;

    public String getVersi() {
        return versi;
    }

    public void setVersi(String versi) {
        this.versi = versi;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}

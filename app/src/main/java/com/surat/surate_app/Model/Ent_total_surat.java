package com.surat.surate_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_total_surat {

    @SerializedName("disposisi")
    @Expose
    private int dispsosi;

    @SerializedName("before_disposisi")
    @Expose
    private int before_disposisi;

    @SerializedName("ditolak_bupati")
    @Expose
    private int ditolak_bupati;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("sudah_diteruskan")
    @Expose
    private int sudah_diteruskan;

    @SerializedName("amat_segera")
    @Expose
    private int amat_segera;

    @SerializedName("segera")
    @Expose
    private int segera;

    @SerializedName("biasa")
    @Expose
    private int biasa;

    @SerializedName("rahasia")
    @Expose
    private int rahasia;

    @SerializedName("penting")
    @Expose
    private int penting;

    @SerializedName("tembusan")
    @Expose
    private int tembusan;

    @SerializedName("bukan_rahasia")
    @Expose
    private int bukan_rahasia;

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("pesan")
    @Expose
    private String pesan;

    public int getDitolak_bupati() {
        return ditolak_bupati;
    }

    public void setDitolak_bupati(int ditolak_bupati) {
        this.ditolak_bupati = ditolak_bupati;
    }

    public int getPenting() {
        return penting;
    }

    public void setPenting(int penting) {
        this.penting = penting;
    }

    public int getTembusan() {
        return tembusan;
    }

    public void setTembusan(int tembusan) {
        this.tembusan = tembusan;
    }

    public int getSudah_diteruskan() {
        return sudah_diteruskan;
    }

    public void setSudah_diteruskan(int sudah_diteruskan) {
        this.sudah_diteruskan = sudah_diteruskan;
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

    public int getAmat_segera() {
        return amat_segera;
    }

    public void setAmat_segera(int amat_segera) {
        this.amat_segera = amat_segera;
    }

    public int getSegera() {
        return segera;
    }

    public void setSegera(int segera) {
        this.segera = segera;
    }

    public int getBiasa() {
        return biasa;
    }

    public void setBiasa(int biasa) {
        this.biasa = biasa;
    }

    public int getRahasia() {
        return rahasia;
    }

    public void setRahasia(int rahasia) {
        this.rahasia = rahasia;
    }

    public int getBukan_rahasia() {
        return bukan_rahasia;
    }

    public void setBukan_rahasia(int bukan_rahasia) {
        this.bukan_rahasia = bukan_rahasia;
    }

    public int getDispsosi() {
        return dispsosi;
    }

    public void setDispsosi(int dispsosi) {
        this.dispsosi = dispsosi;
    }

    public int getBefore_disposisi() {
        return before_disposisi;
    }

    public void setBefore_disposisi(int before_disposisi) {
        this.before_disposisi = before_disposisi;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

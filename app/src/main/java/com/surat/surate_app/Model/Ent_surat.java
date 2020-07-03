package com.surat.surate_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_surat {

    private int id;

    private String gambar;

    @Expose
    @SerializedName("id_dokumen")
    private int id_dokumen;

    @Expose
    @SerializedName("pesan")
    private String pesan;

    @Expose
    @SerializedName("path")
    private String path;

    @Expose
    @SerializedName("path_file")
    private String path_file;

    @Expose
    @SerializedName("id_jenis_dokumen")
    private String id_jenis_dokumen;

    @Expose
    @SerializedName("jenis_dokumen")
    private String jenis_dokumen;

    @Expose
    @SerializedName("time_dokumen")
    private String time_dokumen;

    @Expose
    @SerializedName("id_sifat_dokumen")
    private int id_sifat_dokumen;

    @Expose
    @SerializedName("sifat_dokumen")
    private String sifat_dokumen;


    @Expose
    @SerializedName("path_disposisi")
    private String path_disposisi;

    @Expose
    @SerializedName("response")
    private int response;

    //Table Disposisi
    @Expose
    @SerializedName("id_disposisi")
    private int id_disposisi;

    @Expose
    @SerializedName("dari")
    private String dari;

    @Expose
    @SerializedName("nomor")
    private String nomor;

    @Expose
    @SerializedName("tanggal_diterima")
    private String tanggal_diterima;

    @Expose
    @SerializedName("tanggal_surat")
    private String tanggal_surat;

    @Expose
    @SerializedName("nomor_agenda")
    private String nomor_agenda;

    @Expose
    @SerializedName("perihal")
    private String perihal;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("tanggal_kirim_tu_umum")
    private String tanggal_kirim_tu_umum;

    @Expose
    @SerializedName("tanggal_kirim_tu_bupati")
    private String tanggal_kirim_tu_bupati;

    @Expose
    @SerializedName("tanggal_kirim_bupati")
    private String tanggal_kirim_bupati;

    @Expose
    @SerializedName("tanggal_kirim_ajudan")
    private String tanggal_kirim_ajudan;

    @Expose
    @SerializedName("image_disposisi")
    private String image_disposisi;

    @Expose
    @SerializedName("alasan_ditolak_bupati")
    private String alasan_ditolak_bupati;

    public String getAlasan_ditolak_bupati() {
        return alasan_ditolak_bupati;
    }

    public void setAlasan_ditolak_bupati(String alasan_ditolak_bupati) {
        this.alasan_ditolak_bupati = alasan_ditolak_bupati;
    }

    public String getTanggal_kirim_ajudan() {
        return tanggal_kirim_ajudan;
    }

    public void setTanggal_kirim_ajudan(String tanggal_kirim_ajudan) {
        this.tanggal_kirim_ajudan = tanggal_kirim_ajudan;
    }

    public String getPath_disposisi() {
        return path_disposisi;
    }

    public void setPath_disposisi(String path_disposisi) {
        this.path_disposisi = path_disposisi;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getImage_disposisi() {
        return image_disposisi;
    }

    public void setImage_disposisi(String image_disposisi) {
        this.image_disposisi = image_disposisi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getId_dokumen() {
        return id_dokumen;
    }

    public void setId_dokumen(int id_dokumen) {
        this.id_dokumen = id_dokumen;
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
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

    public String getTime_dokumen() {
        return time_dokumen;
    }

    public void setTime_dokumen(String time_dokumen) {
        this.time_dokumen = time_dokumen;
    }

    public int getId_sifat_dokumen() {
        return id_sifat_dokumen;
    }

    public void setId_sifat_dokumen(int id_sifat_dokumen) {
        this.id_sifat_dokumen = id_sifat_dokumen;
    }

    public String getSifat_dokumen() {
        return sifat_dokumen;
    }

    public void setSifat_dokumen(String sifat_dokumen) {
        this.sifat_dokumen = sifat_dokumen;
    }

    public int getId_disposisi() {
        return id_disposisi;
    }

    public void setId_disposisi(int id_disposisi) {
        this.id_disposisi = id_disposisi;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getTanggal_diterima() {
        return tanggal_diterima;
    }

    public void setTanggal_diterima(String tanggal_diterima) {
        this.tanggal_diterima = tanggal_diterima;
    }

    public String getTanggal_surat() {
        return tanggal_surat;
    }

    public void setTanggal_surat(String tanggal_surat) {
        this.tanggal_surat = tanggal_surat;
    }

    public String getNomor_agenda() {
        return nomor_agenda;
    }

    public void setNomor_agenda(String nomor_agenda) {
        this.nomor_agenda = nomor_agenda;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_kirim_tu_umum() {
        return tanggal_kirim_tu_umum;
    }

    public void setTanggal_kirim_tu_umum(String tanggal_kirim_tu_umum) {
        this.tanggal_kirim_tu_umum = tanggal_kirim_tu_umum;
    }

    public String getTanggal_kirim_tu_bupati() {
        return tanggal_kirim_tu_bupati;
    }

    public void setTanggal_kirim_tu_bupati(String tanggal_kirim_tu_bupati) {
        this.tanggal_kirim_tu_bupati = tanggal_kirim_tu_bupati;
    }

    public String getTanggal_kirim_bupati() {
        return tanggal_kirim_bupati;
    }

    public void setTanggal_kirim_bupati(String tanggal_kirim_bupati) {
        this.tanggal_kirim_bupati = tanggal_kirim_bupati;
    }
}

package com.surat.surate_app.Model;

public class Ent_sifat_surat {
    private String sifat_dokumen, color;
    private int image;
    private int id_sifat_surat;
    private int total;
    private String status;

    private String response,pesan;

    public Ent_sifat_surat(int id_sifat_surat,String sifat_dokumen,int total, int image,String color,String status) {
        this.id_sifat_surat = id_sifat_surat;
        this.sifat_dokumen = sifat_dokumen;
        this.image = image;
        this.total = total;
        this.color = color;
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public int getTotal() {
        return total;
    }

    public int getId_sifat_surat() {
        return id_sifat_surat;
    }

    public String getSifat_dokumen() {
        return sifat_dokumen;
    }


    public int getImage() {
        return image;
    }
}

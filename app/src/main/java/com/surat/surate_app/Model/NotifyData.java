package com.surat.surate_app.Model;

public class NotifyData {
    String title;
    String message,to_notif,perihal,tanggal_surat,dari,id_dokumen,id_disposisi;


    public NotifyData(String title, String message,String to_notif,String perihal,String tanggal_surat,String dari, String id_dokumen,String id_disposisi) {

        this.title = title;
        this.perihal = perihal;
        this.message = message;
        this.to_notif = to_notif;
        this.tanggal_surat = tanggal_surat;
        this.dari = dari;
        this.id_dokumen = id_dokumen;
        this.id_disposisi = id_disposisi;
    }
}

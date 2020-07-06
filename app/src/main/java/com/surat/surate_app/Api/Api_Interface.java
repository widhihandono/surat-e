package com.surat.surate_app.Api;

import com.surat.surate_app.Model.Ent_User;
import com.surat.surate_app.Model.Ent_cekImei;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Model.Ent_total_surat;
import com.surat.surate_app.Model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_Interface {
    //
    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/tolak_dokumen")
    Call<Ent_surat>  tolak_dokumen(@Field("id_disposisi") int id_disposisi,
                                   @Field("alasan") String alasan,
                                   @Field("status") String status);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/forward_dokumen_ajudan")
    Call<Ent_surat>  forward_dokumen_ajudan(@Field("id_disposisi") int id_disposisi,
                                            @Field("forward_from_ajudan") String forward_from_ajudan);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/LoginApi")
    Call<Ent_User>  Login(@Field("username") String username,
                          @Field("password") String password);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/change_pass")
    Call<Ent_User>  Change_pass(@Field("username") String username,
                                @Field("pass_lama") String pass_lama,
                                @Field("pass_baru") String pass_baru);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/show_dokumen_before_disposisi")
    Call<List<Ent_surat>>  show_dokumen_before_disposisi(@Field("status") String status,
                                                         @Field("id_sifat_dokumen") int id_sifat_dokumen,
                                                         @Field("id_jenis_dokumen") String id_jenis_dokumen,
                                                         @Field("offset") int offset,
                                                         @Field("row_count") int row_count);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/search_dokumen_before_disposisi_by_dari")
    Call<List<Ent_surat>>  search_dokumen_before_disposisi_by_dari(@Field("status") String status,
                                                                   @Field("id_sifat_dokumen") int id_sifat_dokumen,
                                                                   @Field("id_jenis_dokumen") String id_jenis_dokumen,
                                                                   @Field("dari") String dari,
                                                                   @Field("offset") int offset,
                                                                   @Field("row_count") int row_count);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/update_disposisi")
    Call<Ent_surat>  update_disposisi(@Field("id_disposisi") int id_disposisi,
                                      @Field("image_disposisi") String image_disposisi);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/show_total_surat")
    Call<Ent_total_surat> total(@Query("id_jenis_dokumen") String id_jenis_dokumen,
                                @Query("status") String status);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/show_total_surat_no_jenis_dokumen")
    Call<Ent_total_surat> showTotalSurat(@Query("status") String status);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/show_all_dokumen_ditolak_bupati")
    Call<List<Ent_surat>> show_all_dokumen_ditolak_bupati(@Field("offset") int offset,
                                                          @Field("row_count") int row_count);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/search_all_dokumen_ditolak_bupati_by_dari")
    Call<List<Ent_surat>> search_all_dokumen_ditolak_bupati_by_dari(@Field("dari") String dari);


    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/show_all_dokumen_before_disposisi")
    Call<List<Ent_surat>> show_all_dokumen_before_disposisi(@Query("offset") int offset,
                                                            @Query("row_count") int row_count);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/search_all_dokumen_before_disposisi_by_dari")
    Call<List<Ent_surat>> search_all_dokumen_before_disposisi_by_dari(@Query("dari") String dari);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/show_all_dokumen_after_disposisi")
    Call<List<Ent_surat>> show_all_dokumen_after_disposisi(@Query("status") String status,
                                                           @Query("offset") int offset,
                                                           @Query("row_count") int row_count);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/search_all_dokumen_after_disposisi_by_dari")
    Call<List<Ent_surat>> search_all_dokumen_after_disposisi_by_dari(@Query("dari") String dari);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/cekImei")
    Call<Ent_cekImei>  cekImei(@Field("imei_hp_id") String imei_hp_id);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/cek_version")
    Call<Ent_cekImei> cek_versi();

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_dokumen/jenis_dokumen")
    Call<List<Ent_jenis_dokumen>> jenis_dokumen();

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/send_to_bupati")
    Call<Ent_surat>  send_to_bupati(@Field("id_disposisi") int id_disposisi);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_dokumen/sendNotif")
    Call<Ent_surat>  sendNotif(@Field("title") String title,
                               @Field("message") String message);


    //FCM Notif
    @POST("/fcm/send")
    Call<Message> sendMessage(@Body Message message);
}

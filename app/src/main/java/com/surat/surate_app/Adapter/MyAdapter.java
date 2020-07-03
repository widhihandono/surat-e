package com.surat.surate_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.surat.surate_app.Dokumen_activity;
import com.surat.surate_app.Login_Activity;
import com.surat.surate_app.Model.Ent_sifat_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.Util.SharedPref;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter< FlowerViewHolder > {

    private Context mContext;
    private List<Ent_sifat_surat> mSifatSuratList;
    SharedPref sharedPref;

    public MyAdapter(Context mContext, List<Ent_sifat_surat> mSifatSuratList) {
        this.mContext = mContext;
        this.mSifatSuratList = mSifatSuratList;
        sharedPref = new SharedPref(mContext);
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, int position) {
        holder.mImage.setImageResource(mSifatSuratList.get(position).getImage());
        holder.mTitle.setText(mSifatSuratList.get(position).getSifat_dokumen());
        holder.tvTotal.setText(String.valueOf(mSifatSuratList.get(position).getTotal()));
        holder.ln_menu.setBackgroundColor(Color.parseColor(mSifatSuratList.get(position).getColor()));

        holder.itemView.setOnClickListener(l->{

                Intent intent = new Intent(mContext, Dokumen_activity.class);
                intent.putExtra("id_sifat_dokumen",mSifatSuratList.get(position).getId_sifat_surat());
                intent.putExtra("sifat_dokumen",mSifatSuratList.get(position).getSifat_dokumen());
                intent.putExtra("total",mSifatSuratList.get(position).getTotal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Animatoo.animateZoom(mContext);

        });
    }

    @Override
    public int getItemCount() {
        return mSifatSuratList.size();
    }

//    private void showDialogKeluar(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//
//        // set title dialog
//        alertDialogBuilder.setTitle("Yakin ingin Logout ?");
//
//        // set pesan dari dialog
//        alertDialogBuilder
//                .setMessage("Tekan Ya untuk Logout")
//                .setCancelable(false)
//                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                    public void onClick(DialogInterface dialog, int id) {
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic(sharedPref.sp.getString("role","")+"_1");
//                        sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,false);
//                        sharedPref.saveSPString("role","");
//
//                        mContext.startActivity(new Intent(mContext,Login_Activity.class));
//
//                        ((Activity)mContext).finish();
//                    }
//                })
//                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // jika tombol ini diklik, akan menutup dialog
//                        // dan tidak terjadi apa2
//                        dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();
//        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        nbutton.setTextColor(Color.RED);
//        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        pbutton.setTextColor(Color.RED);
//    }
}

class FlowerViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle,tvTotal;
    View vLine;
    LinearLayout ln_menu;

    FlowerViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        tvTotal = itemView.findViewById(R.id.tvTotal);
        vLine = itemView.findViewById(R.id.vLine);
        ln_menu = itemView.findViewById(R.id.ln_menu);
    }
}

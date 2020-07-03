package com.surat.surate_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.surat.surate_app.Detail_Dokumen_Terdisposisi_Activity;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.Util.SharedPref;

import java.util.List;

public class List_Terdisposisi_Adapter extends RecyclerView.Adapter<List_Terdisposisi_Adapter.ViewHolder> {
Context context;
private List<Ent_surat> listSurat;
SharedPref sharedPref;
int width;
    public List_Terdisposisi_Adapter(Context context, List<Ent_surat> listSurat) {
        this.context = context;
        this.listSurat = listSurat;
        sharedPref = new SharedPref(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        this.width = displayMetrics.widthPixels;
    }

    @NonNull
    @Override
    public List_Terdisposisi_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_dokumen_terdisposisi,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull List_Terdisposisi_Adapter.ViewHolder holder, int i) {
//        holder.ln_1.getLayoutParams().width = width;
//        holder.ln_2.getLayoutParams().width = width;
//        holder.ln_1.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_menu));
//        holder.ln_2.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_menu));

        holder.tvTglSurat.setWidth(width/2);
        holder.tvSuratDari.setWidth(width/2);
        holder.tvNomorSurat.setWidth(width/2);
        holder.tvPerihal.setWidth(width/2);

        holder.tvTglMasuk.setWidth(width/5);
        holder.tvTglKirim.setWidth(width/5);

            holder.tvKirimBupati.setVisibility(View.GONE);


        holder.tvTglSurat.setText(listSurat.get(i).getTanggal_surat());
        holder.tvTglMasuk.setText(listSurat.get(i).getTanggal_kirim_ajudan());
            if(listSurat.get(i).getTanggal_kirim_bupati() == null || listSurat.get(i).getTanggal_kirim_bupati().isEmpty())
            {
                holder.tvTglKirim.setText("-");
                holder.tvTglKirim.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.tvTglKirim.setText(listSurat.get(i).getTanggal_kirim_bupati());
            }
        holder.tvSuratDari.setText(listSurat.get(i).getDari());
        holder.tvNomorSurat.setText(listSurat.get(i).getNomor());
        holder.tvPerihal.setText(listSurat.get(i).getPerihal());
        holder.tvSifatDokumen.setText(listSurat.get(i).getSifat_dokumen());
        holder.tvKirimBupati.setText(listSurat.get(i).getTanggal_kirim_bupati());

        holder.itemView.setOnClickListener(l->{
            Intent intent = new Intent(context, Detail_Dokumen_Terdisposisi_Activity.class);
            intent.putExtra("id_disposisi",listSurat.get(i).getId_disposisi());
            intent.putExtra("dari",listSurat.get(i).getDari());
            intent.putExtra("nomor_surat",listSurat.get(i).getNomor());
            intent.putExtra("tanggal_diterima",listSurat.get(i).getTanggal_diterima());
            intent.putExtra("tanggal_surat",listSurat.get(i).getTanggal_surat());
            intent.putExtra("nomor_agenda",listSurat.get(i).getNomor_agenda());
            intent.putExtra("perihal",listSurat.get(i).getPerihal());
            intent.putExtra("status",listSurat.get(i).getStatus());
            intent.putExtra("id_dokumen",listSurat.get(i).getId_dokumen());
            intent.putExtra("tanggal_kirim_tu_umum",listSurat.get(i).getTanggal_kirim_tu_umum());
            intent.putExtra("tanggal_kirim_tu_bupati",listSurat.get(i).getTanggal_kirim_tu_bupati());
            intent.putExtra("tanggal_kirim_bupati",listSurat.get(i).getTanggal_kirim_bupati());
            intent.putExtra("path_disposisi",listSurat.get(i).getPath_disposisi());
            intent.putExtra("path",listSurat.get(i).getPath());
            intent.putExtra("path_file",listSurat.get(i).getPath_file());
            intent.putExtra("time_dokumen",listSurat.get(i).getTime_dokumen());
            intent.putExtra("id_jenis_dokumen",listSurat.get(i).getId_jenis_dokumen());
            intent.putExtra("jenis_dokumen",listSurat.get(i).getJenis_dokumen());
            intent.putExtra("id_sifat_dokumen",listSurat.get(i).getId_sifat_dokumen());
            intent.putExtra("sifat_surat",listSurat.get(i).getSifat_dokumen());
            intent.putExtra("image_disposisi",listSurat.get(i).getImage_disposisi());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listSurat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTglMasuk,tvTglKirim,tvTglSurat,
                tvSuratDari,tvNomorSurat,tvPerihal,tvSifatDokumen,tvKirimBupati;
        private LinearLayout rl_bg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTglMasuk = itemView.findViewById(R.id.tvTglMasuk);
            tvTglKirim = itemView.findViewById(R.id.tvTglKirim);
            tvTglSurat = itemView.findViewById(R.id.tvTglSurat);
            tvSuratDari = itemView.findViewById(R.id.tvSuratDari);
            tvNomorSurat = itemView.findViewById(R.id.tvNomorSurat);
            tvPerihal = itemView.findViewById(R.id.tvPerihal);
            tvSifatDokumen = itemView.findViewById(R.id.tvSifatDokumen);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            tvKirimBupati = itemView.findViewById(R.id.tvKirimBupati);

        }
    }
}

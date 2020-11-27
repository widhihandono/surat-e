package com.surat.surate_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surat.surate_app.Detail_Dokumen_Activity;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.Util.SharedPref;

import java.util.List;

import static java.security.AccessController.getContext;

public class List_Disposisi_Adapter extends RecyclerView.Adapter<List_Disposisi_Adapter.Holder> {
    Context context;
    int width;
    private List<Ent_surat> listSurat;
    SharedPref sharedPref;

    public List_Disposisi_Adapter(Context context, int width, List<Ent_surat> listSurat) {
        this.context = context;
        this.listSurat = listSurat;
        sharedPref = new SharedPref(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        this.width = displayMetrics.widthPixels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_dokumen,viewGroup,false);
        Holder viewHolder = new Holder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

            holder.tvTglSurat.setWidth(270);
            holder.tvSuratDari.setWidth(270);
            holder.tvNomorSurat.setWidth(270);
            holder.tvPerihal.setWidth(270);


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



        holder.itemView.setOnClickListener(l->{
            Intent intent = new Intent(context, Detail_Dokumen_Activity.class);
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
            intent.putExtra("path_file",listSurat.get(i).getPath_file());
            intent.putExtra("time_dokumen",listSurat.get(i).getTime_dokumen());
            intent.putExtra("id_jenis_dokumen",listSurat.get(i).getId_jenis_dokumen());
            intent.putExtra("jenis_dokumen",listSurat.get(i).getJenis_dokumen());
            intent.putExtra("id_sifat_dokumen",listSurat.get(i).getId_sifat_dokumen());
            intent.putExtra("sifat_surat",listSurat.get(i).getSifat_dokumen());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listSurat.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvTglMasuk,tvTglKirim,tvTglSurat,
                tvSuratDari,tvNomorSurat,tvPerihal;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTglMasuk = itemView.findViewById(R.id.tvTglMasuk);
            tvTglKirim = itemView.findViewById(R.id.tvTglKirim);
            tvTglSurat = itemView.findViewById(R.id.tvTglSurat);
            tvSuratDari = itemView.findViewById(R.id.tvSuratDari);
            tvNomorSurat = itemView.findViewById(R.id.tvNomorSurat);
            tvPerihal = itemView.findViewById(R.id.tvPerihal);
        }
    }
}

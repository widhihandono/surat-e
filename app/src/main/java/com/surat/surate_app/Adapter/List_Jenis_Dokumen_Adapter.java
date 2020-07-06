package com.surat.surate_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.surat.surate_app.Detail_Dokumen_Activity;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.SQLite.Crud;
import com.surat.surate_app.Show_Dokumen_Activity;
import com.surat.surate_app.Util.SharedPref;

import java.util.List;

public class List_Jenis_Dokumen_Adapter extends RecyclerView.Adapter<List_Jenis_Dokumen_Adapter.ViewHolder> {
Context context;
private List<Ent_jenis_dokumen> listJenisDokumen;

    public List_Jenis_Dokumen_Adapter(Context context, List<Ent_jenis_dokumen> listJenisDokumen) {
        this.context = context;
        this.listJenisDokumen = listJenisDokumen;
    }


    @NonNull
    @Override
    public List_Jenis_Dokumen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_jenis_surat,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull List_Jenis_Dokumen_Adapter.ViewHolder holder, int i) {

        holder.tvJenisSurat.setText(listJenisDokumen.get(i).getJenis_dokumen());
    }

    @Override
    public int getItemCount() {
        return listJenisDokumen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJenisSurat,tvJumlahSurat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJenisSurat = itemView.findViewById(R.id.tvJenisSurat);
            tvJumlahSurat = itemView.findViewById(R.id.tvJumlahSurat);


        }
    }
}

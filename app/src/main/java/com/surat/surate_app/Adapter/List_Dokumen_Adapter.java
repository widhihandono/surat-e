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
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.surat.surate_app.Detail_Dokumen_Activity;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.SQLite.Crud;
import com.surat.surate_app.Show_Dokumen_Activity;
import com.surat.surate_app.Util.SharedPref;

import java.util.List;

public class List_Dokumen_Adapter extends RecyclerView.Adapter<List_Dokumen_Adapter.ViewHolder> {
Context context;
private List<Ent_surat> listSurat;
private Crud crudSqlite;
private SharedPref sharedPref;
int width;

    public List_Dokumen_Adapter(Context context,List<Ent_surat> listSurat) {
        this.context = context;
        this.listSurat = listSurat;
        crudSqlite = new Crud(context);
        sharedPref = new SharedPref(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        this.width = displayMetrics.widthPixels;
//        Toast.makeText(context,sharedPref.sp.getString("role","")+"  sfs", Toast.LENGTH_LONG).show();
    }


    @NonNull
    @Override
    public List_Dokumen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_dokumen,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull List_Dokumen_Adapter.ViewHolder holder, int i) {
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

        if(crudSqlite.getData_surat().size() > 0)
        {
            for(int a=0;a<crudSqlite.getData_surat().size();a++)
            {
                if(listSurat.get(i).getId_dokumen() == crudSqlite.getData_surat().get(a).getId_dokumen())
                {
                    holder.rl_bg.setEnabled(false);
                    holder.rl_bg.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_menu_yellow));
                }

            }
        }

        holder.tvTglSurat.setText(listSurat.get(i).getTanggal_surat());


        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            if(listSurat.get(i).getPerihal().length() > 10)
            {
                holder.tvPerihal.setText(listSurat.get(i).getPerihal().substring(0,10)+"[...]");
            }
            else
            {
                holder.tvPerihal.setText(listSurat.get(i).getPerihal());
            }

            holder.tvPerihal.setOnClickListener(l->{
                if(holder.tvPerihal.getText().length() > 17)
                {
                    holder.tvPerihal.setText(listSurat.get(i).getPerihal().substring(0,10)+"[...]");
                }
                else
                {
                    holder.tvPerihal.setText(listSurat.get(i).getPerihal()+" [hide]");
                }
            });

            holder.tvTglMasuk.setText(listSurat.get(i).getTanggal_kirim_tu_bupati());
            if (listSurat.get(i).getTanggal_kirim_ajudan() == null || listSurat.get(i).getTanggal_kirim_ajudan().isEmpty()) {
                holder.tvTglKirim.setText("-");
                holder.tvTglKirim.setVisibility(View.INVISIBLE);
            } else {
                holder.tvTglKirim.setText(listSurat.get(i).getTanggal_kirim_ajudan());
            }
        }
        else
        {
            holder.tvTglMasuk.setText(listSurat.get(i).getTanggal_kirim_ajudan());
            if (listSurat.get(i).getTanggal_kirim_bupati() == null || listSurat.get(i).getTanggal_kirim_bupati().isEmpty()) {
                holder.tvTglKirim.setText("-");
                holder.tvTglKirim.setVisibility(View.INVISIBLE);
            } else {
                holder.tvTglKirim.setText(listSurat.get(i).getTanggal_kirim_bupati());
            }

            holder.tvPerihal.setText(listSurat.get(i).getPerihal());
        }

        holder.tvSuratDari.setText(listSurat.get(i).getDari());
        holder.tvNomorSurat.setText(listSurat.get(i).getNomor());




        holder.tvSifatDokumen.setText(listSurat.get(i).getSifat_dokumen());

        holder.itemView.setOnClickListener(l->{
            if(sharedPref.sp.getString("role","").equals("ajudan"))
            {
                Intent intent = new Intent(context, Show_Dokumen_Activity.class);
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
                intent.putExtra("path",listSurat.get(i).getPath());
                intent.putExtra("path_diposisi",listSurat.get(i).getPath_disposisi());
                intent.putExtra("path_file",listSurat.get(i).getPath_file());
                intent.putExtra("time_dokumen",listSurat.get(i).getTime_dokumen());
                intent.putExtra("id_jenis_dokumen",listSurat.get(i).getId_jenis_dokumen());
                intent.putExtra("jenis_dokumen",listSurat.get(i).getJenis_dokumen());
                intent.putExtra("id_sifat_dokumen",listSurat.get(i).getId_sifat_dokumen());
                intent.putExtra("sifat_dokumen",listSurat.get(i).getSifat_dokumen());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Animatoo.animateZoom(context);
            }
            else
            {
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
                intent.putExtra("path",listSurat.get(i).getPath());
                intent.putExtra("path_diposisi",listSurat.get(i).getPath_disposisi());
                intent.putExtra("path_file",listSurat.get(i).getPath_file());
                intent.putExtra("time_dokumen",listSurat.get(i).getTime_dokumen());
                intent.putExtra("id_jenis_dokumen",listSurat.get(i).getId_jenis_dokumen());
                intent.putExtra("jenis_dokumen",listSurat.get(i).getJenis_dokumen());
                intent.putExtra("id_sifat_dokumen",listSurat.get(i).getId_sifat_dokumen());
                intent.putExtra("sifat_dokumen",listSurat.get(i).getSifat_dokumen());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Animatoo.animateZoom(context);
            }

        });
    }

    @Override
    public int getItemCount() {
        return listSurat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTglMasuk,tvTglKirim,tvTglSurat,
                tvSuratDari,tvNomorSurat,tvPerihal,tvSifatDokumen;
        private LinearLayout rl_bg;
        private TableRow trData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trData = itemView.findViewById(R.id.trData);

            tvTglMasuk = itemView.findViewById(R.id.tvTglMasuk);
            tvTglKirim = itemView.findViewById(R.id.tvTglKirim);
            tvTglSurat = itemView.findViewById(R.id.tvTglSurat);
            tvSuratDari = itemView.findViewById(R.id.tvSuratDari);
            tvNomorSurat = itemView.findViewById(R.id.tvNomorSurat);
            tvPerihal = itemView.findViewById(R.id.tvPerihal);
            tvSifatDokumen = itemView.findViewById(R.id.tvSifatDokumen);
            rl_bg = itemView.findViewById(R.id.rl_bg);

        }
    }
}

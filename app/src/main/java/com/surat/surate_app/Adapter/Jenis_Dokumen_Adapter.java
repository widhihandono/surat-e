package com.surat.surate_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.surat.surate_app.Detail_Dokumen_Activity;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Model.onMenuClick;
import com.surat.surate_app.R;
import com.surat.surate_app.SQLite.Crud;

import java.util.List;

public class Jenis_Dokumen_Adapter extends RecyclerView.Adapter<Jenis_Dokumen_Adapter.ViewHolder>{
Context context;
private List<Ent_jenis_dokumen> listJenisDokumen;
private Crud crudSqlite;
int width,height;
String id_jenis_dokumen="1",jenis_dokumen="";
JenisAdapterCallback jenisAdapterCallback;
boolean klik=false;

    private static final int MULTIPLE = 0;
    private static final int SINGLE = 1;
    private static int sModo = 0;
    private static int sPosition;

    private static SparseBooleanArray sSelectedItems;
    private static UpdateDataClickListener sClickListener;

    public Jenis_Dokumen_Adapter(Context context, List<Ent_jenis_dokumen> listJenisDokumen,int width,int height, JenisAdapterCallback jenisAdapterCallback) {
        this.context = context;
        this.listJenisDokumen = listJenisDokumen;
        crudSqlite = new Crud(context);
        this.width = width;
        this.height = height;
        this.jenisAdapterCallback = jenisAdapterCallback;
        sSelectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public Jenis_Dokumen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_jenis_dokumen,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Jenis_Dokumen_Adapter.ViewHolder holder, int i) {
        holder.tvJenisDokumen.setText(listJenisDokumen.get(i).getJenis_dokumen());
        if(listJenisDokumen.size()-1 == i)
        {
            holder.lnJenisDokumen.setPadding(10,5,10,5);
        }
        else
        {
            holder.lnJenisDokumen.setPadding(10,5,width/8,5);
        }

        holder.itemView.setOnClickListener(l->{
            id_jenis_dokumen = listJenisDokumen.get(i).getId_jenis_dokumen();
            jenisAdapterCallback.onRowJenisDokumenClicked(id_jenis_dokumen);

        });

        holder.imgJenisDokumen.setSelected(sSelectedItems.get(i, false));



    }

    @Override
    public int getItemCount() {
        return listJenisDokumen.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgJenisDokumen;
        private TextView tvJenisDokumen;
        private LinearLayout lnJenisDokumen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgJenisDokumen = itemView.findViewById(R.id.imgJenisDokumen);
            tvJenisDokumen = itemView.findViewById(R.id.tvJenisDokumen);
            lnJenisDokumen = itemView.findViewById(R.id.lnJenisDokumen);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (sSelectedItems.get(getAdapterPosition(), false)) {
                sSelectedItems.delete(getAdapterPosition());
                imgJenisDokumen.setSelected(false);
            } else {
                switch (sModo) {
                    case SINGLE:
                        sSelectedItems.put(sPosition, false);
                        break;
                    case MULTIPLE:
                    default:
                        break;
                }
                sSelectedItems.put(getAdapterPosition(), true);
                imgJenisDokumen.setSelected(true);
            }
            sClickListener.onItemClick(getAdapterPosition());
        }
    }


    public void selected(int position) {
        switch (sModo) {
            case SINGLE:
                sPosition = position;
                notifyDataSetChanged();
                break;
            case MULTIPLE:
            default:
                break;
        }
    }

    public void changeMode(int modo) {
        sModo = modo;
        sSelectedItems.clear();
        notifyDataSetChanged();
    }

    void setOnItemClickListener(UpdateDataClickListener clickListener) {
        sClickListener = clickListener;
    }



    public interface JenisAdapterCallback
    {
        void onRowJenisDokumenClicked(String id_jenis_dokumen);
    }

    interface UpdateDataClickListener {
        void onItemClick(int position);
    }
}

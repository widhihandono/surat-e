package com.surat.surate_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.surat.surate_app.Ditolak_Bupati_Activity;
import com.surat.surate_app.History_Disposisi_Activity;
import com.surat.surate_app.Model.Ent_sifat_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.Sudah_Diteruskan_Activity;
import com.surat.surate_app.Terdisposisi_Offline_Activity;

import java.util.List;

public class MyAdapter_1 extends RecyclerView.Adapter< FlowerViewHolder > {

    private Context mContext;
    private List<Ent_sifat_surat> mSifatSuratList;

    public MyAdapter_1(Context mContext, List<Ent_sifat_surat> mSifatSuratList) {
        this.mContext = mContext;
        this.mSifatSuratList = mSifatSuratList;
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
            if(mSifatSuratList.get(position).getStatus().equals("belum_kirim"))
            {
                Intent intent = new Intent(mContext, Terdisposisi_Offline_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            else if(mSifatSuratList.get(position).getStatus().equals("terdisposisi"))
            {
                Intent intent = new Intent(mContext, History_Disposisi_Activity.class);
                intent.putExtra("total",mSifatSuratList.get(position).getTotal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            else if(mSifatSuratList.get(position).getStatus().equals("belum_disposisi"))
            {
                Toast toast = Toast.makeText(mContext,"Bisa dilihat dibawah ini",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
            else if(mSifatSuratList.get(position).getStatus().equals("sudah_diteruskan"))
            {
                Intent intent = new Intent(mContext, Sudah_Diteruskan_Activity.class);
                intent.putExtra("total",mSifatSuratList.get(position).getTotal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            else if(mSifatSuratList.get(position).getStatus().equals("surat_masuk"))
            {
                Toast toast = Toast.makeText(mContext,"Bisa dilihat dibawah ini",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
            else if(mSifatSuratList.get(position).getStatus().equals("ditolak"))
            {
                Intent intent = new Intent(mContext, Ditolak_Bupati_Activity.class);
                intent.putExtra("total",mSifatSuratList.get(position).getTotal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            else
            {
                Log.i("my_adapter_1","Total");
            }

        });
    }

    @Override
    public int getItemCount() {
        return mSifatSuratList.size();
    }
}

class FlowerViewHolder_1 extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    LinearLayout ln_menu;

    FlowerViewHolder_1(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        ln_menu = itemView.findViewById(R.id.ln_menu);
    }
}

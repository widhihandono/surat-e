package com.surat.surate_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.surat.surate_app.Model.Ent_sifat_surat;
import com.surat.surate_app.R;

import java.util.List;

public class List_Dokumen_Adapter extends RecyclerView.Adapter<List_Dokumen_Adapter.ViewHolder> {
Context context;
int width,height;

    public List_Dokumen_Adapter(Context context,int width,int height) {
        this.context = context;
        this.width = width;
        this.height = height;

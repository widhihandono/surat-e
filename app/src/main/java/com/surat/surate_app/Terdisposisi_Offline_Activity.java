package com.surat.surate_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.surat.surate_app.Adapter.List_Dokumen_belum_kirim_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.SQLite.Crud;

public class Terdisposisi_Offline_Activity extends AppCompatActivity {
    private RecyclerView rvDokumen;
    private RecyclerView.LayoutManager layoutManager;
    private Api_Interface api_interface;
    Crud crudSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terdisposisi__offline_);
        getSupportActionBar().setTitle("Terdisposisi Belum Kirim");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);

        api_interface = Api_Class.getClient().create(Api_Interface.class);
        crudSqlite = new Crud(this);
        rvDokumen = findViewById(R.id.rvDokumen);
        rvDokumen.setLayoutManager(layoutManager);

        show_terdisposisi_belum_kirim();

    }

    private void show_terdisposisi_belum_kirim()
    {
        List_Dokumen_belum_kirim_Adapter list_dokumen_adapter = new List_Dokumen_belum_kirim_Adapter(this,crudSqlite.getData_surat());
        rvDokumen.setAdapter(list_dokumen_adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Terdisposisi_Offline_Activity.this,Menu_Utama_Activity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Terdisposisi_Offline_Activity.this,Menu_Utama_Activity.class));
        finish();
    }
}

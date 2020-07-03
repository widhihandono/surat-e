package com.surat.surate_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.surat.surate_app.Adapter.List_Terdisposisi_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History_Disposisi_Activity extends AppCompatActivity {
    Display display;
    private RecyclerView rvDTerdisposisi;
    private RecyclerView.LayoutManager layoutManager;
    private Api_Interface api_interface;
    private SharedPref sharedPref;
    private TextView tvLoadMore;
    int offset = 0,row_count = 10;
    List<Ent_surat> pagingDokumen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__disposisi_);
        getSupportActionBar().setTitle("List Terdisposisi");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);

        sharedPref = new SharedPref(this);
        api_interface = Api_Class.getClient().create(Api_Interface.class);
        rvDTerdisposisi = findViewById(R.id.rvDTerdisposisi);
        rvDTerdisposisi.setLayoutManager(layoutManager);
        tvLoadMore = findViewById(R.id.tvLoadMore);

        show_dokumen(offset,row_count);

        tvLoadMore.setOnClickListener(l->{
            offset = offset + 10;
            show_dokumen(offset,row_count);
        });

    }


    private void show_dokumen(int offset,int row_count)
    {
            Call<List<Ent_surat>> callListSurat = api_interface.show_all_dokumen_after_disposisi("5",offset,row_count);
            callListSurat.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().size() == 0 || response.body().size() < row_count || row_count == getIntent().getExtras().getInt("total"))
                        {
                            tvLoadMore.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            tvLoadMore.setVisibility(View.VISIBLE);
                        }

                        pagingDokumen.addAll(response.body());
                        List_Terdisposisi_Adapter list_terdisposisi_adapter = new List_Terdisposisi_Adapter(History_Disposisi_Activity.this,pagingDokumen);
                        rvDTerdisposisi.setAdapter(list_terdisposisi_adapter);
                    }
                    else
                    {
                        Log.i("list_dokumen","Koneksi gagal");
                    }

                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.e("list_dokumen","Koneksi gagal");
                }
            });


    }

    private void show_dokumen(String dari)
    {
        Call<List<Ent_surat>> callListSurat = api_interface.search_all_dokumen_after_disposisi_by_dari(dari);
        callListSurat.enqueue(new Callback<List<Ent_surat>>() {
            @Override
            public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                if(response.isSuccessful())
                {
                    tvLoadMore.setVisibility(View.INVISIBLE);
                    List<Ent_surat> listDokumen = response.body();
                    List_Terdisposisi_Adapter list_terdisposisi_adapter = new List_Terdisposisi_Adapter(History_Disposisi_Activity.this,listDokumen);
                    rvDTerdisposisi.setAdapter(list_terdisposisi_adapter);
                }
                else
                {
                    Log.i("list_dokumen","Koneksi gagal");
                }

            }

            @Override
            public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                Log.e("list_dokumen","Koneksi gagal");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by Surat Dari...");
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(History_Disposisi_Activity.this,Menu_Utama_Activity.class));
                finish();
                return true;
            case R.id.search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               show_dokumen(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(History_Disposisi_Activity.this,Menu_Utama_Activity.class));
        finish();
    }
}

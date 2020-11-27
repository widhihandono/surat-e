package com.surat.surate_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.surat.surate_app.Adapter.List_Dokumen_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dokumen_by_id_jenis_Activity extends AppCompatActivity {
    Display display;
    private RecyclerView rvDokumen_by_jenis;
    private RecyclerView.LayoutManager layoutManager;
    private Api_Interface api_interface;
    private int cek = -1;
    private SharedPref sharedPref;

    private TextView tvLoadMore;
    int offset = 0,row_count = 10;
    List<Ent_surat> pagingDokumen = new ArrayList<>();
    Snackbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen_by_id_jenis_);
        getSupportActionBar().setTitle("List Dokumen "+getIntent().getExtras().getString("jenis_dokumen"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);

        sharedPref = new SharedPref(this);
        api_interface = Api_Class.getClient().create(Api_Interface.class);
        rvDokumen_by_jenis = findViewById(R.id.rvDokumen_by_jenis);
        rvDokumen_by_jenis.setLayoutManager(layoutManager);
        tvLoadMore = findViewById(R.id.tvLoadMore);

        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        show_dokumen(getIntent().getExtras().getString("id_jenis_dokumen",""),offset,row_count);

        tvLoadMore.setOnClickListener(l->{
            offset = offset + 10;
//            Toast.makeText(Dokumen_activity.this,id_jenis_dokumen,Toast.LENGTH_LONG).show();
            show_dokumen(getIntent().getExtras().getString("id_jenis_dokumen",""),offset,row_count);
        });

    }

    private void show_dokumen(String id_jenis_dokumen, int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<List<Ent_surat>> callListSurat = api_interface.show_dokumen_before_disposisi_by_id_jenis("3",id_jenis_dokumen,offset,row_count);
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
                        List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_by_id_jenis_Activity.this,pagingDokumen);
                        rvDokumen_by_jenis.setAdapter(list_dokumen_adapter);
                    }
                    else
                    {
                        showSnackbar("Ada gangguan pada server, Coba beberapa saat lagi","Refresh");
                    }


                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                    showSnackbar("Network Failed","Refresh");
                }
            });
        }
        else
        {

            Call<List<Ent_surat>> callListSurat = api_interface.show_dokumen_before_disposisi_by_id_jenis("4",id_jenis_dokumen,offset,row_count);
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
                        List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_by_id_jenis_Activity.this,pagingDokumen);
                        rvDokumen_by_jenis.setAdapter(list_dokumen_adapter);

                    }
                    else
                    {
                        showSnackbar("Ada gangguan pada server, Coba beberapa saat lagi","Refresh");
                    }
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                    showSnackbar("Network Failed","Refresh");
                }
            });
        }

    }


    private void search_dokumen(String id_jenis_dokumen,String dari, int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<List<Ent_surat>> callListSurat = api_interface.search_dokumen_before_disposisi_by_id_jenis_dari("3",id_jenis_dokumen,dari,offset,row_count);
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
                        List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_by_id_jenis_Activity.this,pagingDokumen);
                        rvDokumen_by_jenis.setAdapter(list_dokumen_adapter);
                    }
                    else
                    {
                        showSnackbar("Ada gangguan pada server, Coba beberapa saat lagi","Refresh");
                    }

                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                    showSnackbar("Network Failed","Refresh");
                }
            });
        }
        else
        {

            Call<List<Ent_surat>> callListSurat = api_interface.search_dokumen_before_disposisi_by_id_jenis_dari("4",id_jenis_dokumen,dari,offset,row_count);
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
                        List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_by_id_jenis_Activity.this,pagingDokumen);
                        rvDokumen_by_jenis.setAdapter(list_dokumen_adapter);
                    }
                    else
                    {
                        showSnackbar("Ada gangguan pada server, Coba beberapa saat lagi","Refresh");
                    }

                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                    showSnackbar("Network Failed","Refresh");
                }
            });
        }

        pagingDokumen.clear();
    }

    private void showSnackbar(String text, String action)
    {
        bar = Snackbar.make(findViewById(R.id.sb_dokumen_id_jenis),text, Snackbar.LENGTH_INDEFINITE);
        View view = bar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);

        bar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
                Intent intent = new Intent(Dokumen_by_id_jenis_Activity.this,Dokumen_by_id_jenis_Activity.class);
                intent.putExtra("id_jenis_dokumen",getIntent().getExtras().getString("id_jenis_dokumen",""));
                intent.putExtra("jenis_dokumen",getIntent().getExtras().getString("jenis_dokumen",""));
                startActivity(intent);
                Animatoo.animateFade(Dokumen_by_id_jenis_Activity.this);
                finish();
            }
        }).setActionTextColor(Color.YELLOW);
        bar.show();
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

    private void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search_dokumen(getIntent().getExtras().getString("id_jenis_dokumen",""),s,offset,row_count);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Dokumen_by_id_jenis_Activity.this,Menu_Utama_Activity.class));
                finish();
                return true;
            case R.id.search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Dokumen_by_id_jenis_Activity.this,Menu_Utama_Activity.class));
        finish();
    }
}

package com.surat.surate_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.surat.surate_app.Adapter.List_Dokumen_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dokumen_activity extends AppCompatActivity {
Display display;
private RecyclerView rvDokumen;
private RecyclerView.LayoutManager layoutManager;
private Api_Interface api_interface;
private TextView tvJenisDokumen;
private int cek = -1;
private SharedPref sharedPref;

private TextView tvLoadMore;
int offset = 0,row_count = 10;
List<Ent_surat> pagingDokumen = new ArrayList<>();
private String id_jenis_dokumen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen_activity);
        getSupportActionBar().setTitle("List Dokumen "+getIntent().getExtras().getString("sifat_dokumen"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);

        sharedPref = new SharedPref(this);
        api_interface = Api_Class.getClient().create(Api_Interface.class);
        rvDokumen = findViewById(R.id.rvDokumen);
        tvJenisDokumen = findViewById(R.id.tvJenisDokumen);
        rvDokumen.setLayoutManager(layoutManager);
        tvLoadMore = findViewById(R.id.tvLoadMore);

        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        id_jenis_dokumen = getIntent().getExtras().getString("id_jenis_dokumen","");

        show_dokumen(getIntent().getExtras().getInt("id_sifat_dokumen",0),id_jenis_dokumen,offset,row_count);

        tvJenisDokumen.setOnClickListener(l->{
            show_dialog_jenisDokumen(cek);
        });

        tvLoadMore.setOnClickListener(l->{
            offset = offset + 10;
//            Toast.makeText(Dokumen_activity.this,id_jenis_dokumen,Toast.LENGTH_LONG).show();
            show_dokumen(getIntent().getExtras().getInt("id_sifat_dokumen",0),id_jenis_dokumen,offset,row_count);
        });
    }

    private void show_dokumen(int sifat_dokumen,String id_jenis_dokumen, int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<List<Ent_surat>> callListSurat = api_interface.show_dokumen_before_disposisi("3",sifat_dokumen,id_jenis_dokumen,offset,row_count);
            callListSurat.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {

                    if(response.body().size() == 0 || response.body().size() < row_count || row_count == getIntent().getExtras().getInt("total"))
                    {
                        tvLoadMore.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        tvLoadMore.setVisibility(View.VISIBLE);
                    }

                    pagingDokumen.addAll(response.body());
                    List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_activity.this,pagingDokumen);
                    rvDokumen.setAdapter(list_dokumen_adapter);
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                }
            });
        }
        else
        {

            Call<List<Ent_surat>> callListSurat = api_interface.show_dokumen_before_disposisi("4",sifat_dokumen,id_jenis_dokumen,offset,row_count);
            callListSurat.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                    if(response.body().size() == 0 || response.body().size() < row_count || row_count == getIntent().getExtras().getInt("total"))
                    {
                        tvLoadMore.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        tvLoadMore.setVisibility(View.VISIBLE);
                    }

                    pagingDokumen.addAll(response.body());
                    List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_activity.this,pagingDokumen);
                    rvDokumen.setAdapter(list_dokumen_adapter);
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                }
            });
        }

    }

    private void search_dokumen(int sifat_dokumen,String id_jenis_dokumen,String dari, int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<List<Ent_surat>> callListSurat = api_interface.search_dokumen_before_disposisi_by_dari("3",sifat_dokumen,id_jenis_dokumen,dari,offset,row_count);
            callListSurat.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {

                    if(response.body().size() == 0 || response.body().size() < row_count || row_count == getIntent().getExtras().getInt("total"))
                    {
                        tvLoadMore.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        tvLoadMore.setVisibility(View.VISIBLE);
                    }

                    pagingDokumen.addAll(response.body());
                    List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_activity.this,pagingDokumen);
                    rvDokumen.setAdapter(list_dokumen_adapter);
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                }
            });
        }
        else
        {

            Call<List<Ent_surat>> callListSurat = api_interface.search_dokumen_before_disposisi_by_dari("4",sifat_dokumen,id_jenis_dokumen,dari,offset,row_count);
            callListSurat.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                    if(response.body().size() == 0 || response.body().size() < row_count || row_count == getIntent().getExtras().getInt("total"))
                    {
                        tvLoadMore.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        tvLoadMore.setVisibility(View.VISIBLE);
                    }

                    pagingDokumen.addAll(response.body());
                    List_Dokumen_Adapter list_dokumen_adapter = new List_Dokumen_Adapter(Dokumen_activity.this,pagingDokumen);
                    rvDokumen.setAdapter(list_dokumen_adapter);
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.i("list_dokumen","Koneksi gagal");
                }
            });
        }

        pagingDokumen.clear();
    }


    private void show_dialog_jenisDokumen(int checkedItem)
    {
        Call<List<Ent_jenis_dokumen>> callJenisDokumen = api_interface.jenis_dokumen();
        callJenisDokumen.enqueue(new Callback<List<Ent_jenis_dokumen>>() {
            @Override
            public void onResponse(Call<List<Ent_jenis_dokumen>> call, Response<List<Ent_jenis_dokumen>> response) {
                if(response.isSuccessful())
                {
//                    String[] listItems = {"Undangan", "Nota Dinas", "FAX", "Surat Umum", "Proposal"};

                    List<Ent_jenis_dokumen> listJenisDokumen = response.body();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Dokumen_activity.this,android.R.layout.select_dialog_singlechoice);
                    for(int a=0;a<listJenisDokumen.size();a++)
                    {
                        arrayAdapter.add(listJenisDokumen.get(a).getJenis_dokumen());

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(Dokumen_activity.this);
                    builder.setTitle("Choose item");

                    builder.setSingleChoiceItems(arrayAdapter,cek, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                    bulan = which+1;

                            pagingDokumen.clear();
                            offset = 0;
                            row_count = 10;

                            cek = which;
                            tvJenisDokumen.setText(arrayAdapter.getItem(which));

                            id_jenis_dokumen = String.valueOf(which+1);

                            show_dokumen(getIntent().getExtras().getInt("id_sifat_dokumen",0),String.valueOf(which+1),0,10);

                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Ada masalah pada server, mohon hubungi developer",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<Ent_jenis_dokumen>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ada masalah koneksi, Coba lagi",Toast.LENGTH_LONG).show();
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

    private void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search_dokumen(getIntent().getExtras().getInt("id_sifat_dokumen",0),id_jenis_dokumen,s,offset,row_count);
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
                startActivity(new Intent(Dokumen_activity.this,Menu_Utama_Activity.class));
                finish();
                return true;
            case R.id.search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Dokumen_activity.this,Menu_Utama_Activity.class));
        finish();
    }
}

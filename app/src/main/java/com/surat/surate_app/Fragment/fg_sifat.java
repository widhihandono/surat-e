package com.surat.surate_app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.surat.surate_app.Adapter.List_Dokumen_Adapter;
import com.surat.surate_app.Adapter.MyAdapter;
import com.surat.surate_app.Adapter.MyAdapter_1;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Ent_sifat_surat;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Model.Ent_total_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.SQLite.Crud;
import com.surat.surate_app.Util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fg_sifat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fg_sifat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fg_sifat extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerview,rvDocument;
    Ent_sifat_surat mEntsifatsurat;
    private RecyclerView.LayoutManager layoutManager;
    private List_Dokumen_Adapter listDokumenAdapter;
    private Api_Interface api_interface;
    SharedPref sharedPref;
    Snackbar bar;
    int height=0,width=0;
    TextView version,tvLogout,tvLoadMore,tvUbahPass;

    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};

    int permsRequestCode = 200;
    Crud crudSqlite;

    String id_jenis="1";

    List<Ent_surat> pagingSurat = new ArrayList<>();
    int offset = 0,row_count = 10;

    public fg_sifat() {
        // Required empty public constructor
    }

    public static fg_sifat newInstance() {
        fg_sifat fragment = new fg_sifat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fg_sifat, container, false);

        sharedPref = new SharedPref(getActivity());
        api_interface = Api_Class.getClient().create(Api_Interface.class);
        crudSqlite = new Crud(getActivity());

        recyclerview = view.findViewById(R.id.recyclerview);
        rvDocument = view.findViewById(R.id.rvDocument);
        layoutManager = new LinearLayoutManager(getContext());
        rvDocument.setLayoutManager(layoutManager);
        tvLoadMore = view.findViewById(R.id.tvLoadMore);


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(mGridLayoutManager);
        show_total();
        show_documents(offset,row_count);


        tvLoadMore.setOnClickListener(l->{
            offset = offset + 10;
            show_documents(offset,row_count);
        });

        return view;
    }

    private void show_total()
    {
        if(sharedPref.sp.getString("role","").equals("ajudan"))
        {
            Call<Ent_total_surat> callTotal = api_interface.showTotalSurat("3");
            callTotal.enqueue(new Callback<Ent_total_surat>() {
                @Override
                public void onResponse(Call<Ent_total_surat> call, Response<Ent_total_surat> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResponse() == 2)
                        {
                            showDialogCekImei(response.body().getPesan());
                        }
                        else
                        {


                            //==============================Bawah====================================================================

                            List<Ent_sifat_surat> mSifatSuratList = new ArrayList<>();
                            mEntsifatsurat = new Ent_sifat_surat(1,"Amat Segera",response.body().getAmat_segera(),
                                    R.drawable.amat_segera_32px,"#673BB7","Amat_Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(2,"Segera",response.body().getSegera(),
                                    R.drawable.bell,"#FF453C","Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(3,"Rahasia",response.body().getRahasia(),
                                    R.drawable.mail,"#3E51B1","Rahasia");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(4,"Penting",response.body().getPenting(),
                                    R.drawable.rahasia_32px,"#D81B60","Penting");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(5,"Biasa",response.body().getBiasa(),
                                    R.drawable.bukan_rahasia_32px,"#033C70","Biasa");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(6,"Tembusan",response.body().getTembusan(),
                                    R.drawable.bell,"#096459","Tembusan");
                            mSifatSuratList.add(mEntsifatsurat);



                            MyAdapter myAdapter = new MyAdapter(getActivity(), mSifatSuratList);
                            recyclerview.setAdapter(myAdapter);
                        }

                    }
                    else
                    {
//                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                        Log.e("menu_utama","Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi");
                    }


                }

                @Override
                public void onFailure(Call<Ent_total_surat> call, Throwable t) {
                    Log.e("menu_utama","Gagal koneksi");
//                    showSnackbar("Network failed","Restart");
                }
            });
        }
        else
        {
            Call<Ent_total_surat> callTotal = api_interface.showTotalSurat("4");
            callTotal.enqueue(new Callback<Ent_total_surat>() {
                @Override
                public void onResponse(Call<Ent_total_surat> call, Response<Ent_total_surat> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResponse() == 2)
                        {
                            showDialogCekImei(response.body().getPesan());
                        }
                        else
                        {
                            //==============================Bawah====================================================================

                            List<Ent_sifat_surat> mSifatSuratList = new ArrayList<>();
                            mEntsifatsurat = new Ent_sifat_surat(1,"Amat Segera",response.body().getAmat_segera(),
                                    R.drawable.amat_segera_32px,"#673BB7","Amat_Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(2,"Segera",response.body().getSegera(),
                                    R.drawable.bell,"#FF453C","Segera");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(3,"Rahasia",response.body().getRahasia(),
                                    R.drawable.rahasia_32px,"#3E51B1","Rahasia");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(4,"Penting",response.body().getPenting(),
                                    R.drawable.mail,"#D81B60","Penting");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(5,"Biasa",response.body().getBiasa(),
                                    R.drawable.bukan_rahasia_32px,"#033C70","Biasa");
                            mSifatSuratList.add(mEntsifatsurat);
                            mEntsifatsurat = new Ent_sifat_surat(6,"Tembusan",response.body().getTembusan(),
                                    R.drawable.bell,"#096459","Tembusan");
                            mSifatSuratList.add(mEntsifatsurat);



                            MyAdapter myAdapter = new MyAdapter(getActivity(), mSifatSuratList);
                            recyclerview.setAdapter(myAdapter);
                        }

                    }
                    else
                    {
//                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                        Log.e("menu_utama","Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi");
                    }


                }

                @Override
                public void onFailure(Call<Ent_total_surat> call, Throwable t) {
                    Log.e("menu_utama","Gagal koneksi");
//                    showSnackbar("Network failed","Restart");
                }
            });
        }

    }

    private void show_documents(int offset,int row_count)
    {
        if(sharedPref.sp.getString("role","").equals("bupati"))
        {
            Call<List<Ent_surat>> callDocuments = api_interface.show_all_dokumen_before_disposisi(offset, row_count);
            callDocuments.enqueue(new Callback<List<Ent_surat>>() {
                @Override
                public void onResponse(Call<List<Ent_surat>> call, Response<List<Ent_surat>> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().size() == 0 || response.body().size() < row_count)
                        {
                            tvLoadMore.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            tvLoadMore.setVisibility(View.VISIBLE);
                        }

                        pagingSurat.addAll(response.body());
                        listDokumenAdapter = new List_Dokumen_Adapter(getContext(),pagingSurat);
                        rvDocument.setAdapter(listDokumenAdapter);
                    }
                    else
                    {
//                        showSnackbar("Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi","Refresh");
                        Log.e("menu_utama","Mohon maaf terjadi gangguan. Tunggu beberapa saat lagi");
                    }
                }

                @Override
                public void onFailure(Call<List<Ent_surat>> call, Throwable t) {
                    Log.e("menu_utama","show documents");
//                    showSnackbar("Network failed","Restart");
                }
            });
        }

    }

    private void showDialogCekImei(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    private void showSnackbar(String text, String action)
//    {
//
//        bar = Snackbar.make(getActivity().findViewById(R.id.sb_menu_utama),text, Snackbar.LENGTH_INDEFINITE);
//        bar.setAction(action, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bar.dismiss();
//                startActivity(new Intent(getContext(),Menu_Utama_Activity.class));
//                getActivity().finish();
//            }
//        });
//        bar.show();
//
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

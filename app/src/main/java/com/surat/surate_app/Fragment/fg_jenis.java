package com.surat.surate_app.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.surat.surate_app.Adapter.List_Jenis_Dokumen_Adapter;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_jenis_dokumen;
import com.surat.surate_app.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fg_jenis.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fg_jenis#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fg_jenis extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Api_Interface api_interface;
    private RecyclerView rvJenisDokumen;
    private RecyclerView.LayoutManager layoutManager;
    private List_Jenis_Dokumen_Adapter dokumen_adapter;

    public fg_jenis() {
        // Required empty public constructor
    }

    public static fg_jenis newInstance() {
        fg_jenis fragment = new fg_jenis();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fg_jenis, container, false);

            layoutManager = new LinearLayoutManager(getContext());
            api_interface = Api_Class.getClient().create(Api_Interface.class);
            rvJenisDokumen = view.findViewById(R.id.rvJenisDokumen);
            rvJenisDokumen.setLayoutManager(layoutManager);

        Call<List<Ent_jenis_dokumen>> listJenis = api_interface.jenis_dokumen();
        listJenis.enqueue(new Callback<List<Ent_jenis_dokumen>>() {
            @Override
            public void onResponse(Call<List<Ent_jenis_dokumen>> call, Response<List<Ent_jenis_dokumen>> response) {
                if(response.isSuccessful())
                {
                    List<Ent_jenis_dokumen> listJenisDok = response.body();

                    Toast.makeText(getContext(),""+listJenisDok.size(),Toast.LENGTH_LONG).show();
                    dokumen_adapter = new List_Jenis_Dokumen_Adapter(getContext(),listJenisDok);
                    rvJenisDokumen.setAdapter(dokumen_adapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Maaf sedang ada gangguan di server, Coba lagi nanti",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ent_jenis_dokumen>> call, Throwable t) {
                Toast.makeText(getContext(),"Sedang ada gangguan pada koneksi, Coba lagi nanti",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

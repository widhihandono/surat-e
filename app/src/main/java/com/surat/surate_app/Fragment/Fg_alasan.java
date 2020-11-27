package com.surat.surate_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fg_alasan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg_alasan extends Fragment {

    private Fg_dokumen.OnFragmentInteractionListener mListener;
    private static String alasanx;
    private static int id_disposisix;
    private TextView tvAlasanDitolakBupati,tvLanjtukan;
    private Api_Interface api_interface;

    public Fg_alasan() {
        // Required empty public constructor
    }

    public static Fg_alasan newInstance(int id_disposisi,String alasan) {
        Fg_alasan fragment = new Fg_alasan();
        Bundle args = new Bundle();
        id_disposisix = id_disposisi;
        alasanx = alasan;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fg_alasan, container, false);
        api_interface = Api_Class.getClient().create(Api_Interface.class);

        tvAlasanDitolakBupati = view.findViewById(R.id.tvAlasanDitolakBupati);
        tvLanjtukan = view.findViewById(R.id.tvLanjutkan);

        if(alasanx != null)
        {
            tvAlasanDitolakBupati.setText(alasanx);
        }
        else
        {
            tvAlasanDitolakBupati.setText("-");
        }

        tvLanjtukan.setOnClickListener(l->{
            Call<Ent_surat> forward = api_interface.forward_dokumen_ajudan(id_disposisix,"YES");
            forward.enqueue(new Callback<Ent_surat>() {
                @Override
                public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResponse() == 1)
                        {
                            Toast.makeText(getActivity(),"Sukses diteruskan ke TU Bupati",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
                            getActivity().finish();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Gagal diteruskan ke TU Bupati",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Maaf, Sedang ada gangguan dengan server",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Ent_surat> call, Throwable t) {
                    Toast.makeText(getActivity(),"Network Failed",Toast.LENGTH_LONG).show();
                }
            });
        });
        return view;
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(Uri uri);
    }
}

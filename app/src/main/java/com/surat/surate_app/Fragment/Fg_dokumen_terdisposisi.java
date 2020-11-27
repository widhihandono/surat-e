package com.surat.surate_app.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fg_dokumen_terdisposisi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fg_dokumen_terdisposisi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg_dokumen_terdisposisi extends Fragment {

    PDFView pdfView;
    private OnFragmentInteractionListener mListener;
    private static String path_filex,pathx;
    ProgressDialog mProgressDialog;
    ProgressBar progressBar;
    View view_pgBar;
    ProgressBar PgBar;
    Dialog dialog;
    TextView tvProgress,tvRefresh,tvCancel;


    public Fg_dokumen_terdisposisi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fg_dokumen.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg_dokumen_terdisposisi newInstance(String path_file,String path) {
        Fg_dokumen_terdisposisi fragment = new Fg_dokumen_terdisposisi();
        Bundle args = new Bundle();
        path_filex = path_file;
        pathx = path;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fg_dokumen_terdisposisi, container, false);

        view_pgBar = getLayoutInflater().inflate(R.layout.progress_bar_layout,null);
        PgBar = view_pgBar.findViewById(R.id.pgBar);
        dialog = new Dialog(getActivity());

        pdfView = view.findViewById(R.id.pdfView);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);

        if(file.exists())
        {
            pdfView.fromFile(file).load();
        }
        else
        {
            new DownloadFile().execute(Api_Class.PDF_URL+pathx+"/"+path_filex);
        }

        return view;
    }

    // DownloadFile AsyncTask
    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(getActivity());
            view_pgBar = getLayoutInflater().inflate(R.layout.progress_bar_layout,null);
            PgBar = view_pgBar.findViewById(R.id.pgBar);

            tvProgress = view_pgBar.findViewById(R.id.tvProgress);
            tvCancel = view_pgBar.findViewById(R.id.tvCancel);
            tvRefresh = view_pgBar.findViewById(R.id.tvRefresh);


            dialog.setTitle("Download File PDF");
            dialog.setCancelable(false);
            PgBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            PgBar.setIndeterminate(false);
            PgBar.setMax(100);
            PgBar.setVisibility(View.VISIBLE);


            tvCancel.setOnClickListener(l->{
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);
                file.delete();
                dialog.dismiss();
                getActivity().onBackPressed();
                getActivity().finish();
            });

            tvRefresh.setOnClickListener(l->{
                dialog.dismiss();
                new Fg_dokumen_terdisposisi.DownloadFile().execute(Api_Class.PDF_URL+pathx+"/"+path_filex);
            });

            dialog.setContentView(view_pgBar);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();

            // Create progress dialog

//            mProgressDialog = new ProgressDialog(getActivity());
//            // Set your progress dialog Title
//            mProgressDialog.setTitle("Download File PDF");
//            // Set your progress dialog Message
//            mProgressDialog.setMessage("Downloading, Please Wait!");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setMax(100);
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            // Show progress dialog
//            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);

                URLConnection connection = url.openConnection();
                connection.connect();

                // Detect the file lenghth
                int fileLength = connection.getContentLength();

                // Locate storage location
                String filepath = Environment.getExternalStorageDirectory()
                        .getPath();

                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Save the downloaded file
                OutputStream output = new FileOutputStream(filepath + "/"
                        + path_filex);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress

                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }

                // Close connection
                output.flush();
                output.close();
                input.close();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);
                pdfView.fromFile(file).load();
                dialog.dismiss();
            } catch (Exception e) {
                // Error Log
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
//            mProgressDialog.setProgress(progress[0]);
            PgBar.setProgress(progress[0]);
            tvProgress.setText(progress[0]+"/100");
            // Dismiss the progress dialog
//            mProgressDialog.dismiss();

        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

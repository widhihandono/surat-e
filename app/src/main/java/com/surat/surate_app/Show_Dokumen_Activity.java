package com.surat.surate_app;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Fragment.Fg_dokumen;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.Model.Message;
import com.surat.surate_app.Model.NotifyData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Show_Dokumen_Activity extends AppCompatActivity {
    PDFView pdfView;
    ProgressDialog mProgressDialog;
    ProgressBar progressBar;
    View view_pgBar;
    ProgressBar PgBar;
    Dialog dialog;
    TextView tvProgress;

    private Api_Interface api_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__dokumen_);
        getSupportActionBar().setTitle("Show Dokumen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view_pgBar = getLayoutInflater().inflate(R.layout.progress_bar_layout,null);
        PgBar = view_pgBar.findViewById(R.id.pgBar);
        dialog = new Dialog(this);

        pdfView = findViewById(R.id.pdfView);
        api_interface = Api_Class.getClient().create(Api_Interface.class);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + getIntent().getExtras().getString("path_file",""));

        if(file.exists())
        {
            pdfView.fromFile(file).load();
        }
        else
        {
            new DownloadFile().execute(Api_Class.PDF_URL+getIntent().getExtras().getString("path","")+"/"+getIntent().getExtras().getString("path_file",""));
        }

    }


    // DownloadFile AsyncTask
    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Show_Dokumen_Activity.this);
            view_pgBar = getLayoutInflater().inflate(R.layout.progress_bar_layout,null);
            PgBar = view_pgBar.findViewById(R.id.pgBar);

            tvProgress = view_pgBar.findViewById(R.id.tvProgress);

            dialog.setTitle("Download File PDF");
            dialog.setCancelable(false);
            PgBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            PgBar.setIndeterminate(false);
            PgBar.setMax(100);
            PgBar.setVisibility(View.VISIBLE);

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
                        + getIntent().getExtras().getString("path_file",""));

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
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + getIntent().getExtras().getString("path_file",""));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.send_to,menu);
        MenuItem item = menu.findItem(R.id.tolak);
        if(getIntent().getExtras().getString("status","").equals("4"))
        {
            item.setVisible(false);
        }
        else
        {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.send_to == item.getItemId())
        {
            showDialogSend_to_bupati();
//            sendNotif();
//            send_notif();
            return true;
        }
        else if(R.id.tolak == item.getItemId())
        {
            Intent intent = new Intent(Show_Dokumen_Activity.this,Penolakan_Bupati_Activity.class);
            intent.putExtra("id_disposisi",getIntent().getExtras().getInt("id_disposisi",0));
            startActivity(intent);

        }
        else if(android.R.id.home == item.getItemId())
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogSend_to_bupati(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin Ingin Diteruskan ke Bupati ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk teruskan")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(Show_Dokumen_Activity.this,String.valueOf(getIntent().getExtras().getInt("id_disposisi",0)),Toast.LENGTH_LONG).show();
                        Call<Ent_surat> send_to_bupati = api_interface.send_to_bupati(getIntent().getExtras().getInt("id_disposisi",0));
                        send_to_bupati.enqueue(new Callback<Ent_surat>() {
                            @Override
                            public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
                                if(response.isSuccessful())
                                {
                                    if(response.body().getResponse() == 1)
                                    {
//                                        sendNotif();
                                        send_notif();
                                        Toast.makeText(Show_Dokumen_Activity.this,"Berhasil Diteruskan ke Bupati",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Menu_Utama_Activity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(Show_Dokumen_Activity.this,"Gagal Diteruskan ke Bupati",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Show_Dokumen_Activity.this,"Gagal diteruskan, Server Error",Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Ent_surat> call, Throwable t) {
                                Toast.makeText(Show_Dokumen_Activity.this,"Gagal diteruskan, Network Failed !",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    //Send Notification
    private void sendNotif()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "key=AAAAoKD6qN8:APA91bGcdtBQEfUGht5qh_jWgNnNko6JAVmG6QcEzEyywrvImGQpajf1HApL_LpZ2_hy1ryCHwE1rKtDl1kTYAiF6Q3-hf6L0XipPcg_h4ehUmLnBUvVWYgomPqtm09hlEDu2egxX79Z"); // <-- this is the important line
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com")//url of FCM message server
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//use for convert JSON file into object
                .build();

        // prepare call in Retrofit 2.0
        api_interface = retrofit.create(Api_Interface.class);

        //for messaging server
        NotifyData notifydata = new NotifyData(getIntent().getExtras().getString("dari"),getIntent().getExtras().getString("perihal"),
                "bupati",getIntent().getExtras().getString("perihal"),getIntent().getExtras().getString("tanggal_surat"),
                getIntent().getExtras().getString("dari"),String.valueOf(getIntent().getExtras().getInt("id_dokumen")),
                String.valueOf(getIntent().getExtras().getInt("id_disposisi")));

        Call<Message> call2 = api_interface.sendMessage(new Message("/topics/bupati", notifydata));

        call2.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                Log.d("Response ", "onResponse");
                Toast.makeText(getApplicationContext(),"Terkirim",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("Response ", "onFailure");
                Toast.makeText(getApplicationContext(),"onFailure",Toast.LENGTH_LONG).show();
            }
        });
    }
    //==========================================================


    private void send_notif()
    {
        Call<Ent_surat> send_notif = api_interface.sendNotif(getIntent().getExtras().getString("dari",""),getIntent().getExtras().getString("perihal",""));
        send_notif.enqueue(new Callback<Ent_surat>() {
            @Override
            public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 1)
                    {
//                        sendNotif();
                        Toast.makeText(Show_Dokumen_Activity.this,"Terikirim",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Show_Dokumen_Activity.this,"Gagal",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Show_Dokumen_Activity.this,"Gagal diteruskan, Server Error",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Ent_surat> call, Throwable t) {
                Toast.makeText(Show_Dokumen_Activity.this,"Gagal diteruskan, Network Failed !",Toast.LENGTH_LONG).show();
            }
        });
    }


}

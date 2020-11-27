package com.surat.surate_app.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Menu_Utama_Activity;
import com.surat.surate_app.Model.Ent_surat;
import com.surat.surate_app.R;
import com.surat.surate_app.SQLite.Crud;
import com.surat.surate_app.Util.DrawableImageView;
import com.surat.surate_app.Util.LockableNestedScrollView;
import com.surat.surate_app.Util.MyHttpEntity;
import com.surat.surate_app.Util.SharedPref;
import com.surat.surate_app.Util.ZoomLinearLayout;
import com.surat.surate_app.Util.imageUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fg_disposisi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fg_disposisi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg_disposisi extends Fragment {
    private ImageView logo;
    private TextView tvClose,tvSizeNumber,tvSizeNumberEraser,tvCloseEraser,
            disposisi,eraser,eraserAll,clear,zoom,pen,save;
    private Button btnMerah,btnBiru,btnHitam;
    private SeekBar seeBarSize,seekBarSizeEraser;
    private TextView sekda_2,bagian_2,dinas_2,kantor_2,badan_2,upload;
    private CheckBox cbAmatSegera,cbSegera,cbBiasa,cbRahasia,cbBukanRahasia;
    private TableLayout tblDisposisiUntuk;
    private ZoomLinearLayout lnDisposisi;
    private LinearLayout lnDispo,lnColor,lnEraser;
    Display display;
    private boolean detectOrientation = true;
    int finalHeight,finalWidth;
    private DrawableImageView drawView;
    //Animation
    private Boolean isFabOpen = false,isFabOpenClear = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward,
            fab_openClear,fab_closeClear,rotate_forwardClear,rotate_backwardClear;
    private FloatingActionButton fabNormal,fabDraw,fabClear,fabAdd,fabSave,fabEraser,fabEraserAll,fabUpload;
    private LockableNestedScrollView nScroll;
    private static String darix,nomor_suratx,tanggal_suratx,perihalx,diterimaTglx,
            no_agendax,path_disposisix,pathx,path_filex,statusx,tgl_kirim_tu_umumx,tgl_kirim_tu_bupatix,
            tgl_kirim_bupatix,time_dokumenx,jenis_dokumenx,sifat_dokumenx,id_jenis_dokumenx;
    private static int id_disposisix,id_dokumenx,id_sifat_dokumenx;
    Bitmap bitmap,alteredBitmap;
    Api_Interface api_interface;
    private boolean hideShowEdit = false,hideShowEditEraser = false;

    Crud crudSqlite;

    private static final String SERVER_PATH = Api_Class.BASE_URL+"Api_dokumen/update_disposisi";

    private OnFragmentInteractionListener mListener;

    public Fg_disposisi() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fg_disposisi.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg_disposisi newInstance(String dari,String nomor_surat,String tanggal_surat,
                                           String perihal,String diterimaTgl,String no_agenda,
                                           int id_disposisi,String path_disposisi,String path,String path_file,String status,int id_dokumen,String tgl_kirim_tu_umum,
                                           String tgl_kirim_tu_bupati,String tgl_kirim_bupati,String time_dokumen,
                                           String id_jenis_dokumen,String jenis_dokumen,int id_sifat_dokumen,String sifat_dokumen) {
        Fg_disposisi fragment = new Fg_disposisi();
        Bundle args = new Bundle();
        darix = dari;
        nomor_suratx = nomor_surat;
        tanggal_suratx = tanggal_surat;
        perihalx = perihal;
        diterimaTglx = diterimaTgl;
        no_agendax = no_agenda;
        id_disposisix = id_disposisi;
        path_filex = path_file;
        statusx = status;
        id_dokumenx = id_dokumen;
        tgl_kirim_tu_umumx = tgl_kirim_tu_umum;
        tgl_kirim_tu_bupatix = tgl_kirim_tu_bupati;
        tgl_kirim_bupatix = tgl_kirim_bupati;
        time_dokumenx = time_dokumen;
        id_jenis_dokumenx = id_jenis_dokumen;
        jenis_dokumenx = jenis_dokumen;
        id_sifat_dokumenx = id_sifat_dokumen;
        sifat_dokumenx = sifat_dokumen;
        pathx = path;
        path_disposisix = path_disposisi;

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
        View view = inflater.inflate(R.layout.fragment_fg_disposisi, container, false);

        api_interface = Api_Class.getClient().create(Api_Interface.class);
        crudSqlite = new Crud(getContext());

        disposisi = view.findViewById(R.id.disposisi);
        fabNormal = view.findViewById(R.id.fabNormal);
        fabDraw = view.findViewById(R.id.fabDraw);
        fabClear = view.findViewById(R.id.fabClear);
        fabAdd = view.findViewById(R.id.fabAdd);
        fabEraser = view.findViewById(R.id.fabEraser);
        fabEraserAll = view.findViewById(R.id.fabEraserAll);
        fabSave = view.findViewById(R.id.fabSave);
        btnBiru = view.findViewById(R.id.btnBiru);
        btnMerah = view.findViewById(R.id.btnMerah);
        btnHitam = view.findViewById(R.id.btnHitam);
        tvClose = view.findViewById(R.id.tvClose);
        seeBarSize = view.findViewById(R.id.seekBarSize);
        tvSizeNumber = view.findViewById(R.id.tvSizeNumber);
        lnColor = view.findViewById(R.id.lnColor);
        lnEraser = view.findViewById(R.id.lnEraser);
        seekBarSizeEraser = view.findViewById(R.id.seekBarSizeEraser);
        tvCloseEraser = view.findViewById(R.id.tvCloseEraser);
        tvSizeNumberEraser = view.findViewById(R.id.tvSizeNumberEraser);
        eraser = view.findViewById(R.id.eraser);
        eraserAll = view.findViewById(R.id.eraserAll);
        clear = view.findViewById(R.id.clear);
        zoom = view.findViewById(R.id.zoom);
        pen = view.findViewById(R.id.pen);
        save = view.findViewById(R.id.save);
        upload = view.findViewById(R.id.upload);
        fabUpload = view.findViewById(R.id.fabUpload);


        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);

        fab_openClear = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_closeClear = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forwardClear = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backwardClear = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);

        fabAdd.setOnClickListener(l->{
            animateFAB();
        });


        //get Size of screen
        display = getActivity().getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //==============================================


        drawView =  view.findViewById(R.id.drawView);
        ViewTreeObserver vto = drawView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                drawView.getViewTreeObserver().removeOnPreDrawListener(this);
                finalHeight = drawView.getMeasuredHeight();
                finalWidth = drawView.getMeasuredWidth();
                bitmap = loadBitmapFromView(drawView,finalWidth,finalHeight);
                alteredBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
                Bitmap bmp = alteredBitmap;
                drawView.setNewImage(bmp,bmp);
                return true;
            }
        });

        tvSizeNumber.setText("5");
        seeBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSizeNumber.setText(String.valueOf(progress));
                drawView.sizePaint(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tvSizeNumberEraser.setText("5");
        seekBarSizeEraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSizeNumberEraser.setText(String.valueOf(progress));
                drawView.startEraser(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnBiru.setOnClickListener(l->{
            drawView.setColorPaint(Color.BLUE);
        });

        btnMerah.setOnClickListener(l->{
            drawView.setColorPaint(Color.RED);
        });

        btnHitam.setOnClickListener(l->{
            drawView.setColorPaint(Color.BLACK);
        });

        tvClose.setOnClickListener(l->{
            lnColor.setVisibility(View.GONE);
            hideShowEdit = false;
        });

        tvCloseEraser.setOnClickListener(l->{
            lnColor.setVisibility(View.GONE);
            hideShowEdit = false;
        });

        disposisi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                Bitmap b = loadBitmapFromView(a, lnDispo.getWidth(), lnDispo.getHeight());
//                createPdf(alteredBitmap);

            }
        });

        fabNormal.setOnClickListener(l->{
//            drawView.setDrawImage(false);
//            drawView.setDragImage(true);
            Bitmap bmp = loadBitmapFromView(drawView,drawView.getWidth(),drawView.getHeight());
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = LayoutInflater.from(getActivity()).inflate(R.layout.show_zoom_image_dialog,null);
            PhotoView photoView = mView.findViewById(R.id.imgPhotoView);
            photoView.setImageBitmap(bmp);
            mBuilder.setView(mView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        });

        fabDraw.setOnClickListener(l->{
            drawView.setDrawImage(true);
            drawView.setDragImage(false);

            if(hideShowEdit == false)
            {
                seeBarSize.setProgress(5);
                lnColor.setVisibility(View.VISIBLE);
                drawView.setColorPaint(Color.BLUE);
                lnEraser.setVisibility(View.GONE);
                drawView.sizePaint(5);
                hideShowEditEraser = false;
                hideShowEdit = true;
            }
            else
            {
                lnColor.setVisibility(View.GONE);
                hideShowEdit = false;
            }
        });

        fabClear.setOnClickListener(l->{
            animateFABClear();
        });

        fabEraser.setOnClickListener(l->{
            if(hideShowEditEraser == false)
            {
                lnEraser.setVisibility(View.VISIBLE);
                lnColor.setVisibility(View.GONE);
                drawView.startEraser(5);
                seekBarSizeEraser.setProgress(5);
//                drawView.sizePaint(5);
                hideShowEdit = false;
                hideShowEditEraser = true;
            }
            else
            {
                lnEraser.setVisibility(View.GONE);
                hideShowEditEraser = false;
            }
        });

        fabEraserAll.setOnClickListener(l->{
            drawView.setColorPaint(Color.WHITE);
            seekBarSizeEraser.setProgress(5);
            seeBarSize.setProgress(5);
            drawView.sizePaint(5);
            lnEraser.setVisibility(View.GONE);
            drawView.setClearImage(finalWidth,finalHeight);

        });

        fabSave.setOnClickListener(l->{
             input_disposisi_lokal();

//            getActivity().finish();
        });

        tvCloseEraser.setOnClickListener(l->{
            lnEraser.setVisibility(View.GONE);
            hideShowEditEraser = false;
        });

        fabUpload.setOnClickListener(l->{
            showDialogKirim();
        });


        return view;
    }


    private File storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return pictureFile;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("error", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("error", "Error accessing file: " + e.getMessage());
        }

        return pictureFile;
    }


    private File getFile()
    {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName = id_dokumenx+".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName = id_dokumenx+".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        if(mediaFile.exists())
        {
            mediaFile.delete();
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        }
        return mediaFile;
    }


    private void input_disposisi_lokal()
    {
        if(storeImage(encodePhoto_bitmap()) != null)
        {
            if(crudSqlite.getData_surat_by_id(id_dokumenx).size() > 0)
            {
                crudSqlite.update_dokumen_by_id(id_dokumenx,id_dokumenx+".jpg");
                Toast.makeText(getActivity(),"Data berhasil di update",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(crudSqlite.InsertData(darix,nomor_suratx,tanggal_suratx,perihalx,diterimaTglx,no_agendax,id_disposisix,
                        path_disposisix,pathx,path_filex,statusx,id_dokumenx,tgl_kirim_tu_umumx,tgl_kirim_tu_bupatix,tgl_kirim_bupatix,time_dokumenx,
                        id_jenis_dokumenx,jenis_dokumenx,id_sifat_dokumenx,sifat_dokumenx,id_dokumenx+".jpg"))
                {
                    Toast.makeText(getContext(),"Data berhasil disimpan di Terdisposisi belum kirim",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getContext(),"Data gagal disimpan",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Toast.makeText(getContext(),"Data gagal disimpan",Toast.LENGTH_LONG).show();
        }


    }

    private void input_disposisi()
    {
        Call<Ent_surat> callInput = api_interface.update_disposisi(id_disposisix,encodePhoto());
        callInput.enqueue(new Callback<Ent_surat>() {
            @Override
            public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 1)
                    {

                        Toast.makeText(getActivity(),"Sukses Upload",Toast.LENGTH_LONG).show();
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);
                        if(file.exists())
                        {
                            file.delete();
                            callBroadCast();
                            crudSqlite.hapus_dokumen_by_id(id_dokumenx);
                            startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
                            getActivity().finish();
                        }
                        else if(response.body().getResponse() == 2)
                        {
                            showDialogKeyAccess(response.body().getPesan());
                        }
                        else
                        {
                            startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
                            getActivity().finish();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Gagal input",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),response.message(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Ent_surat> call, Throwable t) {
                Toast.makeText(getActivity(),"Kesalahan koneksi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private class UploadAsyncTas_dispo extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = new DefaultHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String encode_photo;
        int id_disposisi;

        private UploadAsyncTas_dispo(Context context,int id_disposisi,String encode_photo) {
            this.context = context;
            this.id_disposisi = id_disposisi;
            this.encode_photo = encode_photo;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseString = 0;


            try {
                HttpPost httpPost = new HttpPost(SERVER_PATH);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("id_disposisi", String.valueOf(id_disposisi));
                multipartEntityBuilder.addTextBody("image_disposisi", encode_photo);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
//                        Log.i("BERHASIL CUY", String.valueOf(myObject.getInt("status")));
                    responseString = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseString = 0;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(getActivity(),"Sukses Upload",Toast.LENGTH_LONG).show();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);
                if(file.exists())
                {
                    file.delete();
                    callBroadCast();
                    getFile().delete();
                    crudSqlite.hapus_dokumen_by_id(id_dokumenx);
                    startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
                    getActivity().finish();
                }
//                else if(result == 2)
//                {
//                    showDialogKeyAccess(keterangan);
//                }
                else
                {
                    startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
                    getActivity().finish();
                }

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(getActivity(),"Gagal Upload",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    private void showDialogKeyAccess(String pesan){
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


//    private void input_disposisi()
//    {
//        Call<Ent_surat> callInput = api_interface.update_disposisi(id_disposisix,encodePhoto());
//        callInput.enqueue(new Callback<Ent_surat>() {
//            @Override
//            public void onResponse(Call<Ent_surat> call, Response<Ent_surat> response) {
//                if(response.isSuccessful())
//                {
//                    if(response.body().getResponse() == 1)
//                    {
//                        Toast.makeText(getActivity(),"Sukses input",Toast.LENGTH_LONG).show();
//                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + path_filex);
//                        if(file.exists())
//                        {
//                            file.delete();
//                            callBroadCast();
//
//                        }
//                        startActivity(new Intent(getActivity(), Menu_Utama_Activity.class));
//                        getActivity().finish();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(),"Gagal input",Toast.LENGTH_LONG).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Ent_surat> call, Throwable t) {
//                Toast.makeText(getActivity(),"Kesalahan koneksi",Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public static Bitmap imageViewToBitmap(DrawableImageView view) {
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        return bitmap;
    }

    private void callBroadCast()
    {
        if(Build.VERSION.SDK_INT >= 14)
        {
            MediaScannerConnection.scanFile(getActivity(), new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        }
        else
        {
            Log.e("-->","< 14");
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }

    }

    public void animateFABClear(){

        if(isFabOpenClear){
            fabClear.startAnimation(rotate_backwardClear);
            fabEraser.startAnimation(fab_closeClear);
            eraser.startAnimation(fab_closeClear);
            fabEraserAll.startAnimation(fab_closeClear);
            eraserAll.startAnimation(fab_closeClear);
            fabEraser.setClickable(false);
            fabEraserAll.setClickable(false);
            isFabOpenClear = false;
            Log.d("Raj", "close");

        } else {
            fabClear.startAnimation(rotate_forwardClear);
            fabEraser.startAnimation(fab_openClear);
            eraser.startAnimation(fab_openClear);
            fabEraserAll.startAnimation(fab_openClear);
            eraserAll.startAnimation(fab_openClear);
            fabEraser.setClickable(true);
            fabEraserAll.setClickable(true);
            isFabOpenClear = true;
            Log.d("Raj","open");

        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fabAdd.startAnimation(rotate_backward);
            fabUpload.startAnimation(fab_close);
            upload.startAnimation(fab_close);
            fabNormal.startAnimation(fab_close);
            zoom.startAnimation(fab_close);
            fabDraw.startAnimation(fab_close);
            pen.startAnimation(fab_close);
            fabClear.startAnimation(fab_close);
            clear.startAnimation(fab_close);
            fabSave.startAnimation(fab_close);
            save.startAnimation(fab_close);
            fabUpload.setClickable(false);
            fabNormal.setClickable(false);
            fabDraw.setClickable(false);
            fabClear.setClickable(false);
            fabSave.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

            fabEraser.startAnimation(fab_closeClear);
            eraser.startAnimation(fab_closeClear);
            fabEraserAll.startAnimation(fab_closeClear);
            eraserAll.startAnimation(fab_closeClear);
            fabEraser.setClickable(false);
            fabEraserAll.setClickable(false);
            isFabOpenClear = false;
            Log.d("Raj", "close");

        } else {
            fabAdd.startAnimation(rotate_forward);
            fabUpload.startAnimation(fab_open);
            upload.startAnimation(fab_open);
            fabNormal.startAnimation(fab_open);
            zoom.startAnimation(fab_open);
            fabDraw.startAnimation(fab_open);
            pen.startAnimation(fab_open);
            fabClear.startAnimation(fab_open);
            clear.startAnimation(fab_open);
            fabSave.startAnimation(fab_open);
            save.startAnimation(fab_open);
            fabNormal.setClickable(true);
            fabDraw.setClickable(true);
            fabClear.setClickable(true);
            fabSave.setClickable(true);
            fabUpload.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        display = getActivity().getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this,String.valueOf(width) , Toast.LENGTH_SHORT).show();
            tblDisposisiUntuk.getLayoutParams().width = width/2;
//            tblDisposisiUntuk.requestLayout();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this,String.valueOf(width) , Toast.LENGTH_SHORT).show();
            tblDisposisiUntuk.getLayoutParams().width = (width/2)-90;
//            tblDisposisiUntuk.requestLayout();

        }
    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(Bitmap bitmap){
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


//        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, false);


        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = "/sdcard/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(getActivity(), "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getActivity(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showDialogKirim(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title dialog
        alertDialogBuilder.setTitle("Kirim Ke Tu Bupati ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk kirim")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
//                        input_disposisi();

                        UploadAsyncTas_dispo upload = new UploadAsyncTas_dispo(getActivity(),id_disposisix,encodePhoto());
                        upload.execute();
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


    public String encodePhoto()
    {
        bitmap = loadBitmapFromView(drawView,drawView.getWidth(),drawView.getHeight());
        return imageUtils.bitmapToBase64String(bitmap,100);
    }

    public Bitmap encodePhoto_bitmap()
    {
        bitmap = loadBitmapFromView(drawView,drawView.getWidth(),drawView.getHeight());
        return bitmap;
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

package com.surat.surate_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.surat.surate_app.Api.Api_Class;
import com.surat.surate_app.Api.Api_Interface;
import com.surat.surate_app.Model.Ent_User;
import com.surat.surate_app.Util.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
private EditText etUsername,etPass;
private TextView tvLogin;
private SharedPref sharedPref;
Snackbar bar;
private Api_Interface api_interface;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
    int permsRequestCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);
        api_interface = Api_Class.getClient().create(Api_Interface.class);

        etUsername = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        tvLogin = findViewById(R.id.tvLogin);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }


        tvLogin.setOnClickListener(l->{
            if(etUsername.getText().toString().isEmpty())
            {
                showSnackbar("Username tidak boleh kosong","Ok");
            }
            else if(etPass.getText().toString().isEmpty())
            {
                showSnackbar("Password tidak boleh kosong","Ok");
            }
            else
            {
                login(etUsername.getText().toString(),etPass.getText().toString());
            }
        });

    }

    private void login(String username, String password)
    {
        Call<Ent_User> login = api_interface.Login(username,password);

        login.enqueue(new Callback<Ent_User>() {
            @Override
            public void onResponse(Call<Ent_User> call, Response<Ent_User> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getResponse() == 1)
                    {
                        if(response.body().getLevel().equals("bupati"))
                        {
                            sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,true);
                            sharedPref.saveSPString("role","bupati");
                            sharedPref.saveSPString("username",response.body().getUsername());
                            FirebaseMessaging.getInstance().subscribeToTopic("bupati");
                            startActivity(new Intent(Login_Activity.this,Menu_Utama_Activity.class));
                            finish();
                        }
                        else if(response.body().getLevel().equals("ajudan_bupati"))
                        {
                            sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,true);
                            sharedPref.saveSPString("role","ajudan");
                            sharedPref.saveSPString("username",response.body().getUsername());
                            FirebaseMessaging.getInstance().subscribeToTopic("ajudan");
                            startActivity(new Intent(Login_Activity.this,Menu_Utama_Activity.class));
                            finish();
                        }
                    }
                    else if(response.body().getResponse() == 2)
                    {
                        showSnackbar("User tidak ada","Ok");
                    }
                    else if(response.body().getResponse() == 0)
                    {
                        showSnackbar("Password yang anda masukkan salah","Ok");
                    }
                }
                else
                {
                    showSnackbar("Maaf terjadi gangguan, coba lagi","Ok");
                }
            }

            @Override
            public void onFailure(Call<Ent_User> call, Throwable t) {
                showSnackbar(t.getMessage(),"Ok");

            }
        });

    }

    private void showSnackbar(String text, String action)
    {

        bar = Snackbar.make(findViewById(R.id.sb_login),text, Snackbar.LENGTH_INDEFINITE);
        bar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
            }
        });
        bar.show();

    }

    private void showDialogKeluar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin ingin keluar ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk keluar")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
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

    @Override
    public void onBackPressed() {
        showDialogKeluar();
    }
}

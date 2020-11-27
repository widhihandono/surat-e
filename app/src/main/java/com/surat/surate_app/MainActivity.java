package com.surat.surate_app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.surat.surate_app.Util.SharedPref;

public class MainActivity extends AppCompatActivity {
private ImageView imgDisposisi;
SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);

        imgDisposisi = findViewById(R.id.imgDisposisi);


        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgDisposisi, "scaleX", 0.4f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgDisposisi, "scaleY", 0.4f);
        scaleDownX.setDuration(2500);
        scaleDownY.setDuration(2500);

        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(imgDisposisi, "translationY", -50);
        moveUpY.setDuration(2500);

        AnimatorSet scaleDown = new AnimatorSet();
        AnimatorSet moveUp = new AnimatorSet();

        scaleDown.play(scaleDownX).with(scaleDownY);
        moveUp.play(moveUpY);

        scaleDown.start();
        moveUp.start();

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {

                    if(!sharedPref.getSPSudahLogin())
                    {
                        Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                        startActivity(intent);
                        Animatoo.animateZoom(MainActivity.this);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this,Menu_Utama_Activity.class);
                        startActivity(intent);
                        Animatoo.animateZoom(MainActivity.this);
                        finish();
                    }
                }
            }
        };
        thread.start();



    }
}

package com.example.onsto.musicbeta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Logo extends AppCompatActivity {
    Intent Logo ;
    ImageView imgLogo;
    Animation animLogo;
    Runnable runnable;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        imgLogo = (ImageView) findViewById(R.id.imageViewLogo);
        animLogo = AnimationUtils.loadAnimation(this, R.anim.anim_logo);
        handler = new Handler();
        Logo = new Intent(getApplicationContext(),MainActivity.class);
        Logo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Logo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Logo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Logo.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        runnable = new Runnable() {
            @Override
            public void run() {
                imgLogo.startAnimation(animLogo);
            }
        };
        handler.postDelayed(runnable,1000);
        Thread logo = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(Logo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        logo.start();
    }
}

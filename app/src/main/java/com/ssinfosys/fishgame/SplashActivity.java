package com.ssinfosys.fishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Runnable {

    public Animation anim;
    private ImageView logo;

    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=(ImageView)findViewById(R.id.imageView);
        //handler
        handler=new Handler();
        handler.postDelayed(this, 6000);
        anim= AnimationUtils.loadAnimation(this, R.anim.anim);
        logo.startAnimation(anim);
    }

    @Override
    public void run() {
        Intent intent=new Intent(this,ActivityMain.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(this);
    }
}

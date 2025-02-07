package com.example.saacpfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        progress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Menu.class));
            }
        },5000);
    }

    private void progress() {
        progressBar = findViewById(R.id.progressbar_splash);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(counter<=15) counter+=1;
                else if( counter>15 && counter<60)counter+=5;
                else counter++;

                progressBar.setProgress(counter);
                if(counter==100)
                    t.cancel();
            }
        };
        t.schedule(tt,0,80);
    }
}
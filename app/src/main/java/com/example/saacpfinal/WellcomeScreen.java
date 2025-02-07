package com.example.saacpfinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saacpfinal.Admin.AdminLogin;
import com.example.saacpfinal.Authentication.MainActivity;

public class WellcomeScreen extends AppCompatActivity {
    AppCompatButton LoginButton;
    TextView wellcome,seeYou,AreYouAdmin,AdminLogin;
    Animation top,below;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wellcome_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryPurple));

        LoginButton = findViewById(R.id.LogInButtonW);
        wellcome = findViewById(R.id.wellcomeText);
        seeYou = findViewById(R.id.topsecondText);
        AreYouAdmin = findViewById(R.id.areAdminw);
        AdminLogin = findViewById(R.id.AdminLogin);

        top = AnimationUtils.loadAnimation(this,R.anim.top_transition);
        below = AnimationUtils.loadAnimation(this,R.anim.bottom_transition);

        LoginButton.setAnimation(top);
        wellcome.setAnimation(top);
        seeYou.setAnimation(top);
        AreYouAdmin.setAnimation(below);
        AdminLogin.setAnimation(below);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.saacpfinal.Admin.AdminLogin.class));
            }
        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
package com.example.saacpfinal.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.saacpfinal.R;
import com.example.saacpfinal.databinding.ActivityLinksentSuccessfullyBinding;

public class LinksentSuccessfully extends AppCompatActivity {
    TextView setEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linksent_successfully);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        String email = getIntent().getStringExtra("email");
        setEmail = findViewById(R.id.mailSet);
        setEmail.setText(email);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, 3500);


    }
}
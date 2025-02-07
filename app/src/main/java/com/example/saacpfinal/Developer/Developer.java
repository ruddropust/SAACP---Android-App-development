package com.example.saacpfinal.Developer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.saacpfinal.Menu;
import com.example.saacpfinal.R;

public class Developer extends AppCompatActivity {
    AppCompatButton home;
    LinearLayout dont,website,vers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlue));

        dont = findViewById(R.id.donate);
        website = findViewById(R.id.visit_profile);
        vers = findViewById(R.id.version_developer_profile);
        home = findViewById(R.id.buttonhome);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
            }
        });
        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Developer.this, "Donate not active in this time, Thanks", Toast.LENGTH_SHORT).show();
            }
        });
        vers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Developer.this, "Your used version is 1.0.0", Toast.LENGTH_SHORT).show();
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLink("https://ruddropust.github.io/ruddro/index.html");
            }
        });

    }

    private void goLink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
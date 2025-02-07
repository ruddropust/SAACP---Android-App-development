package com.example.saacpfinal.Bustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saacpfinal.Menu;
import com.example.saacpfinal.R;

public class BusTracker extends AppCompatActivity {
    TextView track;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tracker);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


        track = findViewById(R.id.track_me);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),VehicleView.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Menu.class));
    }
}
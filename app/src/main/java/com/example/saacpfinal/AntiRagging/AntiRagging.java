package com.example.saacpfinal.AntiRagging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saacpfinal.R;






public class AntiRagging extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    // private static final String TAG="AntiRagging";
    ImageButton call, send_mail;
    String number_ = "+8801784337747";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_ragging);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        call = findViewById(R.id.call_teacher);
        send_mail = findViewById(R.id.mail_bar);





        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendEmail("Pabna");
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
                Toast.makeText(AntiRagging.this, "You will response soon\nThanks for calling.", Toast.LENGTH_SHORT).show();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }


    private void makePhoneCall() {
        if (number_.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(AntiRagging.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AntiRagging.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number_;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(AntiRagging.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void sendEmail(String location) {
        Log.i("Send email", "");
        String[] TO = {"luciferxd2021@gmail.com"};
        String[] CC = {"sharkabir01@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ragging Issue");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi,\nI need to help from unethical & physical harassment.\nI will apply you to solve it.\nLocation: "+location);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
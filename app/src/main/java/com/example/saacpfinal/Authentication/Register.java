package com.example.saacpfinal.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.saacpfinal.R;
import com.example.saacpfinal.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding registerBinding;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));

        fAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        registerBinding.letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerBinding.filledEmailSignup.getEditText().getText().toString();
                String password = registerBinding.filledPasswordSignup.getEditText().getText().toString();


                if(!email.isEmpty()){
                    registerBinding.filledEmailSignup.setError(null);
                    registerBinding.filledEmailSignup.setErrorEnabled(false);
                    if(!password.isEmpty() && password.length()>5)
                    {
                        registerBinding.filledEmailSignup.setHelperText(null);
                        registerBinding.filledPasswordSignup.setHelperText(null);

                        progressDialog.setTitle("Sign Up");
                        progressDialog.show();
                        progressDialog.setMessage("Creating Account...");
                        //Toast.makeText(Register.this, "All set", Toast.LENGTH_SHORT).show();

                        fAuth.createUserWithEmailAndPassword(email,password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(Register.this, "User register Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),ExtendSignUp.class);
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        registerBinding.filledEmailSignup.setHelperText(e.getMessage());
                                        progressDialog.cancel();
                                    }
                                });

                    }else {
                        registerBinding.filledPasswordSignup.setHelperText("Password must be 6 charecter");
                    }

                }else {
                    registerBinding.filledEmailSignup.setError("*Required");
                }
            }
        });
    }
}
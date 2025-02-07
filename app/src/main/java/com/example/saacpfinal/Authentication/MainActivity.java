package com.example.saacpfinal.Authentication;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.saacpfinal.Menu;
import com.example.saacpfinal.R;
import com.example.saacpfinal.WellcomeScreen;
import com.example.saacpfinal.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        firebaseAuth  = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        activityMainBinding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = activityMainBinding.TextEmail.getText().toString();
                String password = activityMainBinding.TextPassword.getText().toString();

                if(!email.isEmpty())
               {
                  if(!password.isEmpty() || password.length()>5)
                  {
                      progressDialog.setTitle("Login");
                      progressDialog.show();
                      progressDialog.setMessage("Loading...");

                      firebaseAuth.signInWithEmailAndPassword(email,password)
                              .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                  @Override
                                  public void onSuccess(AuthResult authResult) {
                                      String name,url;
                                      //User Information
                                      DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userdata");
                                      reference.child(email.replace(".","")).addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                                              UserModel user = snapshot.getValue(UserModel.class);
                                              String name = user.getName();
                                              String url = user.getUrl();
                                              editor.putString("name", name);
                                              editor.apply();
                                              editor.putString("url", url);
                                              editor.apply();
                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {

                                          }
                                      });
                                      //closed user information

                                      editor.putString("user", email.replace(".",""));
                                      editor.apply();
                                      startActivity(new Intent(getApplicationContext(), Menu.class));
                                      progressDialog.cancel();
                                  }
                              })
                              .addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      progressDialog.cancel();
                                      Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                  }
                              });
                  }
                  else{
                      activityMainBinding.TextPassword.setError("Password must be 6 charecter");
                  }
               }else {
                   activityMainBinding.TextEmail.setError("*Required");
               }
            }
        });
        activityMainBinding.ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });
        activityMainBinding.signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        activityMainBinding.adminLoginFromUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WellcomeScreen.class));
            }
        });
    }

}
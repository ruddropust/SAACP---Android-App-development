package com.example.saacpfinal.Authentication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.saacpfinal.R;
import com.example.saacpfinal.databinding.ActivityExtendSignUpBinding;
import com.example.saacpfinal.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExtendSignUp extends AppCompatActivity {
    ActivityExtendSignUpBinding extendSignUpBinding;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    String imageUrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extendSignUpBinding = ActivityExtendSignUpBinding.inflate(getLayoutInflater());
        setContentView(extendSignUpBinding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));
        ///String email = getIntent().getStringExtra("email");



        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = database.getReference("userdata");
        progressDialog = new ProgressDialog(ExtendSignUp.this);


        //database.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        extendSignUpBinding.imageUpload.setImageURI(result);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
                        Date now = new Date();
                        String fileName = formatter.format(now);
                        progressDialog.setTitle("Image Upload");
                        progressDialog.show();
                        progressDialog.setMessage("Uploading");
                        final StorageReference storageReference = storage.getReference("images/" + fileName)
                                .child("image");
                        try {
                            storageReference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUrl = uri.toString();
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(ExtendSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }catch (Exception e){
                            progressDialog.cancel();
                            Toast.makeText(ExtendSignUp.this, "Image not select", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        extendSignUpBinding.imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });


        extendSignUpBinding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emails = getIntent().getStringExtra("email");
                String email = emails.replace(".","");
                String name = extendSignUpBinding.filledNameE.getEditText().getText().toString();
                String username = "+880"+extendSignUpBinding.filledusernameSignup.getEditText().getText().toString();
                String studentid = extendSignUpBinding.filledStudentIdSignup.getEditText().getText().toString();
                String gender = "" + getRadiobuttonText();
                String url = ""+imageUrl;
                String facebookurl="nolink",giturl="nolink",linkedinurl="nolink",location="Not set yet",bloodgroup="N/A";

                if (!gender.isEmpty()) {
                    //Toast.makeText(ExtendSignUp.this, "Hello", Toast.LENGTH_SHORT).show();
                    if(!emails.isEmpty()){
                        if(!name.isEmpty() && name.length()>=3){
                            extendSignUpBinding.filledNameE.setHelperText(null);
                            extendSignUpBinding.filledNameE.setHelperTextEnabled(false);
                            if(!username.isEmpty()){
                                extendSignUpBinding.filledusernameSignup.setHelperText(null);
                                extendSignUpBinding.filledusernameSignup.setHelperTextEnabled(false);
                                if(studentid.length()>5){
                                    extendSignUpBinding.filledStudentIdSignup.setHelperText(null);
                                    extendSignUpBinding.filledStudentIdSignup.setHelperTextEnabled(false);
                                    if(!url.isEmpty()){
                                        UserModel userModel = new UserModel(emails,name,username,studentid,gender,url,facebookurl,giturl,linkedinurl,location,bloodgroup);
                                        reference.child(email).setValue(userModel);
                                        Toast.makeText(ExtendSignUp.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }else{
                                        Toast.makeText(ExtendSignUp.this, "Image not select", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    extendSignUpBinding.filledStudentIdSignup.setHelperText("Userid Must be 6 charecter");
                                    extendSignUpBinding.filledStudentIdSignup.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                                }
                            }else {
                                extendSignUpBinding.filledusernameSignup.setHelperText("Username *Required");
                                extendSignUpBinding.filledusernameSignup.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                            }
                        }
                        else{
                            extendSignUpBinding.filledNameE.setHelperText("Name *Required");
                            extendSignUpBinding.filledNameE.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                        }
                    }
                    else{
                        Toast.makeText(ExtendSignUp.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ExtendSignUp.this, "Gender *Required", Toast.LENGTH_SHORT).show();
                }



                //
                //Toast.makeText(ExtendSignUp.this, uri, Toast.LENGTH_SHORT).show();

/*
                Toast.makeText(ExtendSignUp.this, email, Toast.LENGTH_SHORT).show();
                Toast.makeText(ExtendSignUp.this, name, Toast.LENGTH_SHORT).show();
                Toast.makeText(ExtendSignUp.this, username, Toast.LENGTH_SHORT).show();
                Toast.makeText(ExtendSignUp.this, studentid, Toast.LENGTH_SHORT).show();
                Toast.makeText(ExtendSignUp.this, gender, Toast.LENGTH_SHORT).show();
                //Toast.makeText(ExtendSignUp.this, url, Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private String getRadiobuttonText() {
        try {
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            String gender = radioButton.getText().toString();
            return gender;
        } catch (Exception e) {
            return "";
        }

    }

}



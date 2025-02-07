package com.example.saacpfinal.Admin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saacpfinal.Authentication.ExtendSignUp;
import com.example.saacpfinal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTeacherAdmin extends AppCompatActivity {
    ImageView backButton,uploadTeacherImg;
    TextInputLayout teacher_name,teacher_qualification,job_status;
    AppCompatButton SetData;
    ActivityResultLauncher<String> launcher;

    String imgurl;
    ProgressDialog progressDialog;
    FirebaseStorage storage;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_admin);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorRedThin));

        backButton = findViewById(R.id.back_button_add_teacher);
        teacher_name = findViewById(R.id.teacher_name);
        teacher_qualification = findViewById(R.id.qualification_view);
        job_status = findViewById(R.id.job_place);
        SetData = findViewById(R.id.button_teacher);
        uploadTeacherImg = findViewById(R.id.teacher_image);
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);



        launcher = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        uploadTeacherImg.setImageURI(result);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
                        Date now = new Date();
                        String fileName = formatter.format(now);
                        progressDialog.setTitle("Image Upload");
                        progressDialog.show();
                        progressDialog.setMessage("Uploading");
                        final StorageReference storageReference = storage.getReference("images/" + fileName)
                                .child("image");
                        storageReference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imgurl = uri.toString();
                                        progressDialog.cancel();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddTeacherAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


        uploadTeacherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        SetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String teacherName = teacher_name.getEditText().getText().toString();
                    String teacherQualification = teacher_qualification.getEditText().getText().toString();
                    String jobStatus = job_status.getEditText().getText().toString();
                    String url = imgurl;

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("teacher_information");
                    TeacherModel teacherModel = new TeacherModel(teacherName,teacherQualification,jobStatus,url);
                    reference.child(teacherName).setValue(teacherModel);
                    Toast.makeText(AddTeacherAdmin.this, "Successfully stored teacher Info", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),TeacherListAdmin.class));
                }catch (Exception e){
                Toast.makeText(AddTeacherAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();}
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),TeacherListAdmin.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
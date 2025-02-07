package com.example.saacpfinal.AdminNotice;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saacpfinal.Authentication.ExtendSignUp;
import com.example.saacpfinal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminNoticeAdd extends AppCompatActivity {
    private final int REQUEST_CODE_SELECT_FILE=1;
    ImageView backBtn,uploadFileBtn;
    AppCompatButton SendNotification;
    EditText noticeTitle,noticeMessage;
    FirebaseStorage storage;
    String uploadFile="";
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice_add);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


        backBtn = (ImageView) findViewById(R.id.back_button_noticeAdd);
        uploadFileBtn = (ImageView) findViewById(R.id.upload_file_notice);
        noticeTitle = (EditText) findViewById(R.id.notice_title);
        noticeMessage = (EditText) findViewById(R.id.notice_Message);
        SendNotification = (AppCompatButton) findViewById(R.id.button_send_notification);
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(AdminNoticeAdd.this);

        ActivityCompat.requestPermissions(AdminNoticeAdd.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NoticeListAdmin.class));
            }
        });


        uploadFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        SendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = noticeTitle.getText().toString();
                String message = noticeMessage.getText().toString();
                String fileurl = ""+uploadFile;
                if(noticeTitle.getText().toString().isEmpty() || noticeMessage.getText().toString().isEmpty())
                {
                    Toast.makeText(AdminNoticeAdd.this, "Notice Title & Message\n\t\t\t\t\t\t\t*Required", Toast.LENGTH_SHORT).show();
                }else {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notice_item");
                    NoticeModel noticeModel = new NoticeModel(title, message, fileurl);
                    reference.push().setValue(noticeModel);

                    startActivity(new Intent(getApplicationContext(), NoticeListAdmin.class));
                }
            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.show();
        if (requestCode == REQUEST_CODE_SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();

            StorageReference storageRef = storage.getReference();

            final StorageReference pdfRef = storageRef.child("pdfs/" + System.currentTimeMillis() + ".pdf");
            pdfRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uploadFile = uri.toString();
                                    Drawable drawable = getResources().getDrawable(R.drawable.pdf_show_view);
                                    uploadFileBtn.setImageDrawable(drawable);
                                    Toast.makeText(AdminNoticeAdd.this, uploadFile, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminNoticeAdd.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminNoticeAdd.this, "File upload failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

   // @SuppressLint({"NewApi", "LocalSuppress"}) Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
//        intent.setType("*/*");
//                this.startActivity(intent);
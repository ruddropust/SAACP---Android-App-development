package com.example.saacpfinal.Profile;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.saacpfinal.Authentication.ExtendSignUp;
import com.example.saacpfinal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    String[] items = {"None","A+","A-","B+","B-","AB+","AB","O+","O-"};
    AutoCompleteTextView autoCompleteTextView;
    ActivityResultLauncher<String> launcher;
    TextInputLayout Name_var,Email_var,Phone_var,Location_var,FacebookLink_var,GitHubLink_var,LinkedIn_var;
    CircleImageView ProfileImage_var;

    ArrayAdapter<String>adapter;
    String bloodGroup,Profile_Image;
    ProgressDialog progressDialog;
    AppCompatButton SaveButton;
    FirebaseStorage storage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Name_var = findViewById(R.id.EditName);
        Email_var = findViewById(R.id.EditEmail);
        Phone_var = findViewById(R.id.EditPhone);
        Location_var = findViewById(R.id.EditLocation);
        FacebookLink_var = findViewById(R.id.EditFacebookLink);
        GitHubLink_var = findViewById(R.id.EditGitLink);
        LinkedIn_var = findViewById(R.id.EditLibkedinLink);
        SaveButton= findViewById(R.id.SaveButton);
        ProfileImage_var = findViewById(R.id.profile_image);
        progressDialog = new ProgressDialog(EditProfile.this);
        storage = FirebaseStorage.getInstance();
        /*
        progressDialog.setTitle("Blood Group");
                progressDialog.show();
                progressDialog.setMessage("Saving blood group");
         */


        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapter = new ArrayAdapter<String>(this,R.layout.blood_group_item,items);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                bloodGroup = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(EditProfile.this, bloodGroup, Toast.LENGTH_SHORT).show();

            }
        });

        //upload picture
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        ProfileImage_var.setImageURI(result);
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
                                        Profile_Image = uri.toString();
                                        progressDialog.cancel();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        ProfileImage_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });
        //end upload picture
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userdata");
                String key = sharedPreferences.getString("user", "default_value");
                //Toast.makeText(EditProfile.this, key, Toast.LENGTH_SHORT).show();

                String BloodGroup = ""+bloodGroup;
                String name = Name_var.getEditText().getText().toString();
                String email = Email_var.getEditText().getText().toString();
                String phone = Phone_var.getEditText().getText().toString();
                String location = Location_var.getEditText().getText().toString();
                String facebook = FacebookLink_var.getEditText().getText().toString();
                String git = GitHubLink_var.getEditText().getText().toString();
                String linkedin = LinkedIn_var.getEditText().getText().toString();
                String profileImage = ""+Profile_Image;
                if(!BloodGroup.isEmpty() && (bloodGroup instanceof String))
                {
                    reference.child(key).child("bloodgroup").setValue(BloodGroup);
                }
                if(!name.isEmpty())
                {
                    reference.child(key).child("name").setValue(name);
                }
                if(!email.isEmpty())
                {
                    reference.child(key).child("emails").setValue(email);
                }
                if(!phone.isEmpty())
                {
                    reference.child(key).child("username").setValue("+880"+phone);
                }
                if(!location.isEmpty())
                {
                    reference.child(key).child("location").setValue(location);
                }
                if(!facebook.isEmpty())
                {
                    reference.child(key).child("facebookurl").setValue(facebook);
                }
                if(!git.isEmpty())
                {
                    reference.child(key).child("giturl").setValue(git);
                }
                if(!linkedin.isEmpty())
                {
                    reference.child(key).child("linkedinurl").setValue(linkedin);
                }
                if(!profileImage.isEmpty() &&(Profile_Image instanceof String))
                {
                    reference.child(key).child("url").setValue(profileImage);
                }
                Toast.makeText(EditProfile.this,"Profile Update Successfully", Toast.LENGTH_SHORT).show();




            }
        });


    }
}
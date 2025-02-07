package com.example.saacpfinal.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;
import com.example.saacpfinal.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
        ImageView ProfilePicture,FbIcon,GitIcon,Linkedin;
        TextView profileName,bg,id,batch,emailShow,phoneShow,Location;
        AppCompatButton EditProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPurple));


        ProfilePicture = findViewById(R.id.profileImageView);
        bg = findViewById(R.id.profileBGView);
        profileName = findViewById(R.id.profileNameView);
        id = findViewById(R.id.profileSIdView);
        batch = findViewById(R.id.profileBatchView);
        emailShow = findViewById(R.id.profileEmailView);
        phoneShow = findViewById(R.id.profilePhoneView);
        Location = findViewById(R.id.profileLocationView);
        FbIcon = findViewById(R.id.profileFacebookIconView);
        GitIcon = findViewById(R.id.profileGitIconView);
        Linkedin = findViewById(R.id.profileLinkedInIconView);
        EditProfile = findViewById(R.id.EditProfile);

        //Data receive
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("user", "default_value");
        editor.putString("key", username);
        editor.apply();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userdata");
        reference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                String name = user.getName();
                String url = user.getUrl();
                String gender = user.getGender();
                String studentId = user.getStudentid();
                String phone = user.getUsername();
                String email = user.getEmails();
                String facebookurl = user.getFacebookurl();
                String giturl = user.getGiturl();
                String linkedinurl = user.getLinkedinurl();
                String bloodgroup = user.getBloodgroup();
                String location = user.getLocation();

                //add data field
                Glide.with(Profile.this).load(url).into(ProfilePicture);
                bg.setText(bloodgroup);
                profileName.setText(name);
                id.setText(studentId);
                batch.setText(findBatch(studentId));
                emailShow.setText(email);
                phoneShow.setText(phone);
                Location.setText(location);
                FbIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goLink(facebookurl);
                    }
                });
                GitIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goLink(giturl);
                    }
                });
                Linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goLink(linkedinurl);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.saacpfinal.Profile.EditProfile.class));
            }
        });


    }

    private String findBatch(String studentId) {
        int num = Integer.parseInt( studentId.substring(0, 2));
        num = num-8;
        if(num<1)return "N/A";
        return ""+num;
    }
    private void goLink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
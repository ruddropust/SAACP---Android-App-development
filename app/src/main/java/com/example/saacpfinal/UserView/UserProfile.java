package com.example.saacpfinal.UserView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    DatabaseReference reference;
    ImageView profilePicture;
    LinearLayout Facebook,Git,Linkedin;
    TextView profileName,bg,sId,batch,email,phone,location;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profilePicture = findViewById(R.id.user_profile_image_show);
        Facebook = findViewById(R.id.user_profile_facebook_show);
        Git = findViewById(R.id.user_profile_git_show);
        Linkedin =  findViewById(R.id.user_profile_linkedIn_show);
        profileName = findViewById(R.id.user_profile_name_show);
        bg = findViewById(R.id.user_profile_bg_show);
        sId = findViewById(R.id.user_profile_sId_show);
        batch = findViewById(R.id.user_profile_batch_show);
        email = findViewById(R.id.user_profile_email_show);
        phone = findViewById(R.id.user_profile_number_show);
        location = findViewById(R.id.user_profile_location_show);



        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String key = username.replace(".","");

        reference = FirebaseDatabase.getInstance().getReference().child("userdata").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);

                Glide.with(UserProfile.this).load(model.getUrl()).into(profilePicture);
                profileName.setText(model.getName());
                bg.setText(model.getBloodgroup());
                sId.setText(model.getStudentid());
                batch.setText(getId(model.getStudentid()));
                email.setText(model.getEmails());
                phone.setText(model.getUsername());
                location.setText(model.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();


    }
    public static String getId(String s)
    {
        int num = Integer.parseInt( s.substring(0, 2));
        num = num-8;
        if(num<1)return "N/A";
        return ""+num;
    }
}
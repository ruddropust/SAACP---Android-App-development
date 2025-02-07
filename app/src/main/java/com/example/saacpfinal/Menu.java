package com.example.saacpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.AntiRagging.AntiRagging;
import com.example.saacpfinal.Authentication.MainActivity;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.Bustracker.BusTracker;
import com.example.saacpfinal.CGPACalculator.CgpaCalculator;
import com.example.saacpfinal.ClassTime.ClassTimeList;
import com.example.saacpfinal.Developer.Developer;
import com.example.saacpfinal.Profile.Profile;
import com.example.saacpfinal.TODO.ToDoList;
import com.example.saacpfinal.TeacherUser.TeacherListUser;
import com.example.saacpfinal.UserNotice.NoticeListUser;
import com.example.saacpfinal.UserView.UserList;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu extends AppCompatActivity{
//    ActivityMenuBinding menuBinding;
    CircleImageView ProfileImage;
    TextView Appwishes,ProfileName;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    ImageView imageMenu;

    //Menu
    CardView developaerView,cgpaCalculator,BusTrack,AntiRag,Profile,Teacher,notice,UserView,classTime,ToDO;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        Appwishes = findViewById(R.id.appwishes);
        ProfileImage = findViewById(R.id.profile_image);
        ProfileName = findViewById(R.id.profie_name);



        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("user", "default_value");
        String name = sharedPreferences.getString("name", "default_value");
        String url = sharedPreferences.getString("url", "default_value");
        //DataShare
        editor.putString("name", name);
        editor.apply();
        editor.putString("username", username);
        editor.apply();
        editor.putString("url", url);
        editor.apply();


        //Get data from firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userdata");
        reference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               try {
                   UserModel user = snapshot.getValue(UserModel.class);
                   String gender = ""+user.getGender();
                   String studentId = user.getStudentid();
                   String phone = user.getUsername();
                   String email = user.getEmails();

                   //Data Share
                   editor.apply();
                   editor.putString("email", email);
                   editor.apply();
                   editor.putString("gender", gender);
                   editor.apply();
                   editor.putString("studentId", studentId);
                   editor.apply();
                   editor.putString("phone", phone);
                   //Top view show
                   ProfileName.setText(user.getName());
                   Glide.with(Menu.this).load(user.getUrl()).into(ProfileImage);

                   //Top end view show
               }catch (Exception e){

               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //End gate data


        //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();


        //Time Show(wishes)
        int timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 4 && timeOfDay < 12) {
            Appwishes.setText("Good morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Appwishes.setText("Good afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Appwishes.setText("Good evening");
        } else if ((timeOfDay >= 21 && timeOfDay < 24 )||(timeOfDay>=0 && timeOfDay<4)) {
            Appwishes.setText("Good night");
        }
//        reloadActivity();  //This activity reload app many time caused app error
        //Closed Time Show(wishes)


        // Navagation Drawar------------------------------
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);

        toggle = new ActionBarDrawerToggle(Menu.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Drawar click event
        // Drawer item Click event ------

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mHome:
                        Toast.makeText(Menu.this, "Already Menu Page", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.mProfile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.mDashboard:
                        Toast.makeText(Menu.this, "Dashboard", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.mLogout:
                        Logout();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.shareapp:
                        ShareApp(this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.Policy:
                        startActivity(new Intent(getApplicationContext(),PrivacyPolicy.class));
                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });
        //------------------------------

        // ------------------------
        // App Bar Click Event

        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code Here
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        // ------------------------

        //Drawer top access


//        ImageView drawerTopImage;
//        drawerTopImage = findViewById(R.id.topImageDrawer);
//        Glide.with(this).load(url).into(drawerTopImage);

        //Drawer top access end
        //Menu Button click
        developaerView = findViewById(R.id.developerKey);
        cgpaCalculator = findViewById(R.id.cgpacalculator);
        BusTrack = findViewById(R.id.bus_track);
        AntiRag = findViewById(R.id.antirag);
        Profile = findViewById(R.id.user_profile);
        Teacher = findViewById(R.id.teacher_field);
        notice = findViewById(R.id.notice_view);
        UserView = findViewById(R.id.friend_view);
        classTime = findViewById(R.id.class_time);
        ToDO = findViewById(R.id.to_do);

        developaerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Developer.class));
            }
        });
        cgpaCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CgpaCalculator.class));
            }
        });
        BusTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusTracker.class));
            }
        });
        AntiRag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AntiRagging.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Menu.this, username, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), com.example.saacpfinal.Profile.Profile.class));
            }
        });
        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeacherListUser.class));
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NoticeListUser.class));
            }
        });
        UserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserList.class));
            }
        });
        classTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ClassTimeList.class));
            }
        });
        ToDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoList.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        //end menu button click






    } //closed onCreate Method
    public  void Logout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),WellcomeScreen.class);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Menu.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void ShareApp(NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE,"Share with");
        intent.putExtra(Intent.EXTRA_TEXT,"Share this app with your friend\n\thttps://github.com/ruddropust/SAACP-Project\n\nThanks for stay with us\n");intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share with"));
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(getApplicationContext(), WellcomeScreen.class));
        }
    }
//    public void reloadActivity() {
//        recreate();
//    }
}
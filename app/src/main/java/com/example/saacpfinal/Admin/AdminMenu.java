package com.example.saacpfinal.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.saacpfinal.AdminClassSchedule.AdminScheduleAdd;
import com.example.saacpfinal.AdminClassSchedule.ScheduleListAdmin;
import com.example.saacpfinal.AdminNotice.NoticeListAdmin;
import com.example.saacpfinal.R;

public class AdminMenu extends AppCompatActivity {
    CardView teacherField,NoticeField,ScheduleField,AttendanceField;
    ImageView bgApp,cloverImage;
    LinearLayout splashText,homeText;
    Animation fromBottom,left,right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlueContext));

        fromBottom  = AnimationUtils.loadAnimation(this,R.anim.splash_text_from_bottom);
        left = AnimationUtils.loadAnimation(this,R.anim.left_transition_adminmenu);
        right = AnimationUtils.loadAnimation(this,R.anim.right_transition_adminmenu);

        bgApp = findViewById(R.id.bgapp);
        cloverImage = findViewById(R.id.cloverImg);
        splashText = findViewById(R.id.splashText);
        homeText = findViewById(R.id.hometext);
        teacherField = findViewById(R.id.teacher_field_admin);
        NoticeField = findViewById(R.id.notice_field_admin);
        ScheduleField = findViewById(R.id.schedule_field_admin);
        AttendanceField = findViewById(R.id.attendance_field_admin);


        bgApp.animate().translationY(-1100).setDuration(800).setStartDelay(300);
        cloverImage.animate().alpha(0).setDuration(800).setStartDelay(600);
        splashText.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        homeText.startAnimation(fromBottom);

        teacherField.setAnimation(left);
        NoticeField.setAnimation(right);
        ScheduleField.setAnimation(left);
        AttendanceField.setAnimation(right);

        teacherField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeacherListAdmin.class));
            }
        });

        NoticeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NoticeListAdmin.class));
            }
        });
        ScheduleField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScheduleListAdmin.class));
            }
        });
    }
}
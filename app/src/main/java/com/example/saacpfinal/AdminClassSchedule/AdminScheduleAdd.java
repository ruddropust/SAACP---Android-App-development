package com.example.saacpfinal.AdminClassSchedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.saacpfinal.R;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AdminScheduleAdd extends AppCompatActivity {
    EditText courseName,courseTeacher;
    AppCompatButton datePicker,timePicker,sendSchedule;
    TextView dateShow,TimeShow;
    ProgressDialog progressDialog;
    String times,dates;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule_add);

        courseName = findViewById(R.id.course_nameAdmin);
        courseTeacher = findViewById(R.id.course_teacherAdmin);
        datePicker = findViewById(R.id.pick_date);
        timePicker = findViewById(R.id.pick_time);
        dateShow = findViewById(R.id.show_date);
        TimeShow = findViewById(R.id.show_time);
        sendSchedule = findViewById(R.id.button_Send_schedule);
        progressDialog = new ProgressDialog(this);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminScheduleAdd.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                dates = date;
                                dateShow.setText(date);
                                datePicker.setVisibility(View.GONE);
                                dateShow.setVisibility(View.VISIBLE);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminScheduleAdd.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String meridian;
                                if(hourOfDay>11) meridian="PM";
                                else meridian = "AM";
                                if(hourOfDay>12) hourOfDay-=12;
                                timePicker.setVisibility(View.GONE);
                                TimeShow.setVisibility(View.VISIBLE);
                                String time = hourOfDay + ":" + minute+" "+meridian;
                                times = time;
                                TimeShow.setText(time);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        sendSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNames = "Course Name: "+courseName.getText().toString();
                String courseTeachers = "Course Teacher: "+courseTeacher.getText().toString();
                String time = "Course Time: "+times;
                String date = "Course Date: "+dates;

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Class_Schedule");
                ScheduleModel scheduleModel = new ScheduleModel(courseNames,courseTeachers,date,time);
                reference.push().setValue(scheduleModel);
                Toast.makeText(AdminScheduleAdd.this, "Schedule send Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ScheduleListAdmin.class));

            }
        });
    }

}
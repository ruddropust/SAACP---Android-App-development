package com.example.saacpfinal.AdminClassSchedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleListAdmin extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclelist;
    ScheduleAdapter scheduleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list_admin);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));


        floatingActionButton = findViewById(R.id.float_Schedule_add);
        recyclelist = findViewById(R.id.recycle_view_Schedule_admin);
        try {
            recyclelist.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<ScheduleModel> options = new FirebaseRecyclerOptions.Builder<ScheduleModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Class_Schedule"), ScheduleModel.class)
                    .build();
            scheduleAdapter = new ScheduleAdapter(options);
            recyclelist.setAdapter(scheduleAdapter);
        }catch (Exception e){}


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AdminScheduleAdd.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        scheduleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scheduleAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
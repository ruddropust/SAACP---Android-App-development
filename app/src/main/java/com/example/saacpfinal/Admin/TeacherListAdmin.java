package com.example.saacpfinal.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherListAdmin extends AppCompatActivity {
    FloatingActionButton addButton;
    RecyclerView recyclerView;
    TeacherAdapter teacherAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));


        addButton = findViewById(R.id.float_teacher_add);
        recyclerView = findViewById(R.id.recycle_view);


        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<TeacherModel> options =
                    new FirebaseRecyclerOptions.Builder<TeacherModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("teacher_information"), TeacherModel.class)
                            .build();

            teacherAdapter = new TeacherAdapter(options);
            recyclerView.setAdapter(teacherAdapter);
        }catch (Exception e){};


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddTeacherAdmin.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        teacherAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        teacherAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
package com.example.saacpfinal.TeacherUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherListUser extends AppCompatActivity {
    RecyclerView recyclerView;
    UserTeacherAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list_user);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        recyclerView = findViewById(R.id.recycler_view_teacher_list_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserTeacherModel> options =
                new FirebaseRecyclerOptions.Builder<UserTeacherModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("teacher_information"),UserTeacherModel.class )
                        .build();
        teacherAdapter = new UserTeacherAdapter(options);
        recyclerView.setAdapter(teacherAdapter);

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
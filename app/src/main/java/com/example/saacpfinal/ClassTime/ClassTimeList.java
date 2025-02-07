package com.example.saacpfinal.ClassTime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.saacpfinal.AdminClassSchedule.ScheduleModel;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;
import com.example.saacpfinal.UserView.UserList;
import com.example.saacpfinal.UserView.UserViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassTimeList extends AppCompatActivity {
    RecyclerView recyclerView;
    ClassTimeAdapter adapter;
    DatabaseReference reference;
    ArrayList<ScheduleModel> list,arrayList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorRedThin));
        recyclerView = findViewById(R.id.recycle_view_class_time);

        reference = FirebaseDatabase.getInstance().getReference("Class_Schedule");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        adapter = new ClassTimeAdapter(this,list);
        recyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ScheduleModel model = dataSnapshot.getValue(ScheduleModel.class);
                    arrayList2.add(model);
                }
//                Toast.makeText(ClassTimeList.this, String.valueOf(arrayList2.size()), Toast.LENGTH_SHORT).show();
                for(int i=arrayList2.size()-1;i>=0;i--)
                {
                    list.add(arrayList2.get(i));
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClassTimeList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.saacpfinal.UserNotice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.saacpfinal.*;
import com.example.saacpfinal.AdminNotice.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeListUser extends AppCompatActivity {
    RecyclerView recyclerView;
    UserNoticeAdapter userNoticeAdapter;
    DatabaseReference reference;
    ArrayList<NoticeModel> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list_user);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        recyclerView = findViewById(R.id.recycler_view_notice_list_user);
        reference = FirebaseDatabase.getInstance().getReference("notice_item");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        userNoticeAdapter = new UserNoticeAdapter(this,list);
        recyclerView.setAdapter(userNoticeAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NoticeModel model = dataSnapshot.getValue(NoticeModel.class);
                    list.add(model);
                }
                userNoticeAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NoticeListUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
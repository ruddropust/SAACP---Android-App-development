package com.example.saacpfinal.UserView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.saacpfinal.AdminNotice.NoticeModel;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;
import com.example.saacpfinal.UserNotice.NoticeListUser;
import com.example.saacpfinal.UserNotice.UserNoticeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {
    RecyclerView recyclerView;
    UserViewAdapter adapter;
    DatabaseReference reference;
    ArrayList<UserModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        recyclerView = findViewById(R.id.recycler_view_all_userView_list_user);
        reference = FirebaseDatabase.getInstance().getReference("userdata");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new UserViewAdapter(this,list);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserModel model = dataSnapshot.getValue(UserModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
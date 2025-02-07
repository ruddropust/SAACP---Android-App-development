package com.example.saacpfinal.AdminNotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeListAdmin extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NoticeAdapter noticeAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.float_notice_add);
        recyclerView = findViewById(R.id.recycle_view_Notice_admin);
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FirebaseRecyclerOptions<NoticeModel> optionNotice =
                    new FirebaseRecyclerOptions.Builder<NoticeModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("notice_item"),NoticeModel.class)
                            .build();
            noticeAdapter = new NoticeAdapter(optionNotice);
            recyclerView.setAdapter(noticeAdapter);
        }catch (Exception e){}


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AdminNoticeAdd.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        noticeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noticeAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
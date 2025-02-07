package com.example.saacpfinal.TODO;

import static com.example.saacpfinal.R.id.todo_alarm_imageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmActivity extends AppCompatActivity {
    private static AlarmActivity inst;
    ImageView imageView;
    @BindView(R.id.todo_title)
    TextView title;
    @BindView(R.id.todo_description)
    TextView description;
    @BindView(R.id.todo_timeAndData)
    TextView timeAndData;
    @BindView(R.id.todo_closeButton)
    AppCompatButton closeButton;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        imageView = findViewById(todo_alarm_imageView);
        ButterKnife.bind(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        mediaPlayer.start();

        if(getIntent().getExtras() != null) {
            title.setText(getIntent().getStringExtra("TITLE"));
            description.setText(getIntent().getStringExtra("DESC"));
            timeAndData.setText(getIntent().getStringExtra("DATE") + ", " + getIntent().getStringExtra("TIME"));
        }

        Glide.with(getApplicationContext()).load(R.drawable.alert).into(imageView);
        closeButton.setOnClickListener(view -> finish());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
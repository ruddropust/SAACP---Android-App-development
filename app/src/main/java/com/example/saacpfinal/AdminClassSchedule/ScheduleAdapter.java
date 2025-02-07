package com.example.saacpfinal.AdminClassSchedule;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saacpfinal.AdminNotice.NoticeModel;
import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ScheduleAdapter extends FirebaseRecyclerAdapter<ScheduleModel, ScheduleAdapter.ScheduleViewHlder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ScheduleAdapter(@NonNull FirebaseRecyclerOptions<ScheduleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ScheduleViewHlder holder, @SuppressLint("RecyclerView") final int position, @NonNull ScheduleModel model) {
          holder.course_name.setText(model.getCourseName());
          holder.course_teacher.setText(model.getCourseTeacher());
          holder.course_time.setText(model.getTime());
          holder.course_date.setText(model.getDate());

          holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(holder.course_name.getContext());
                  builder.setTitle("Are you sure? ");
                  builder.setMessage("Delete data can't be undo.");
                  builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          FirebaseDatabase.getInstance().getReference().child("Class_Schedule")
                                  .child(getRef(position).getKey()).removeValue();
                          Toast.makeText(holder.course_name.getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                      }
                  });
                  builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Toast.makeText(holder.course_name.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                      }
                  });
                  builder.show();
              }
          });
    }

    @NonNull
    @Override
    public ScheduleViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item_view_show,parent,false);
        return new ScheduleViewHlder(view);
    }

    class ScheduleViewHlder extends RecyclerView.ViewHolder{
        TextView course_name,course_teacher,course_time,course_date;
        ImageView deleteBtn;

        public ScheduleViewHlder(@NonNull View itemView) {
            super(itemView);
            course_name = itemView.findViewById(R.id.schedule_course_name_show_admin);
            course_teacher = itemView.findViewById(R.id.schedule_course_teacher_show_admin);
            course_time = itemView.findViewById(R.id.schedule_time_show_admin);
            course_date = itemView.findViewById(R.id.schedule_date_show_admin);

            deleteBtn = itemView.findViewById(R.id.delete_schedule_info_admin);


        }
    }
}

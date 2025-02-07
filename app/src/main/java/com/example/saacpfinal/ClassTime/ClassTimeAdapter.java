package com.example.saacpfinal.ClassTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saacpfinal.AdminClassSchedule.ScheduleModel;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;
import com.example.saacpfinal.UserView.UserViewAdapter;

import java.util.ArrayList;

public class ClassTimeAdapter extends RecyclerView.Adapter<ClassTimeAdapter.ClassViewHolder>{
    Context context;
    ArrayList<ScheduleModel> list;

    public ClassTimeAdapter(Context context, ArrayList<ScheduleModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ClassTimeAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolder(LayoutInflater.from(context).inflate(R.layout.class_time,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassTimeAdapter.ClassViewHolder holder, int position) {
        ScheduleModel model = list.get(position);

        holder.className.setText(model.getCourseName());
        holder.classTeacher.setText(model.getCourseTeacher());
        holder.classTime.setText(model.getTime());
        holder.classDate.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder{
        TextView className,classTime,classTeacher,classDate;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.class_name_show);
            classTeacher = itemView.findViewById(R.id.class_teacher_name_show);
            classTime = itemView.findViewById(R.id.class_time_show);
            classDate = itemView.findViewById(R.id.class_date_show);
        }
    }
}

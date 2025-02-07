package com.example.saacpfinal.CGPACalculator;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saacpfinal.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView sub,gd,cd;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        sub = itemView.findViewById(R.id.subject_put);
        gd = itemView.findViewById(R.id.grade_put);
        cd = itemView.findViewById(R.id.credit_put);
    }
}
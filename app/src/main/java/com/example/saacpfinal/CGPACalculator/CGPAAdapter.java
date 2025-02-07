package com.example.saacpfinal.CGPACalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saacpfinal.R;

import java.util.List;

public class CGPAAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<item> item;

    public CGPAAdapter(Context context, List<com.example.saacpfinal.CGPACalculator.item> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sub.setText(item.get(position).getSubjects());
        holder.gd.setText(item.get(position).getGread());
        holder.cd.setText(item.get(position).getCrdit());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}

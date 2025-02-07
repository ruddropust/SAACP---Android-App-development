package com.example.saacpfinal.Admin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewTreeViewModelKt;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.Bustracker.VehicleView;
import com.example.saacpfinal.Profile.Profile;
import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends FirebaseRecyclerAdapter<TeacherModel,TeacherAdapter.TeacherViewHlder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TeacherAdapter(@NonNull FirebaseRecyclerOptions<TeacherModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeacherViewHlder holder,final int position, @NonNull TeacherModel model) {
        try {
            holder.name.setText(model.getTeachername());
            holder.qualification.setText(model.getQualification());
            holder.status.setText(model.getJobstatus());
            Glide.with(holder.img.getContext())
                    .load(model.getUrl())
                    .placeholder(R.drawable.load_image_failed)
                    .circleCrop().error(R.drawable.load_image_failed).into(holder.img);
            int p = position;


            holder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                            .setContentHolder(new ViewHolder(R.layout.edit_teacher_dialouge_show))
                            .setExpanded(true,1450)
                            .create();

                    View v = dialogPlus.getHolderView();
                    TextInputEditText name = v.findViewById(R.id.name_edit_field);
                    TextInputEditText qualification = v.findViewById(R.id.qualification_edit_field);
                    TextInputEditText jobStatus = v.findViewById(R.id.status_edit_field);
                    ImageView surl = v.findViewById(R.id.img_url_edit_field);

                    AppCompatButton saveBtn = v.findViewById(R.id.edit_teacher_info_btn);

                    name.setText(model.getTeachername());
                    qualification.setText(model.getQualification());
                    jobStatus.setText(model.getJobstatus());
                    Glide.with(holder.name.getContext()).load(model.getUrl()).into(surl);
                    dialogPlus.show();

                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("teachername",name.getText().toString());
                            map.put("qualification",qualification.getText().toString());
                            map.put("jobstatus",jobStatus.getText().toString());
                            map.put("url",model.getUrl());
                            String teacherName = (String) map.get("teachername");
                            //map.put("url",surl)
                            FirebaseDatabase.getInstance().getReference().child("teacher_information")
                                    .child(model.getTeachername()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("teacher_information")
                                    .child(teacherName).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.name.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.name.getContext(), "Data not receive", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }
                    });

                }
            });
            holder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                    builder.setTitle("Are you sure? ");
                    builder.setMessage("Delete data can't be undo.");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase.getInstance().getReference().child("teacher_information")
                                    .child(model.getTeachername()).removeValue();
                            Toast.makeText(holder.name.getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(holder.name.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }catch (Exception e){}
    }

    @NonNull
    @Override
    public TeacherViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item_view_show,parent,false);
        return new TeacherViewHlder(view);
    }


    class TeacherViewHlder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,qualification,status;

        ImageView delBtn,editBtn;

        public TeacherViewHlder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.teacher_image_show);
            name = (TextView) itemView.findViewById(R.id.teacher_name_show);
            qualification = (TextView) itemView.findViewById(R.id.teacher_qualification_show);
            status = (TextView) itemView.findViewById(R.id.teacher_status_show);

            delBtn = (ImageView) itemView.findViewById(R.id.delete_teacher_info);
            editBtn = (ImageView) itemView.findViewById(R.id.edit_teacher_info);
        }
    }
}

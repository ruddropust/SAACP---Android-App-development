package com.example.saacpfinal.AdminNotice;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.Admin.TeacherModel;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeAdapter extends FirebaseRecyclerAdapter<NoticeModel, NoticeAdapter.NoticeViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoticeAdapter(@NonNull FirebaseRecyclerOptions<NoticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoticeViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull NoticeModel model) {
            holder.title.setText(model.getTitle());
            holder.body.setText(model.getMessage());
        holder.editNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_notice_dialouge_show))
                        .setExpanded(true,1450)
                        .create();

                View v = dialogPlus.getHolderView();
                TextInputEditText title = v.findViewById(R.id.notice_title_edit);
                TextInputEditText message = v.findViewById(R.id.notice_message_edit);
                AppCompatButton saveButton = v.findViewById(R.id.edit_notice_info_btn);

                title.setText(model.getTitle());
                message.setText(model.getMessage());
                dialogPlus.show();
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("message",message.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("notice_item")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Update Notice", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.title.getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });

                    }
                });
            }
        });
        holder.deleteNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you sure? ");
                builder.setMessage("Delete data can't be undo.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("notice_item")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.title.getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.title.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item_view_show,parent,false);
        return new NoticeViewHolder(view);
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder{
        TextView title,body;
        ImageView editNoticeBtn,deleteNoticeBtn;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notice_title_show_admin);
            body = itemView.findViewById(R.id.notice_body_show_admin);

            deleteNoticeBtn = itemView.findViewById(R.id.delete_notice_info_admin);
            editNoticeBtn = itemView.findViewById(R.id.edit_notice_info_admin);

        }
    }
}



/*



 */
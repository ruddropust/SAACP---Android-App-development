package com.example.saacpfinal.UserNotice;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.saacpfinal.AdminNotice.NoticeModel;
import com.example.saacpfinal.R;


import java.util.ArrayList;


public class UserNoticeAdapter extends RecyclerView.Adapter<UserNoticeAdapter.UserNoticeViewHlder>{
    Context context;
    ArrayList<NoticeModel> arrayList;

    public UserNoticeAdapter(Context context, ArrayList<NoticeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UserNoticeViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserNoticeViewHlder(LayoutInflater.from(context).inflate(R.layout.user_notice_item_view_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserNoticeViewHlder holder, int position) {
        NoticeModel model = arrayList.get(position);

        holder.title.setText(model.getTitle());
        holder.message.setText(model.getMessage());
        holder.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getFileurl()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class UserNoticeViewHlder extends RecyclerView.ViewHolder{
        TextView title,message;
        ImageButton imgBtn;

        public UserNoticeViewHlder(@NonNull View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.notice_title_show_user);
            message = (TextView) itemView.findViewById(R.id.notice_message_show_user);
            imgBtn = (ImageButton) itemView.findViewById(R.id.notice_download_show_user);


        }
    }
}

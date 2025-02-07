package com.example.saacpfinal.UserView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saacpfinal.AdminClassSchedule.ScheduleModel;
import com.example.saacpfinal.Authentication.UserModel;
import com.example.saacpfinal.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.UserViewHlder>{
    Context context;
    ArrayList<UserModel> arrayList;

    public UserViewAdapter(Context context, ArrayList<UserModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UserViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHlder(LayoutInflater.from(context).inflate(R.layout.all_user_item_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHlder holder, int position) {
        UserModel model = arrayList.get(position);

        Glide.with(context).load(model.getUrl()).into(holder.profileImgView);
        holder.profileName.setText(model.getName());

        holder.singlePress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UserProfile.class);
                intent.putExtra("username",model.getEmails());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class UserViewHlder extends RecyclerView.ViewHolder{
        CircleImageView profileImgView;
        TextView profileName;
        LinearLayout singlePress;

        public UserViewHlder(@NonNull View itemView) {
            super(itemView);


            profileImgView = (CircleImageView) itemView.findViewById(R.id.userView_list_image_show);
            profileName = (TextView) itemView.findViewById(R.id.userView_profile_name_show);
            singlePress = (LinearLayout) itemView.findViewById(R.id.single_press);


        }
    }
}

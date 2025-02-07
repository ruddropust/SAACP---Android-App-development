package com.example.saacpfinal.TeacherUser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.saacpfinal.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserTeacherAdapter extends FirebaseRecyclerAdapter<UserTeacherModel, UserTeacherAdapter.TeacherViewHlder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserTeacherAdapter(@NonNull FirebaseRecyclerOptions<UserTeacherModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeacherViewHlder holder,final int position, @NonNull UserTeacherModel model) {
        holder.name.setText(model.getTeachername());
        holder.qualification.setText(model.getQualification());
        holder.status.setText(model.getJobstatus());
        Glide.with(holder.img.getContext())
                .load(model.getUrl())
                .placeholder(R.drawable.load_image_failed)
                .circleCrop().error(R.drawable.load_image_failed).into(holder.img);

    }

    @NonNull
    @Override
    public TeacherViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_teacher_item_view_show,parent,false);
        return new TeacherViewHlder(view);
    }

    class TeacherViewHlder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,qualification,status;

        public TeacherViewHlder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.user_teacher_image_show);
            name = (TextView) itemView.findViewById(R.id.user_teacher_name_show);
            qualification = (TextView) itemView.findViewById(R.id.user_teacher_qualification_show);
            status = (TextView) itemView.findViewById(R.id.user_teacher_status_show);

        }
    }
}

package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Models.FindMentor;
import com.example.finalyearproject.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdapterClass  extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    private Context context;
    private List<FindMentor> userList;

    public AdapterClass(Context context, List<FindMentor> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_mentors_displayed, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  FindMentor user = userList.get(position);

        holder.type.setText(user.getType());
        holder.type.setText(user.getType());
        holder.name.setText(user.getName());
        holder.bio.setText(user.getBio());
        holder.location.setText(user.getLocation());
        holder.language.setText(user.getLanguage());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView type, name, bio, location, language;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.searchMentorType);
            name   = itemView.findViewById(R.id.searchMentorName);
            bio   = itemView.findViewById(R.id.searchMentorBio1);
            location   = itemView.findViewById(R.id.searchMentorLocation);
            language   = itemView.findViewById(R.id.searchMentorIndustry);



        }
    }

}
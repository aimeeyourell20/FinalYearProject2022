package com.example.finalyearproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Goals_Adapter extends RecyclerView.Adapter<Goals_Adapter.GoalsViewHolder> {

    Context context;
    ArrayList<Goals_Model> goals_models;
    private FirebaseAuth mAuth;

    public Goals_Adapter (List<Goals_Model> goals_models)
    {
        this.goals_models = (ArrayList<Goals_Model>) goals_models;
    }

    public Goals_Adapter(Context c, ArrayList<Goals_Model> g){
        context = c;
        goals_models = g;
    }

    @NonNull
    @Override
    public Goals_Adapter.GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_details, parent, false);

        //return new Meetings_Adapter.MeetingsViewHolder(LayoutInflater.from(context).inflate(R.layout.all_mentee_meetings_displayed, parent, false));
        mAuth = FirebaseAuth.getInstance();

        return new Goals_Adapter.GoalsViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Goals_Adapter.GoalsViewHolder holder, int position) {


        Goals_Model goals_model = goals_models.get(position);

        holder.goalsTitle.setText(goals_model.getGoalsTitle());
        holder.goalsDescription.setText(goals_model.getGoalsDescription());
        holder.goalsDate.setText(goals_model.getGoalsDate());
        //holder.key.setText(goals_model.getKey());


        final String getGoalsTitle = goals_model.getGoalsTitle();
        final String getGoalsDescription = goals_model.getGoalsDescription();
        final String getGoalsDate = goals_model.getGoalsDate();
        final String key = goals_model.getKey();

    }





    @Override
    public int getItemCount() {
        return goals_models.size();
    }

    class GoalsViewHolder extends RecyclerView.ViewHolder{

        TextView goalsTitle,goalsDescription,goalsDate, key;
        TextView status;


        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);

            goalsTitle = itemView.findViewById(R.id.goalTitle);
            goalsDescription = itemView.findViewById(R.id.goalDescription);
            goalsDate = itemView.findViewById(R.id.goalDate);

        }
    }
}

package com.example.finalyearproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Goals_Adapter extends RecyclerView.Adapter<Goals_Adapter.GoalsViewHolder> {

    Context context;
    ArrayList<Goals_Model> goals_models;
    ArrayList<CheckBox> checkBoxes;
    List<String> keys;
    private FirebaseAuth mAuth;
    private static final String tag = "Mentor";






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



        final String getGoalsTitle = goals_model.getGoalsTitle();
        final String getGoalsDescription = goals_model.getGoalsDescription();
        final String getGoalsDate = goals_model.getGoalsDate();
        final String getGoalsStartDate = goals_model.getGoalsStartDate();
        final String getGoalsMentor = goals_model.getGoalsMentor();
        final String getGoalsMentee = goals_model.getGoalsMentee();
        final String getGoalsStatus = goals_model.getStatus();


        holder.goalsTitle.setText(getGoalsTitle);
        holder.goalsDescription.setText(getGoalsDescription);
        holder.goalsDate.setText(getGoalsDate);
        holder.goalsStartDate.setText(getGoalsStartDate);
        holder.goalMenteeName.setText(getGoalsMentee);
        holder.goalMentorName.setText(getGoalsMentor);
        holder.status.setText(getGoalsStatus);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals_Model goals_id = goals_models.get(position);
                Goals_Model goal_mentor = goals_models.get(position);
                Intent intent = new Intent(context, Goal_Edit.class);
                intent.putExtra("goals_id", goals_id.getGoalsid());
                intent.putExtra("menteeid", goal_mentor.getGoalsmentorid());
                Log.d(tag, "Hello" + goal_mentor);
                context.startActivity(intent);
            }
        });

    }






    @Override
    public int getItemCount() {
        return goals_models.size();
    }

    class GoalsViewHolder extends RecyclerView.ViewHolder{

        TextView goalsTitle,goalsDescription,goalsDate, key, goalsStartDate, goalMentorName, goalMenteeName, status;

        Button edit;


        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);

            goalsTitle = itemView.findViewById(R.id.goalTitle);
            goalsDescription = itemView.findViewById(R.id.goalDescription);
            goalsDate = itemView.findViewById(R.id.goalDate);
            goalsStartDate = itemView.findViewById(R.id.goalStartDate);
            goalMentorName = itemView.findViewById(R.id.goalMentorsName);
            goalMenteeName = itemView.findViewById(R.id.goalMenteesName);
            status = itemView.findViewById(R.id.goalProgressSpinner);


        }
    }
}

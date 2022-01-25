package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Goals_Adapter extends RecyclerView.Adapter<Goals_Adapter.GoalsViewHolder> {

    Context context;
    ArrayList<Goals_Model> goals_models;

    public Goals_Adapter(Context c, ArrayList<Goals_Model> g){
        context = c;
        goals_models = g;
    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalsViewHolder(LayoutInflater.from(context).inflate(R.layout.goals_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

        Goals_Model goals_model = goals_models.get(position);

        holder.goalsTitle.setText(goals_model.getGoalsTitle());
        holder.goalsDescription.setText(goals_model.getGoalsDescription());
        holder.goalsDate.setText(goals_model.getGoalsDate());
        //holder.key.setText(goals_model.getKey());


        final String getGoalsTitle = goals_model.getGoalsTitle();
        final String getGoalsDescription = goals_model.getGoalsDescription();
        final String getGoalsDate = goals_model.getGoalsDate();
        final String key = goals_model.getKey();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Edit_Goal.class);
                i.putExtra("goalsTitle", getGoalsTitle);
                i.putExtra("goalsDescription", getGoalsDescription);
                i.putExtra("goalsDate", getGoalsDate);
                i.putExtra("key", key);
                context.startActivity(i);
            }
        });


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

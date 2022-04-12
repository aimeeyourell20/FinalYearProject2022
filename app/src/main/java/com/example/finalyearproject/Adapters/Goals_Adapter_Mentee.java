package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Mentees.Goal_Edit;
import com.example.finalyearproject.Mentees.Goals_Activity_Mentee;
import com.example.finalyearproject.Models.Goals_Model;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Goals_Adapter_Mentee extends RecyclerView.Adapter<Goals_Adapter_Mentee.GoalsViewHolder> implements Filterable {

    Context context;
    ArrayList<Goals_Model> goals_models;
    ArrayList<Goals_Model> goals_modelsFull;
    DatabaseReference Goals = FirebaseDatabase.getInstance().getReference().child("Goals");
    public Goals_Adapter_Mentee(List<Goals_Model> goals_models)
    {
        this.goals_models = (ArrayList<Goals_Model>) goals_models;
        goals_modelsFull = new ArrayList<>(goals_models);


    }

    public Goals_Adapter_Mentee(Context c, ArrayList<Goals_Model> g){
        context = c;
        goals_models = g;
    }

    @NonNull
    @Override
    public Goals_Adapter_Mentee.GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_details, parent, false);

        return new Goals_Adapter_Mentee.GoalsViewHolder(V);
    }



    @Override
    public void onBindViewHolder(@NonNull Goals_Adapter_Mentee.GoalsViewHolder holder, int position) {

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

                CharSequence charSequence[] = new CharSequence[]{

                        "Edit Goal",
                        "Remove Goal"

                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Goal Options");

                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0){
                            Goals_Model goals_id = goals_models.get(position);
                            Goals_Model goal_mentor = goals_models.get(position);
                            Intent intent = new Intent(context, Goal_Edit.class);
                            intent.putExtra("goals_id", goals_id.getGoalsid());
                            intent.putExtra("menteeid", goal_mentor.getGoalsmentorid());
                            context.startActivity(intent);
                        }
                        if(i == 1){
                            Goals_Model goal_mentor = goals_models.get(position);
                            Goals.child(FirebaseAuth.getInstance().getUid()).child(goal_mentor.getGoalsmentorid()).child(goals_model.getGoalsid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(context, "Goal Successfully Removed", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, Goals_Activity_Mentee.class);
                                        context.startActivity(intent);

                                    }

                                }
                            });
                        }

                    }
                });

                builder.show();
            }
        });

    }







    @Override
    public int getItemCount() {
        return goals_models.size();
    }

    @Override
    public Filter getFilter() {
        return goalsFilter;
    }

    private final Filter goalsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Goals_Model> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(goals_modelsFull);
            }else {
                String filter = charSequence.toString().toLowerCase().trim();

                for(Goals_Model g : goals_modelsFull){
                    if(g.getStatus().toLowerCase().contains(filter)){
                        filterList.add(g);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            goals_models.clear();
            //goals_models.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };




    class GoalsViewHolder extends RecyclerView.ViewHolder{

        TextView goalsTitle,goalsDescription,goalsDate, key, goalsStartDate, goalMentorName, goalMenteeName, status;
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

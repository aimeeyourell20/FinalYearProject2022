package com.example.finalyearproject.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.Mentees.Goal_Edit;
import com.example.finalyearproject.Mentees.Goals_Activity_Mentee;
import com.example.finalyearproject.Models.Goals_Model;
import com.example.finalyearproject.Models.Meeting_Model;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Meetings_Adapter extends RecyclerView.Adapter<Meetings_Adapter.MeetingsViewHolder> {

    Context context;
    ArrayList<Meeting_Model> meeting_models;

    public Meetings_Adapter (List<Meeting_Model> meeting_models)
    {
        this.meeting_models = (ArrayList<Meeting_Model>) meeting_models;
    }

    public Meetings_Adapter(Context c, ArrayList<Meeting_Model> g){
        context = c;
        meeting_models = g;
    }

    @NonNull
    @Override
    public Meetings_Adapter.MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_mentee_meetings_displayed, parent, false);

        return new Meetings_Adapter.MeetingsViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Meetings_Adapter.MeetingsViewHolder holder, int position) {


        Meeting_Model meeting_model = meeting_models.get(position);

        holder.meetingDescription.setText(meeting_model.getMeetingDescription());
        holder.meetingLocation.setText(meeting_model.getMeetingLocation());
        holder.meetingMentor.setText(meeting_model.getMeetingMentor());
        holder.meetingTitle.setText(meeting_model.getMeetingTitle());
        holder.meetingMentee.setText(meeting_model.getMeetingMentee());
        holder.meetingDate.setText(meeting_model.getDate());

    }





    @Override
    public int getItemCount() {
        return meeting_models.size();
    }

    class MeetingsViewHolder extends RecyclerView.ViewHolder{

        TextView meetingDescription,meetingLocation,meetingMentor, meetingTitle, meetingMentee, meetingDate;



        public MeetingsViewHolder(@NonNull View itemView) {
            super(itemView);

            meetingDescription = itemView.findViewById(R.id.descriptionMeeting);
            meetingLocation = itemView.findViewById(R.id.locationMeeting);
            meetingMentor = itemView.findViewById(R.id.searchMentorName);
            meetingTitle = itemView.findViewById(R.id.titleMeeting);
            meetingMentee = itemView.findViewById(R.id.searchMenteeName);
            meetingDate = itemView.findViewById(R.id.searchMentorDate);

        }
    }
}

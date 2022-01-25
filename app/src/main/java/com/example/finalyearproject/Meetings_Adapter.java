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

import java.text.BreakIterator;
import java.util.ArrayList;

public class Meetings_Adapter extends RecyclerView.Adapter<Meetings_Adapter.MeetingsViewHolder> {

    Context context;
    ArrayList<Meeting_Model> meeting_models;

    public Meetings_Adapter(Context c, ArrayList<Meeting_Model> g){
        context = c;
        meeting_models = g;
    }

    @NonNull
    @Override
    public Meetings_Adapter.MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Meetings_Adapter.MeetingsViewHolder(LayoutInflater.from(context).inflate(R.layout.all_mentee_meetings_displayed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Meetings_Adapter.MeetingsViewHolder holder, int position) {

        Meeting_Model meeting_model = meeting_models.get(position);

        holder.meetingDescription.setText(meeting_model.getMeetingDescription());
        holder.meetingLocation.setText(meeting_model.getMeetingLocation());
        holder.meetingMentor.setText(meeting_model.getMeetingMentor());
        holder.meetingTitle.setText(meeting_model.getMeetingTitle());


        final String getMeetingDescription = meeting_model.getMeetingDescription();
        final String getMeetingLocation = meeting_model.getMeetingLocation();
        final String getMeetingMentor = meeting_model.getMeetingMentor();
        final String getMeetingTitle = meeting_model.getMeetingTitle();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Goals.class);
                context.startActivity(i);
            }


        });
    }

    @Override
    public int getItemCount() {
        return meeting_models.size();
    }

    class MeetingsViewHolder extends RecyclerView.ViewHolder{

        TextView meetingDescription,meetingLocation,meetingMentor, meetingTitle;



        public MeetingsViewHolder(@NonNull View itemView) {
            super(itemView);

            meetingDescription = itemView.findViewById(R.id.descriptionMeeting);
            meetingLocation = itemView.findViewById(R.id.locationMeeting);
            meetingMentor = itemView.findViewById(R.id.searchMentorName);
            meetingTitle = itemView.findViewById(R.id.titleMeeting);

        }
    }
}

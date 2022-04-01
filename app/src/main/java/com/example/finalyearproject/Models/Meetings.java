package com.example.finalyearproject.Models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.finalyearproject.Adapters.Meetings_Adapter;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Meetings extends AppCompatActivity {

    private DatabaseReference MeetingsRef,MentorRef, reference;
    private RecyclerView menteeRecyclerView;
    private FirebaseAuth firebaseAuth;
    private String menteeOnline;
    ArrayList<Meeting_Model> meeting;
    Meetings_Adapter meetings_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);


        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Meeting");



        menteeRecyclerView = findViewById(R.id.meetingsRecyclerView);
        menteeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        meeting = new ArrayList<Meeting_Model>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){

                    Meeting_Model g = uniqueKeySnapshot.getValue(Meeting_Model.class);
                    meeting.add(g);

                }

                meetings_adapter = new Meetings_Adapter(Meetings.this, meeting);
                menteeRecyclerView.setAdapter(meetings_adapter);
                meetings_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Meetings.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}








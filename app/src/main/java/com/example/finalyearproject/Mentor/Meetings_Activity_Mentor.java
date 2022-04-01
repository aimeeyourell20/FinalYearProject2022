package com.example.finalyearproject.Mentor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalyearproject.Adapters.Meetings_Adapter;
import com.example.finalyearproject.Models.Meeting_Model;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Meetings_Activity_Mentor extends AppCompatActivity {

    private DatabaseReference RootRef;
    private String menteeID ="";
    private RecyclerView menteeRecyclerView;
    private final ArrayList<Meeting_Model> meeting  = new ArrayList<>();
    private Meetings_Adapter meetings_adapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_mentor);

        RootRef = FirebaseDatabase.getInstance().getReference();

        meetings_adapter = new Meetings_Adapter(meeting);
        menteeRecyclerView = (RecyclerView) findViewById(R.id.meetingsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        menteeRecyclerView.setLayoutManager(linearLayoutManager);
        menteeRecyclerView.setAdapter(meetings_adapter);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeID = (String) extras.get("menteeid");
            }
        }

        FetchMeetings();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Meetings_Activity_Mentor.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void FetchMeetings() {

        RootRef.child("Meetings").child(FirebaseAuth.getInstance().getUid()).child(menteeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                    for(DataSnapshot a : uniqueKeySnapshot.getChildren()) {

                        Meeting_Model g = a.getValue(Meeting_Model.class);
                        meeting.add(g);
                    }

                }

                meetings_adapter = new Meetings_Adapter(Meetings_Activity_Mentor.this, meeting);
                menteeRecyclerView.setAdapter(meetings_adapter);
                meetings_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Meetings_Activity_Mentor.this, "Error", Toast.LENGTH_SHORT).show();

            }

        });

    }

}

package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenteeRequestMentor extends AppCompatActivity {

    private TextView mname, mskills1, mskills2, mskills3, mlocation, mlanguage, mjobTitle, mbio, mindustry, mtype;
    private Button MeetingRequest, Meetings;

    private DatabaseReference FriendsRequestRef, UsersRef, FriendsRef;
    private FirebaseAuth mAuth;
    private String senderUserId, CURRENT_STATE, saveCurrentDate;
    private String receiverUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_request_mentor);


        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        mname = findViewById(R.id.personname);
        mtype = findViewById(R.id.persontype);
        mskills1 = findViewById(R.id.personskills1);
        mskills2 = findViewById(R.id.personskills2);
        mlanguage = findViewById(R.id.personlanguage);
        mlocation = findViewById(R.id.personlocation);
        mjobTitle = findViewById(R.id.personjobTitle);
        mbio = findViewById(R.id.personbio);
        mindustry = findViewById(R.id.personindustry);
        MeetingRequest = (Button) findViewById(R.id.requestMeeting);
        Meetings = (Button) findViewById(R.id.meetings);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                receiverUserId = (String) extras.get("mentorid");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        //receiverUserId = getIntent().getExtras().get("mentorid").toString();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");

        UsersRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String bio = dataSnapshot.child("bio").getValue().toString();
                    String jobTitle = dataSnapshot.child("jobTitle").getValue().toString();
                    String industry = dataSnapshot.child("industry").getValue().toString();
                    String type = dataSnapshot.child("type").getValue().toString();
                    String skill1 = dataSnapshot.child("skill1").getValue().toString();
                    String skill2 = dataSnapshot.child("skill2").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();


                    mname.setText(name);
                    mbio.setText(bio);
                    mjobTitle.setText(jobTitle);
                    mindustry.setText(industry);
                    mtype.setText(type);
                    mskills1.setText(skill1);
                    mskills2.setText(skill2);
                    mlanguage.setText(language);
                    mlocation.setText(location);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        MeetingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenteeRequestMentor.this, MeetingRequest.class);
                startActivity(i);
            }
        });

        Meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenteeRequestMentor.this, Meetings.class);
                startActivity(i);
            }
        });
    }
}


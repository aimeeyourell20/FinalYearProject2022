package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class MentorFriends extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, mtype, mcollege, mcourse, mindustry, mgoals;
    private Button SendFriendReqButton, DeclineFriendRequestButton;
    private DatabaseReference FriendsRequestRef, UsersRef, FriendsRef;
    private FirebaseAuth mAuth;
    private ImageView mprofile;
    private String senderUserId, CURRENT_STATE, saveCurrentDate;
    private String receiverUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_friends);


        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        mname = findViewById(R.id.personname);
        mtype = findViewById(R.id.persontype);
        mskills = findViewById(R.id.personskills);
        mlanguage = findViewById(R.id.personlanguage);
        mlocation = findViewById(R.id.personlocation);
        mcollege = findViewById(R.id.personcollege);
        mcourse = findViewById(R.id.personcourse);
        mindustry = findViewById(R.id.personIndustry);
        mgoals = findViewById(R.id.persongoals);
        mprofile = findViewById(R.id.profileImageProfile);
        SendFriendReqButton = (Button) findViewById(R.id.sendRequest);
        DeclineFriendRequestButton = (Button) findViewById(R.id.cancelRequest);


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                receiverUserId = (String) extras.get("menteeid");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        //receiverUserId = getIntent().getExtras().get("menteeid").toString();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");


        UsersRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String type = dataSnapshot.child("type").getValue().toString();
                    String skills = dataSnapshot.child("skill1").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String college = dataSnapshot.child("college").getValue().toString();
                    String course = dataSnapshot.child("course").getValue().toString();
                    String industry = dataSnapshot.child("industry").getValue().toString();
                    String goals = dataSnapshot.child("goals").getValue().toString();
                    String photo = dataSnapshot.child("profileimage").getValue().toString();


                    mname.setText(name);
                    mtype.setText(type);
                    mskills.setText(skills);
                    mlanguage.setText(language);
                    mlocation.setText(location);
                    mcourse.setText(course);
                    mcollege.setText(college);
                    mindustry.setText(industry);
                    mgoals.setText(goals);
                    Glide.with(getApplicationContext()).load(photo).into(mprofile);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


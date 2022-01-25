package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mentee_Profile_Activity extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, moccupation, mcollege, mgoals, mcourse, mtype;
    private ImageView findMentor, mentors, settings;
    private Button home;
    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskills = findViewById(R.id.skills);
        mlanguage = findViewById(R.id.langauge);
        mlocation = findViewById(R.id.location);
        moccupation = findViewById(R.id.occupation);
        mcollege = findViewById(R.id.college);
        mgoals = findViewById(R.id.goals);
        mcourse = findViewById(R.id.course);
        findMentor = findViewById(R.id.findMentor);
        mentors = findViewById(R.id.mentorsProfile);
        settings = findViewById(R.id.editProfile);
        home = findViewById(R.id.updateMentee);


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String goals = snapshot.child("goals").getValue().toString();
                    String college = snapshot.child("college").getValue().toString();
                    String occupation = snapshot.child("occupation").getValue().toString();
                    String course = snapshot.child("course").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();

                    mname.setText(name);
                    mgoals.setText(goals);
                    mcollege.setText(college);
                    moccupation.setText(occupation);
                    mcourse.setText(course);
                    mtype.setText(type);
                    mskills.setText(skills);
                    mlanguage.setText(language);
                    mlocation.setText(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this,  Mentee_Find_Mentors_Activity.class);
                startActivity(i);
            }
        });

        mentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, MenteeList.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, Profile_Settings_Mentee_Activity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, MenteeMainActivity.class);
                startActivity(i);
            }
        });


    }
}
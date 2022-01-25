package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mentor_Profile_Activity extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, moccupation, mjobTitle, mbio, mindustry, mtype,mskills2;
    private ImageView reports, mentees, settings;
    private Button home;
    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskills = findViewById(R.id.skills1);
        mskills2 = findViewById(R.id.skills2);
        mlanguage = findViewById(R.id.langauge);
        mlocation = findViewById(R.id.location);
        mjobTitle = findViewById(R.id.jobTitle);
        mbio = findViewById(R.id.bio);
        mindustry = findViewById(R.id.industry);
        mentees = findViewById(R.id.menteesProfile);
        reports = findViewById(R.id.reportsProfile);
        settings = findViewById(R.id.editProfile);
        home = findViewById(R.id.home);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String jobTitle = snapshot.child("jobTitle").getValue().toString();
                    String industry = snapshot.child("industry").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String skills2 = snapshot.child("skill2").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();


                    mname.setText(name);
                    mbio.setText(bio);
                    mjobTitle.setText(jobTitle);
                    mindustry.setText(industry);
                    mtype.setText(type);
                    mskills.setText(skills);
                    mskills2.setText(skills2);
                    mlanguage.setText(language);
                    mlocation.setText(location);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mentor_Profile_Activity.this, "Reports", Toast.LENGTH_SHORT).show();
            }
        });

        mentees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorList.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, Profile_Settings_Mentor_Activity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorMainActivity.class);
                startActivity(i);
            }
        });



    }

}
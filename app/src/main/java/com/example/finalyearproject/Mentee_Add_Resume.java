package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;

public class Mentee_Add_Resume extends AppCompatActivity {
    private EditText mskills,  mcollege, mcourse, mgraduationYear, mbio, mgender, mcompany, mrole, mstartDate,
            mEndDate, mDescription, mHobbies, mProjects;
    private TextView mname;
    private Button mSaveCV;
    private DatabaseReference dr;
    private String currentUser;
    private String saveCurrentDate;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef, user_message_key;
    private String messageSenderID;
    private String messageReceiverID1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_add_resume);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        //user_message_key = RootRef.child("CV").child(messageSenderID).push();

        mname = findViewById(R.id.name);
        mskills = findViewById(R.id.interestSkills);
        mgraduationYear = findViewById(R.id.graduationYear);
        mbio = findViewById(R.id.bio);
        mcollege = findViewById(R.id.college);
        mcourse = findViewById(R.id.course);
        mcompany = findViewById(R.id.companyName);
        mrole = findViewById(R.id.role);
        mstartDate = findViewById(R.id.startDate);
        mEndDate = findViewById(R.id.endDate);
        mDescription = findViewById(R.id.workDescription);
        mHobbies = findViewById(R.id.interestHobbies);
        mProjects = findViewById(R.id.interestProjects);
        mSaveCV = findViewById(R.id.saveCV);


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String college = snapshot.child("college").getValue().toString();
                    String course = snapshot.child("course").getValue().toString();
                    String graduationYear = snapshot.child("graduationYear").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String company = snapshot.child("company").getValue().toString();
                    String role = snapshot.child("role").getValue().toString();
                    String startDate = snapshot.child("startDate").getValue().toString();
                    String endDate = snapshot.child("endDate").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String hobbies = snapshot.child("hobbies").getValue().toString();
                    String projects = snapshot.child("projects").getValue().toString();



                    mname.setText(name);
                    mgraduationYear.setText(graduationYear);
                    mcollege.setText(college);
                    mbio.setText(bio);
                    mcourse.setText(course);
                    mrole.setText(role);
                    mskills.setText(skills);
                    mcompany.setText(company);
                    mstartDate.setText(startDate);
                    mEndDate.setText(endDate);
                    mDescription.setText(description);
                    mHobbies.setText(hobbies);
                    mProjects.setText(projects);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mSaveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mname.getText().toString();
                String graduationYear = mgraduationYear.getText().toString();
                String college = mcollege.getText().toString();
                String bio = mbio.getText().toString();
                String course = mcourse.getText().toString();
                String skills = mskills.getText().toString();
                String role = mrole.getText().toString();
                String company = mcompany.getText().toString();
                String startDate = mstartDate.getText().toString();
                String endDate = mEndDate.getText().toString();
                String description = mDescription.getText().toString();
                String hobbies = mHobbies.getText().toString();
                String projects = mProjects.getText().toString();
                String id = RootRef.push().getKey();
                UpdateMentee(name, graduationYear, college, bio, course, skills, role, company,startDate, endDate, description, hobbies, projects, id);
            }
        });

    }

    private void UpdateMentee(String name, String graduationYear, String college, String bio, String course, String skills, String role, String company, String startDate, String endDate, String description, String hobbies, String projects, String id) {

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        DatabaseReference user_message_key = RootRef.child("CV").child(messageSenderID).push();
        String key = user_message_key.getKey();

        HashMap<String, Object> MenteeMap = new HashMap<>();
        MenteeMap.put("name", name);
        MenteeMap.put("bio", bio);
        MenteeMap.put("college", college);
        MenteeMap.put("graduationYear", graduationYear);
        MenteeMap.put("course", course);
        MenteeMap.put("skill1", skills);
        MenteeMap.put("role", role);
        MenteeMap.put("company", company);
        MenteeMap.put("startDate", startDate);
        MenteeMap.put("endDate", endDate);
        MenteeMap.put("description", description);
        MenteeMap.put("hobbies", hobbies);
        MenteeMap.put("projects", projects);
        MenteeMap.put("date", saveCurrentDate);
        MenteeMap.put("resumeid", key );



        user_message_key.updateChildren(MenteeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Mentee_Add_Resume.this, "Mentee CV saved successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Mentee_Add_Resume.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Mentee_Add_Resume.this, Mentee_Resume_Options.class);
        startActivity(i);
        finish();
    }



}

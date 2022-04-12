package com.example.finalyearproject.Mentees;

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

import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Mentee_Edit_Resume extends AppCompatActivity {

    private EditText  mSkills,  mCollege, mCourse, mGraduationYear, mBio, mCompany, mRole, mStartDate,
            mEndDate, mDescription, mHobbies, mProjects;
    private TextView mName;
    private Button mUpdateCV;
    private DatabaseReference RootRef;
    private String currentUser;
    private String resume_id = "";
    private FirebaseAuth firebaseAuth;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_edit_resume);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Edit_Resume.this, Mentee_Resume_Options.class);
                startActivity(i);
                finish();
            }
        });

        mName = findViewById(R.id.name);
        mSkills = findViewById(R.id.interestSkills);
        mGraduationYear = findViewById(R.id.graduationYear);
        mBio = findViewById(R.id.bio);
        mCollege = findViewById(R.id.college);
        mCourse = findViewById(R.id.course);
        mCompany = findViewById(R.id.companyName);
        mRole = findViewById(R.id.role);
        mStartDate = findViewById(R.id.startDate);
        mEndDate = findViewById(R.id.endDate);
        mDescription = findViewById(R.id.workDescription);
        mHobbies = findViewById(R.id.interestHobbies);
        mProjects = findViewById(R.id.interestProjects);
        mUpdateCV = findViewById(R.id.updateCV);


        Intent i = getIntent();
        resume_id = i.getStringExtra("resume_id");

        RootRef = FirebaseDatabase.getInstance().getReference().child("CV").child(currentUser).child(resume_id);



        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



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
                    resume_id = snapshot.child("resumeid").getValue().toString();


                    mName.setText(name);
                    mGraduationYear.setText(graduationYear);
                    mCollege.setText(college);
                    mBio.setText(bio);
                    mCourse.setText(course);
                    mRole.setText(role);
                    mSkills.setText(skills);
                    mCompany.setText(company);
                    mStartDate.setText(startDate);
                    mEndDate.setText(endDate);
                    mDescription.setText(description);
                    mHobbies.setText(hobbies);
                    mProjects.setText(projects);




                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mUpdateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String graduationYear = mGraduationYear.getText().toString();
                String college = mCollege.getText().toString();
                String bio = mBio.getText().toString();
                String course = mCourse.getText().toString();
                String skills = mSkills.getText().toString();
                String role = mRole.getText().toString();
                String company = mCompany.getText().toString();
                String startDate = mStartDate.getText().toString();
                String endDate = mEndDate.getText().toString();
                String description = mDescription.getText().toString();
                String hobbies = mHobbies.getText().toString();
                String projects = mProjects.getText().toString();
                UpdateMentee(name, graduationYear, college, bio, course, skills, role, company,startDate, endDate, description, hobbies, projects );
            }
        });

    }

    private void UpdateMentee(String name, String graduationYear, String college, String bio, String course, String skills, String role, String company, String startDate, String endDate, String description, String hobbies, String projects) {
        HashMap<String, Object> CVMap = new HashMap<>();
        CVMap.put("name", name);
        CVMap.put("bio", bio);
        CVMap.put("college", college);
        CVMap.put("graduationYear", graduationYear);
        CVMap.put("course", course);
        CVMap.put("skill1", skills);
        CVMap.put("role", role);
        CVMap.put("company", company);
        CVMap.put("startDate", startDate);
        CVMap.put("endDate", endDate);
        CVMap.put("description", description);
        CVMap.put("hobbies", hobbies);
        CVMap.put("projects", projects);


        RootRef.updateChildren(CVMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Mentee_Edit_Resume.this, "Mentee CV update successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Mentee_Edit_Resume.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Mentee_Edit_Resume.this, Mentee_Resume_Options.class);
        startActivity(i);
        finish();
    }


}






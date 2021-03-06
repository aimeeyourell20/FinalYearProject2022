package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Mentee_Add_Resume extends AppCompatActivity {
    private EditText mSkills,  mCollege, mCourse, mGraduationYear, mBio, mCompany, mRole, mStartDate,
            mEndDate, mDescription, mHobbies, mProjects, mCollegeGrades;
    private TextView mName;
    private Button mSaveCV;
    private DatabaseReference dr,RootRef;
    private String currentUser, date, menteeID;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_add_resume);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        menteeID = mAuth.getCurrentUser().getUid();

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
        mSaveCV = findViewById(R.id.saveCV);
        mCollegeGrades = findViewById(R.id.collegeGrades);


        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Add_Resume.this, Mentee_Resume_Options.class);
                startActivity(i);
                finish();
            }
        });

        Calendar calendar1 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(Calendar.YEAR, i);
                calendar1.set(Calendar.MONTH, i1);
                calendar1.set(Calendar.DAY_OF_MONTH, i2);

                calendar11();
            }

            private void calendar11() {

                String format = "dd/MM/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.UK);

                mStartDate.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        };

        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(Mentee_Add_Resume.this, dateSetListener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),  calendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        Calendar calendar2 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar2.set(Calendar.YEAR, i);
                calendar2.set(Calendar.MONTH, i1);
                calendar2.set(Calendar.DAY_OF_MONTH, i2);

                calendar11();
            }

            private void calendar11() {

                String format = "dd/MM/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.UK);

                mEndDate.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        };

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(Mentee_Add_Resume.this, dateSetListener2, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),  calendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mSaveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String grades = mCollegeGrades.getText().toString();
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
                String id = RootRef.push().getKey();
                UpdateMentee(name, graduationYear, college, bio, course, skills, role, company,startDate, endDate, description, hobbies, projects, id, grades);
            }
        });

    }

    private void UpdateMentee(String name, String graduationYear, String college, String bio, String course, String skills, String role, String company, String startDate, String endDate, String description, String hobbies, String projects, String id, String grades) {

        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        date = currentDate.format(calendarDate.getTime());

        DatabaseReference CV = RootRef.child("CV").child(menteeID).push();
        String key = CV.getKey();

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
        CVMap.put("date", date);
        CVMap.put("resumeid", key );
        CVMap.put("grades", grades );



        CV.updateChildren(CVMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

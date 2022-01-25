package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile_Settings_Mentee_Activity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private EditText mskills, mlanguage, moccupation, mcollege, mgoals, mcourse;
    private Spinner mlocation;
    private Button mupdateMentee;
    private TextView mtype, mname;
    private DatabaseReference dr;
    private String currentUser;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_profile_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);



        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskills = findViewById(R.id.skills);
        mlanguage = findViewById(R.id.langauge);
        mlocation = findViewById(R.id.locationSpinner);
        moccupation = findViewById(R.id.occupation);
        mcollege = findViewById(R.id.college);
        mgoals = findViewById(R.id.goals);
        mcourse = findViewById(R.id.course);
        mupdateMentee = findViewById(R.id.updateMentee);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String goals = snapshot.child("goals").getValue().toString();
                    String college = snapshot.child("college").getValue().toString();
                    String occupation = snapshot.child("occupation").getValue().toString();
                    String course = snapshot.child("course").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skill1 = snapshot.child("skill1").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();


                    mname.setText(name);
                    mgoals.setText(goals);
                    mcollege.setText(college);
                    moccupation.setText(occupation);
                    mcourse.setText(course);
                    mtype.setText(type);
                    mskills.setText(skill1);
                    mlanguage.setText(language);
                    mlocation.setSelected(Boolean.parseBoolean(location));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mupdateMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {

        String name = mname.getText().toString();
        String goals = mgoals.getText().toString();
        String college = mcollege.getText().toString();
        String occupation = moccupation.getText().toString();
        String course = mcourse.getText().toString();
        String skill1 = mskills.getText().toString();
        String language = mlanguage.getText().toString();
        String location = mlocation.getSelectedItem().toString();
        String type = mtype.getText().toString();

        if(TextUtils.isEmpty(name)){
            mname.setError("Name can not be left blank");
        }
        if(TextUtils.isEmpty(goals)){
            mgoals.setError("Goal can not be left blank");
        }
        if(TextUtils.isEmpty(college)){
            mcollege.setError("College can not be left blank");
        }
        if(TextUtils.isEmpty(occupation)){
            moccupation.setError("Occupation can not be left blank");
        }
        if(TextUtils.isEmpty(course)){
            mcourse.setError("Course can not be left blank");
        }
        if(TextUtils.isEmpty(skill1)){
            mskills.setError("Skills can not be left blank");
        }
        if(TextUtils.isEmpty(language)){
            mlanguage.setError("Language can not be left blank");
        }
        else{
            UpdateMentee(name, goals, college, occupation, course, skill1, language, location, type);
        }


    }

    private void UpdateMentee(String name, String goals, String college, String occupation, String course, String skill1, String language, String location, String type) {
        HashMap<String, Object> MenteeMap = new HashMap<>();
        MenteeMap.put("name", name);
        MenteeMap.put("goals", goals);
        MenteeMap.put("college", college);
        MenteeMap.put("occupation", occupation);
        MenteeMap.put("course", course);
        MenteeMap.put("skill1", skill1);
        MenteeMap.put("language", language);
        MenteeMap.put("location", location);
        MenteeMap.put("search", type + skill1 + language);


        dr.updateChildren(MenteeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Profile_Settings_Mentee_Activity.this, "Mentee details updated successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Profile_Settings_Mentee_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Profile_Settings_Mentee_Activity.this, MenteeMainActivity.class);
        startActivity(i);
        finish();
    }


}
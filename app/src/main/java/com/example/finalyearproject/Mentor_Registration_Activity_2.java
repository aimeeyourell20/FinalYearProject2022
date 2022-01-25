package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mentor_Registration_Activity_2 extends AppCompatActivity {

    private TextInputEditText mRegisterName;
    private Button mMentorFinishButton, mbackbutton;
    private Spinner mSkillSpinner, mLocationSpinner, mLanguageSpinner;
    private CircleImageView mprofile;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dr;
    String currentMentor;
    private Uri uri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_registration2);

        mRegisterName = findViewById(R.id.registerName);
        mMentorFinishButton = findViewById(R.id.mentorFinishButton);
        mSkillSpinner = findViewById(R.id.skillSpinner);
        mLocationSpinner = findViewById(R.id.locationSpinner);
        mLanguageSpinner = findViewById(R.id.languageSpinner);
        mprofile = findViewById(R.id.profile_image);
        firebaseAuth = FirebaseAuth.getInstance();
        currentMentor = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("Users").child(currentMentor);

        mMentorFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveMentorDetails();
            }
        });

    }

    private void SaveMentorDetails() {

        String name = mRegisterName.getText().toString();
        String skills = mSkillSpinner.getSelectedItem().toString();
        String location = mLocationSpinner.getSelectedItem().toString();
        String language = mLanguageSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            mRegisterName.setError("Fullname required");
            return;
        }
        if (skills.equals("Please select the skill you would like to improve")) {
            Toast.makeText(Mentor_Registration_Activity_2.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.equals("Please select the location you would prefer")) {
            Toast.makeText(Mentor_Registration_Activity_2.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }
        if (language.equals("Please select the language you would prefer")) {
            Toast.makeText(Mentor_Registration_Activity_2.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            HashMap <String, Object> mentor = new HashMap();
            mentor.put("name", name);
            mentor.put("skills", skills);
            mentor.put("location", location);
            mentor.put("language", language);
            mentor.put("status", "Hi there");
            mentor.put("type", "Mentor");
            mentor.put("industry", "Tech");
            mentor.put("jobTitle", "Customer Service");
            dr.updateChildren(mentor).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Mentor_Registration_Activity_2.this, "Success!", Toast.LENGTH_SHORT).show();
                        MainActivity();
                    }else{
                        Toast.makeText(Mentor_Registration_Activity_2.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void MainActivity() {
        Intent i = new Intent(Mentor_Registration_Activity_2.this, MentorMainActivity.class);
        startActivity(i);
        finish();
    }
}
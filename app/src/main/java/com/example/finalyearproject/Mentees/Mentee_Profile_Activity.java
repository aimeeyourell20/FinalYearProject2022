package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Industry_Fragments.SearchIndustryFragment;
import com.example.finalyearproject.MenteeList;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Mentee_Profile_Activity extends AppCompatActivity {

    private TextView mName, mSkills, mLocation, mLanguage, mOccupation, mCollege, mGoals, mCourse, mType;
    private ImageView mFindMentor, mMentors, mSettings;
    private Button mHome;
    private DatabaseReference RootRef;
    private ImageView mProfile;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private static int PICK_IMAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mName = findViewById(R.id.profileNameTxt);
        mType = findViewById(R.id.typeProfileTxt);
        mSkills = findViewById(R.id.skills);
        mLanguage = findViewById(R.id.langauge);
        mLocation = findViewById(R.id.location);
        mOccupation = findViewById(R.id.occupation);
        mCollege = findViewById(R.id.college);
        mGoals = findViewById(R.id.goals);
        mCourse = findViewById(R.id.course);
        mFindMentor = findViewById(R.id.findMentor);
        mMentors = findViewById(R.id.mentorsProfile);
        mSettings = findViewById(R.id.editProfile);
        mHome = findViewById(R.id.updateMentee);
        mProfile = findViewById(R.id.profileImageProfile);


        //Sets users information
        RootRef.addValueEventListener(new ValueEventListener() {
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
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);

                    mName.setText(name);
                    mGoals.setText(goals);
                    mCollege.setText(college);
                    mOccupation.setText(occupation);
                    mCourse.setText(course);
                    mType.setText(type);
                    mSkills.setText(skills);
                    mLanguage.setText(language);
                    mLocation.setText(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mFindMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this,  SearchIndustryFragment.class);
                startActivity(i);
                finish();
            }
        });

        mMentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, MenteeList.class);
                startActivity(i);
                finish();
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, Profile_Settings_Mentee_Activity.class);
                startActivity(i);
                finish();
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Profile_Activity.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

//Sets profile image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

    }

}
package com.example.finalyearproject.Mentor;

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
import com.example.finalyearproject.Reports.Mentor_Reports;
import com.example.finalyearproject.MentorList;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Mentor_Profile_Activity extends AppCompatActivity {

    private TextView mName, mSkills, mLocation, mLanguage, mJobTitle, mBio, mIndustry, mType,mSkills2;
    private ImageView mReports, mMentees, mSettings, mProfile;
    private Button home;
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private static int PICK_IMAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mName = findViewById(R.id.profileNameTxt);
        mType = findViewById(R.id.typeProfileTxt);
        mSkills = findViewById(R.id.skills1);
        mSkills2 = findViewById(R.id.skills2);
        mLanguage = findViewById(R.id.langauge);
        mLocation = findViewById(R.id.location);
        mJobTitle = findViewById(R.id.jobTitle);
        mBio = findViewById(R.id.bio);
        mIndustry = findViewById(R.id.industry);
        mMentees = findViewById(R.id.menteesProfile);
        mReports = findViewById(R.id.reportsProfile);
        mSettings = findViewById(R.id.editProfile);
        home = findViewById(R.id.home);
        mProfile = findViewById(R.id.profileImageProfile);

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String jobTitle = snapshot.child("jobType").getValue().toString();
                    String industry = snapshot.child("industry").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String skills2 = snapshot.child("skill2").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);


                    mName.setText(name);
                    mBio.setText(bio);
                    mJobTitle.setText(jobTitle);
                    mIndustry.setText(industry);
                    mType.setText(type);
                    mSkills.setText(skills);
                    mSkills2.setText(skills2);
                    mLanguage.setText(language);
                    mLocation.setText(location);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, Mentor_Reports.class);
                startActivity(i);            }
        });

        mMentees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorList.class);
                startActivity(i);
                finish();
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, Profile_Settings_Mentor_Activity.class);
                startActivity(i);
                finish();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorMainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

                Uri image = data.getData();

                CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

                }
            }

        }


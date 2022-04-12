package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.API.CareerOptionsMentee;
import com.example.finalyearproject.Industry_Fragments.SearchIndustryFragment;
import com.example.finalyearproject.Kotlin.ChatbotKotlin;
import com.example.finalyearproject.Reports.Mentee_Reports;
import com.example.finalyearproject.Login_Activity;
import com.example.finalyearproject.MenteeList;
import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MenteeMainActivity extends AppCompatActivity{

    private ImageView mMentors, mFindMentor, mSettings, mGoals, mCareer, mCv, mProfile, mLogout, mMeeting, mChatbot, mReports;
    private TextView mProfileName, mType, mMentorships;
    private DatabaseReference RootRef, Mentorships;
    private DatabaseReference user;
    private FirebaseAuth firebaseAuth;
    private static int PICK_IMAGE = 123;
    private String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_main);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference().child("users");
        Mentorships = FirebaseDatabase.getInstance().getReference().child("Mentorship").child(currentUser);

        mMentors = findViewById(R.id.mentor);
        mFindMentor = findViewById(R.id.findMentor);
        mSettings = findViewById(R.id.settings);
        mCareer = findViewById(R.id.careerFinder);
        mCv = findViewById(R.id.resume);
        mGoals = findViewById(R.id.goals);
        mProfileName = findViewById(R.id.profilename);
        mProfile = findViewById(R.id.profileimage);
        mType = findViewById(R.id.type);
        mLogout = findViewById(R.id.logout);
        mMeeting = findViewById(R.id.meeting);
        mChatbot = findViewById(R.id.chatbot);
        mReports = findViewById(R.id.reports);
        mMentorships = findViewById(R.id.mentorships);

        Mentorships.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int counter = (int) snapshot.getChildrenCount();
                String userCounter = String.valueOf(counter);
                mMentorships.setText("Current Ongoing Mentorships: " + userCounter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mProfileName.setText(name);

                    String types = snapshot.child("type").getValue().toString();
                    mType.setText(types);

                    String photo = "";
                    photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mMentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentors();
            }
        });


        mFindMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });


        mCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Career();
            }
        });

        mCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentee_Resume();
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });

        mGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals();
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });


        mMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting();
            }
        });

        mChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatBot();
            }
        });

        mReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reports();
            }
        });

    }

    //Sets users profile image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){

            Login();
        }else{
            CheckUser();
        }
    }

    private void CheckUser() {

        final String user1 = firebaseAuth.getCurrentUser().getUid();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.hasChild(user1)){

                    RegistrationActivity2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RegistrationActivity2() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Registration_Activity.class);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }



    private void Career() {
        Intent i = new Intent(MenteeMainActivity.this, CareerOptionsMentee.class);
        startActivity(i);
    }

    private void Goals() {
        Intent i = new Intent(MenteeMainActivity.this, Goals_Activity_Mentee.class);
        startActivity(i);
    }

    private void Mentors() {

        Intent i = new Intent(MenteeMainActivity.this, MenteeList.class);
        startActivity(i);
    }

    private void Search() {
        Intent i = new Intent(MenteeMainActivity.this, SearchIndustryFragment.class);
        startActivity(i);
    }

    private void Mentee_Resume() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Resume_Options.class);
        startActivity(i);finish();
    }

    private void Profile() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Profile_Activity.class);
        startActivity(i);
    }

    private void Settings() {
        Intent i = new Intent(MenteeMainActivity.this, Profile_Settings_Mentee_Activity.class);
        startActivity(i);

    }

    private void ChatBot() {
        Intent i = new Intent(MenteeMainActivity.this, ChatbotKotlin.class);
        startActivity(i);


    }

    private void Signout() {
        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }

    private void Meeting() {
        Intent i = new Intent(MenteeMainActivity.this, Meetings_Activity_Mentee.class);
        startActivity(i);

    }

    private void Reports() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Reports.class);
        startActivity(i);

    }

}
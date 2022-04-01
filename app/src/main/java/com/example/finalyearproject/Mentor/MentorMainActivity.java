package com.example.finalyearproject.Mentor;

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
import com.example.finalyearproject.Reports.Mentor_Reports;
import com.example.finalyearproject.Login_Activity;
import com.example.finalyearproject.MentorList;
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

public class MentorMainActivity extends AppCompatActivity{

    private ImageView mMentees, mRequests, mSettings, mReports, mGoals, mProfile, mLogout, mMeetings;
    private TextView mProfileName, mType;
    private DatabaseReference RootRef, MentorRef;
    private FirebaseAuth firebaseAuth;
    private static int PICK_IMAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);

        firebaseAuth = FirebaseAuth.getInstance();
        MentorRef = FirebaseDatabase.getInstance().getReference().child("users");

        mMentees = findViewById(R.id.mentees);
        mRequests = findViewById(R.id.requests);
        mSettings = findViewById(R.id.settings);
        mReports = findViewById(R.id.reports);
        mGoals = findViewById(R.id.goals);
        mProfileName = findViewById(R.id.profilename);
        mProfile = findViewById(R.id.profileimage);
        mType = findViewById(R.id.type);
        mMeetings = findViewById(R.id.meeting);
        mLogout = findViewById(R.id.logout);



        RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mProfileName.setText(name);

                    String types = snapshot.child("type").getValue().toString();
                    mType.setText(types);

                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mMentees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentees();
            }
        });


        mRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Requests();
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });

        mGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals();
            }
        });

        mReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reports();
            }
        });


        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });

        mMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meetings();
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
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

        MentorRef.addValueEventListener(new ValueEventListener() {
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
        Intent i = new Intent(MentorMainActivity.this, Mentor_Registration_Activity.class);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(MentorMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }



    private void Reports() {
        Intent i = new Intent(MentorMainActivity.this, Mentor_Reports.class);
        startActivity(i);
        finish();

    }

    private void Goals() {
        Intent i = new Intent(MentorMainActivity.this, Goals_Activity_Mentor.class);
        startActivity(i);
        finish();
    }

    private void Mentees() {

        Intent i = new Intent(MentorMainActivity.this, MentorList.class);
        startActivity(i);
        finish();
    }

    private void Requests() {
        Intent i = new Intent(MentorMainActivity.this, Mentee_Request_Activity.class);
        startActivity(i);
        finish();
    }


    private void Profile() {
        Intent i = new Intent(MentorMainActivity.this, Mentor_Profile_Activity.class);
        startActivity(i);
        finish();
    }

    private void Settings() {
        Intent i = new Intent(MentorMainActivity.this, Profile_Settings_Mentor_Activity.class);
        startActivity(i);
        finish();
    }

    private void Meetings() {
        Intent i = new Intent(MentorMainActivity.this, Meetings_Activity_Mentor.class);
        startActivity(i);
        finish();
    }


    private void Signout() {
        Intent i = new Intent(MentorMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }

}
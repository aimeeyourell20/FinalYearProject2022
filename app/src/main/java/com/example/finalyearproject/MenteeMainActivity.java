package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.fragments.Search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenteeMainActivity extends AppCompatActivity{

    private ImageView mentors, findMentor, settings, messages, career, cv, profile, logout, meeting;
    private TextView profileName, type;
    private DatabaseReference dr;
    private DatabaseReference user;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_main);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference().child("users");

        mentors = findViewById(R.id.mentor);
        findMentor = findViewById(R.id.findMentor);
        settings = findViewById(R.id.settings);
        messages = findViewById(R.id.messages);
        career = findViewById(R.id.careerFinder);
        cv = findViewById(R.id.resume);
        profileName = findViewById(R.id.profilename);
        profile = findViewById(R.id.profileimage);
        type = findViewById(R.id.type);
        logout = findViewById(R.id.logout);
        meeting = findViewById(R.id.meeting);



        dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    profileName.setText(name);

                    String types = snapshot.child("type").getValue().toString();
                    type.setText(types);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentors();
            }
        });


        findMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals();
            }
        });

        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Career();
            }
        });

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentee_Resume();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });


        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting();
            }
        });

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
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }



    private void Career() {
        Intent i = new Intent(MenteeMainActivity.this, CareerOptions.class);
        startActivity(i);
    }

    private void Goals() {
        Intent i = new Intent(MenteeMainActivity.this, Goals.class);
        startActivity(i);    }

    private void Mentors() {

        Intent i = new Intent(MenteeMainActivity.this, MenteeList.class);
        startActivity(i);
    }

    private void Search() {
        Intent i = new Intent(MenteeMainActivity.this, Search.class);
        startActivity(i);
    }

    private void Mentee_Resume() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Resume.class);
        startActivity(i);
    }

    private void Profile() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Profile_Activity.class);
        startActivity(i);
    }

    private void Settings() {
        Intent i = new Intent(MenteeMainActivity.this, Profile_Settings_Mentee_Activity.class);
        startActivity(i);
    }

    private void RateMentor() {
        Intent i = new Intent(MenteeMainActivity.this, Rating_Activity.class);
        startActivity(i);

    }

    private void Signout() {
        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }

    private void Meeting() {
        Intent i = new Intent(MenteeMainActivity.this, Meetings.class);
        startActivity(i);
        finish();
    }

}
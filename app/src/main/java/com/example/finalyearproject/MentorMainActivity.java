package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MentorMainActivity extends AppCompatActivity{

    private ImageView mentees, requests, settings, reports, goals, profile, logout;
    private TextView profileName, type;
    private DatabaseReference dr;
    private DatabaseReference user;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseDatabase.getInstance().getReference().child("users");

        mentees = findViewById(R.id.mentees);
        requests = findViewById(R.id.requests);
        settings = findViewById(R.id.settings);
        reports = findViewById(R.id.reports);
        goals = findViewById(R.id.goals);
        profileName = findViewById(R.id.profilename);
        profile = findViewById(R.id.profileimage);
        type = findViewById(R.id.type);
        logout = findViewById(R.id.logout);



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

        mentees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentees();
            }
        });


        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Requests();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals();
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reports();
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
        Intent i = new Intent(MentorMainActivity.this, Mentor_Registration_Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(MentorMainActivity.this, Login_Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }



    private void Reports() {
        Toast.makeText(MentorMainActivity.this, "Reports", Toast.LENGTH_SHORT).show();

    }

    private void Goals() {
        Toast.makeText(MentorMainActivity.this, "Goals", Toast.LENGTH_SHORT).show();
    }

    private void Mentees() {

        Intent i = new Intent(MentorMainActivity.this, MentorList.class);
        startActivity(i);
    }

    private void Requests() {
        Intent i = new Intent(MentorMainActivity.this, Mentee_Request_Activity.class);
        startActivity(i);
    }


    private void Profile() {
        Intent i = new Intent(MentorMainActivity.this, Mentor_Profile_Activity.class);
        startActivity(i);
    }

    private void Settings() {
        Intent i = new Intent(MentorMainActivity.this, Profile_Settings_Mentor_Activity.class);
        startActivity(i);
    }


    private void Signout() {
        Intent i = new Intent(MentorMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }

}
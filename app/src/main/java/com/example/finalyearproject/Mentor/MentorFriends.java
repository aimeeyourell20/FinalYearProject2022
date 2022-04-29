package com.example.finalyearproject.Mentor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Mentees.Message_Mentee;
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

public class MentorFriends extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, mcollege, mcourse, mindustry, mgoals, mIsSeen;
    private ImageView mHome, mMessageProfile;;
    private Button SendFriendReqButton, DeclineFriendRequestButton;
    private DatabaseReference FriendsRequestRef, UsersRef, FriendsRef, RootRef, Messaging;
    private FirebaseAuth mAuth;
    private ImageView mprofile;
    private String senderUserId;
    private String menteeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_friends);


        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        mname = findViewById(R.id.personname);
        mskills = findViewById(R.id.personskills);
        mlanguage = findViewById(R.id.personlanguage);
        mlocation = findViewById(R.id.personlocation);
        mcollege = findViewById(R.id.personcollege);
        mcourse = findViewById(R.id.personcourse);
        mindustry = findViewById(R.id.personIndustry);
        mgoals = findViewById(R.id.persongoals);
        mprofile = findViewById(R.id.profileImageProfile);
        SendFriendReqButton = (Button) findViewById(R.id.sendRequest);
        DeclineFriendRequestButton = (Button) findViewById(R.id.cancelRequest);
        mMessageProfile = findViewById(R.id.messageProfile);
        mIsSeen = findViewById(R.id.isSeen);


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeId = (String) extras.get("menteeid");
            }
        }

        mHome = findViewById(R.id.home);

        Messaging = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(menteeId).child(senderUserId);
        Messaging.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Messaging = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(senderUserId).child(menteeId);
                    HashMap messaging = new HashMap();
                    messaging.put("isRead", false);
                }else {

                    String isRead = snapshot.child("isRead").getValue().toString();
                    if (isRead.equals("true")) {
                        mIsSeen.setBackgroundColor(Color.GREEN);
                    }
                    if (isRead.equals("false")) {
                        mIsSeen.setBackgroundColor(Color.RED);

                    }
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MentorFriends.this);


                builder.setTitle("Messages");

                builder.setMessage("Have you read messages?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        RootRef = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(senderUserId).child(menteeId);
                        HashMap messaging = new HashMap();
                        messaging.put("isRead", true);

                        RootRef.updateChildren(messaging).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Intent i = new Intent(MentorFriends.this, MentorMainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RootRef = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(senderUserId).child(menteeId);
                        HashMap messaging = new HashMap();
                        messaging.put("isRead", false);

                        RootRef.updateChildren(messaging).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Intent i = new Intent(MentorFriends.this, MentorMainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }

                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        mMessageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentorFriends.this, Message_Mentee.class);
                intent.putExtra("menteeid", menteeId);
                intent.putExtra("name",  mname.getText());
                startActivity(intent);
            }
        });



        //receiverUserId = getIntent().getExtras().get("menteeid").toString();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");


        UsersRef.child(menteeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String skills = dataSnapshot.child("skill1").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String college = dataSnapshot.child("college").getValue().toString();
                    String course = dataSnapshot.child("course").getValue().toString();
                    String industry = dataSnapshot.child("industry").getValue().toString();
                    String goals = dataSnapshot.child("goals").getValue().toString();
                    String photo = dataSnapshot.child("profileimage").getValue().toString();


                    mname.setText(name);
                    mskills.setText(skills);
                    mlanguage.setText(language);
                    mlocation.setText(location);
                    mcourse.setText(course);
                    mcollege.setText(college);
                    mindustry.setText(industry);
                    mgoals.setText(goals);
                    Glide.with(getApplicationContext()).load(photo).into(mprofile);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


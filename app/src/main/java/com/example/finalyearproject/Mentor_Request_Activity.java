package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Mentor.MentorMainActivity;
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

public class Mentor_Request_Activity extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, mtype, mcollege, mcourse, moccupation, mgoals;
    private Button SendFriendReqButton, DeclineFriendRequestButton;
    private DatabaseReference MentorshipRequestRef, UsersRef, MentorshipRef;
    private FirebaseAuth mAuth;
    private ImageView mprofile;
    private String mentorId, Mentorship_State, saveCurrentDate;
    private String menteeId = "";
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile2);


        mAuth = FirebaseAuth.getInstance();
        mentorId = mAuth.getCurrentUser().getUid();
        mHome = findViewById(R.id.home);

        mname = findViewById(R.id.personname);
        mtype = findViewById(R.id.persontype);
        mskills = findViewById(R.id.personskills);
        mlanguage = findViewById(R.id.personlanguage);
        mlocation = findViewById(R.id.personlocation);
        mcollege = findViewById(R.id.personcollege);
        mcourse = findViewById(R.id.personcourse);
        moccupation = findViewById(R.id.personoccupation);
        mgoals = findViewById(R.id.persongoals);
        mprofile = findViewById(R.id.profileImageProfile);
        SendFriendReqButton = (Button) findViewById(R.id.sendRequest);
        DeclineFriendRequestButton = (Button) findViewById(R.id.cancelRequest);

        Mentorship_State = "not_mentorship";


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeId = (String) extras.get("menteeid");
            }
        }

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Request_Activity.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        MentorshipRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        MentorshipRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");


        UsersRef.child(menteeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String type = dataSnapshot.child("type").getValue().toString();
                    String skills = dataSnapshot.child("skill1").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String college = dataSnapshot.child("college").getValue().toString();
                    String course = dataSnapshot.child("course").getValue().toString();
                    String occupation = dataSnapshot.child("occupation").getValue().toString();
                    String goals = dataSnapshot.child("goals").getValue().toString();
                    String photo = dataSnapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mprofile);



                    mname.setText(name);
                    mtype.setText(type);
                    mskills.setText(skills);
                    mlanguage.setText(language);
                    mlocation.setText(location);
                    mcourse.setText(course);
                    mcollege.setText(college);
                    moccupation.setText(occupation);
                    mgoals.setText(goals);

                    MentorshipButtons();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
        DeclineFriendRequestButton.setEnabled(false);

        if(!mentorId.equals(menteeId))
        {
            SendFriendReqButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (Mentorship_State.equals("request_received"))
                    {
                        AcceptMentorshipRequest();
                    }
                    if (Mentorship_State.equals("mentorship"))
                    {
                        UnFriendMentorship();
                    }
                }
            });
        }
    }

    private void UnFriendMentorship() {

        MentorshipRef.child(mentorId).child(menteeId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            MentorshipRef.child(menteeId).child(mentorId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {

                                                //If there isnt a mentorship then a request can be sent
                                                SendFriendReqButton.setEnabled(true);
                                                Mentorship_State = "not_mentorship";
                                                SendFriendReqButton.setText("Send Mentorship Request");
                                                SendFriendReqButton.setVisibility(View.INVISIBLE);

                                                //Only mentee can send a mentorship requrst
                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void AcceptMentorshipRequest() {

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        MentorshipRef.child(mentorId).child(menteeId).child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            //Saves the mentorship date
                            MentorshipRef.child(menteeId).child(mentorId).child("date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                MentorshipRequestRef.child(mentorId).child(menteeId)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                                        {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if(task.isSuccessful())
                                                                {
                                                                    MentorshipRequestRef.child(menteeId).child(mentorId).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>()
                                                                            {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task)
                                                                                {
                                                                                    if(task.isSuccessful())
                                                                                    {

                                                                                        //If their is a mentorship it can be ended
                                                                                        SendFriendReqButton.setEnabled(true);
                                                                                        Mentorship_State = "mentorship";
                                                                                        SendFriendReqButton.setText("End Mentorship");


                                                                                        //Only mentee can view this button
                                                                                        DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                                                        DeclineFriendRequestButton.setEnabled(false);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                            }
                        }
                    });
        }

    private void MentorshipButtons()
    {
        MentorshipRequestRef.child(mentorId)
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.hasChild(menteeId))
                        {

                            String request_type = dataSnapshot.child(menteeId).child("request_type").getValue().toString();

                            //Mentor can decline mentorship request
                            if(request_type.equals("sent"))
                            {
                                Mentorship_State = "request_sent";
                                SendFriendReqButton.setText("Cancel Mentorship request");

                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                DeclineFriendRequestButton.setEnabled(false);
                            }

                            //Mentor can accept mentorship request
                            else if (request_type.equals("received"))
                            {
                                Mentorship_State = "request_received";
                                SendFriendReqButton.setText("Accept Mentorship Request");

                                DeclineFriendRequestButton.setVisibility(View.VISIBLE);
                                DeclineFriendRequestButton.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
        }

}
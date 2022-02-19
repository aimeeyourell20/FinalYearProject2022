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

public class Person_Profile_Activity_2 extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, mtype, mcollege, mcourse, moccupation, mgoals;
    private Button SendFriendReqButton, DeclineFriendRequestButton;
    private DatabaseReference FriendsRequestRef, UsersRef, FriendsRef;
    private FirebaseAuth mAuth;
    private ImageView mprofile;
    private String senderUserId, CURRENT_STATE, saveCurrentDate;
    private String receiverUserId = "";
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile2);


        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                receiverUserId = (String) extras.get("menteeid");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Person_Profile_Activity_2.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //receiverUserId = getIntent().getExtras().get("menteeid").toString();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");

        IntializeFields();

        UsersRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
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



                    mname.setText("Fullname " + name);
                    mtype.setText("Type " + type);
                    mskills.setText("Skills " + skills);
                    mlanguage.setText("Language " + language);
                    mlocation.setText("Location " + location);
                    mcourse.setText("Course " + course);
                    mcollege.setText("College " + college);
                    moccupation.setText("Occupation " + occupation);
                    mgoals.setText("Goals " + goals);

                    MaintananceofButtons();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
        DeclineFriendRequestButton.setEnabled(false);

        if(!senderUserId.equals(receiverUserId))
        {
            SendFriendReqButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //SendFriendReqButton.setEnabled(false);

                    /*if(CURRENT_STATE.equals("not_firends"))
                    {
                        SendFriendRequestToaPerson();
                    }
                    if(CURRENT_STATE.equals("request_sent"))
                    {
                        CancelFriendrequest();
                    }*/
                    if (CURRENT_STATE.equals("request_received"))
                    {
                        AcceptFriendRequest();
                    }
                    if (CURRENT_STATE.equals("mentorship"))
                    {
                        UnFriendAnExistingFriend();
                    }
                }
            });
        }
        else
        {
            // DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
            //SendFriendReqButton.setVisibility(View.INVISIBLE);
        }
    }

    private void UnFriendAnExistingFriend() {

        FriendsRef.child(senderUserId).child(receiverUserId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            FriendsRef.child(receiverUserId).child(senderUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                SendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "not_mentorship";
                                                SendFriendReqButton.setText("Send Mentorship Request");

                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void AcceptFriendRequest() {

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        FriendsRef.child(senderUserId).child(receiverUserId).child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            FriendsRef.child(receiverUserId).child(senderUserId).child("date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                FriendsRequestRef.child(senderUserId).child(receiverUserId)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                                        {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if(task.isSuccessful())
                                                                {
                                                                    FriendsRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>()
                                                                            {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task)
                                                                                {
                                                                                    if(task.isSuccessful())
                                                                                    {
                                                                                        SendFriendReqButton.setEnabled(true);
                                                                                        CURRENT_STATE = "mentorship";
                                                                                        SendFriendReqButton.setText("End Mentorship");

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

    private void CancelFriendrequest() {

        FriendsRequestRef.child(senderUserId).child(receiverUserId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            FriendsRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                SendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "not_mentorship";
                                                SendFriendReqButton.setText("Send Mentorship Request");

                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void MaintananceofButtons()
    {
        FriendsRequestRef.child(senderUserId)
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.hasChild(receiverUserId))
                        {
                            String request_type = dataSnapshot.child(receiverUserId).child("request_type").getValue().toString();

                            if(request_type.equals("sent"))
                            {
                                CURRENT_STATE = "request_sent";
                                SendFriendReqButton.setText("Cancel Mentorship request");

                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                DeclineFriendRequestButton.setEnabled(false);
                            }
                            else if (request_type.equals("received"))
                            {
                                CURRENT_STATE = "request_received";
                                SendFriendReqButton.setText("Accept Mentorship Request");

                                DeclineFriendRequestButton.setVisibility(View.VISIBLE);
                                DeclineFriendRequestButton.setEnabled(true);

                                DeclineFriendRequestButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        CancelFriendrequest();
                                    }
                                });
                            }
                        }
                        else
                        {
                            FriendsRef.child(senderUserId)
                                    .addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            if(dataSnapshot.hasChild(receiverUserId))
                                            {
                                                CURRENT_STATE = "mentorship";
                                                SendFriendReqButton.setText("Cancel Mentorship");

                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void SendFriendRequestToaPerson() {
        FriendsRequestRef.child(senderUserId).child(receiverUserId)
                .child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            FriendsRequestRef.child(receiverUserId).child(senderUserId).child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                SendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "request_sent";
                                                SendFriendReqButton.setText("Cancel Mentorship Request");

                                                DeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void IntializeFields()
    {
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

        CURRENT_STATE = "not_mentorship";
    }
}
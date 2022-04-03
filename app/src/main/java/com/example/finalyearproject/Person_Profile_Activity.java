package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Mentees.MenteeMainActivity;
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

public class Person_Profile_Activity extends AppCompatActivity {

    private TextView mName, mSkills1, mLocation, mLanguage, mJobTitle, mBio, mIndustry, mType, mCompany;
    private Button mSendFriendReqButton, mDeclineFriendRequestButton;
    private ImageView mProfile;
    private DatabaseReference FriendsRequestRef, UsersRef, FriendsRef, RootRef, Rating;
    private FirebaseAuth mAuth;
    private String senderUserId, CURRENT_STATE, saveCurrentDate;
    private String receiverUserId = "";
    private ImageView mHome;
    private  RatingBar mRatingBar;
    private String rating ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);


        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                receiverUserId = (String) extras.get("mentorid");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        mRatingBar = findViewById(R.id.RatingBar);

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Person_Profile_Activity.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //receiverUserId = getIntent().getExtras().get("mentorid").toString();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        FriendsRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        FriendsRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");
        RootRef = FirebaseDatabase.getInstance().getReference().child("users");

        IntializeFields();

        UsersRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String bio = dataSnapshot.child("bio").getValue().toString();
                    String jobTitle = dataSnapshot.child("jobType").getValue().toString();
                    String industry = dataSnapshot.child("industry").getValue().toString();
                    String type = dataSnapshot.child("type").getValue().toString();
                    String skill1 = dataSnapshot.child("skill1").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String company = dataSnapshot.child("company").getValue().toString();
                    String photo = dataSnapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);

                    if(dataSnapshot.hasChild("Rating")){
                        rating = dataSnapshot.child("Rating").child(senderUserId).child("rating").getValue().toString();
                        mRatingBar.setRating(Float.parseFloat(rating));
                    }
                    else{
                        RootRef.child(receiverUserId).child("Rating").child(senderUserId).child("rating").setValue("0");
                        mRatingBar.setRating(Float.parseFloat("0"));
                    }

                    mName.setText(name);
                    mBio.setText(bio);
                    mJobTitle.setText(jobTitle);
                    mIndustry.setText(industry);
                    mType.setText(type);
                    mSkills1.setText(skill1);
                    mLocation.setText(location);
                    mCompany.setText(company);



                    MaintananceofButtons();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
        mDeclineFriendRequestButton.setEnabled(false);

        if(!senderUserId.equals(receiverUserId))
        {
            mSendFriendReqButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mSendFriendReqButton.setEnabled(false);

                    if(CURRENT_STATE.equals("not_mentorship"))
                    {
                        SendFriendRequestToaPerson();
                    }
                    if(CURRENT_STATE.equals("request_sent"))
                    {
                        CancelFriendrequest();
                    }
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
            mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
            mSendFriendReqButton.setVisibility(View.INVISIBLE);
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
                                                mSendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "not_mentorship";
                                                mSendFriendReqButton.setText("Send Mentorship Request");

                                                mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                mDeclineFriendRequestButton.setEnabled(false);
                                                mRatingBar.setIsIndicator(false);
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
                                                                                        mSendFriendReqButton.setEnabled(true);
                                                                                        CURRENT_STATE = "mentorship";
                                                                                        mSendFriendReqButton.setText("End Mentorship");

                                                                                        mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                                                        mDeclineFriendRequestButton.setEnabled(false);

                                                                                        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                                                                            @Override
                                                                                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                                                                RootRef.child(receiverUserId).child(senderUserId).child("rating").push();
                                                                                                RootRef.child(receiverUserId).child("Rating").child(senderUserId).child("rating").setValue(v);
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
                                                mSendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "not_mentorship";
                                                mSendFriendReqButton.setText("Send Mentorship Request");

                                                mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                mDeclineFriendRequestButton.setEnabled(false);
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
                                mSendFriendReqButton.setText("Cancel Mentorship request");

                                mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                mDeclineFriendRequestButton.setEnabled(false);
                            }
                            else if (request_type.equals("received"))
                            {
                                CURRENT_STATE = "request_received";
                                mSendFriendReqButton.setText("Accept Mentorship Request");

                                mDeclineFriendRequestButton.setVisibility(View.VISIBLE);
                                mDeclineFriendRequestButton.setEnabled(true);

                                mDeclineFriendRequestButton.setOnClickListener(new View.OnClickListener()
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
                                                mSendFriendReqButton.setText("Cancel Mentorship");

                                                mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                mDeclineFriendRequestButton.setEnabled(false);

                                                mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                                    @Override
                                                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                        RootRef.child(receiverUserId).child(senderUserId).child("rating").push();
                                                        RootRef.child(receiverUserId).child("Rating").child(senderUserId).child("rating").setValue(v);
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
                                                mSendFriendReqButton.setEnabled(true);
                                                CURRENT_STATE = "request_sent";
                                                mSendFriendReqButton.setText("Cancel Mentorship Request");

                                                mDeclineFriendRequestButton.setVisibility(View.INVISIBLE);
                                                mDeclineFriendRequestButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }





    private void IntializeFields()
    {
        mName = findViewById(R.id.personname);
        mType = findViewById(R.id.persontype);
        mSkills1 = findViewById(R.id.personskills1);
        mCompany = findViewById(R.id.personcompany);
        mLanguage = findViewById(R.id.personlanguage);
        mLocation = findViewById(R.id.personlocation);
        mJobTitle = findViewById(R.id.personjobTitle);
        mBio = findViewById(R.id.personbio);
        mIndustry = findViewById(R.id.personindustry);
        mProfile = findViewById(R.id.profileImageProfile);
        mSendFriendReqButton = (Button) findViewById(R.id.sendRequest);
        mDeclineFriendRequestButton = (Button) findViewById(R.id.cancelRequest);


        //RateMentor();
        CURRENT_STATE = "not_mentorship";
    }


}
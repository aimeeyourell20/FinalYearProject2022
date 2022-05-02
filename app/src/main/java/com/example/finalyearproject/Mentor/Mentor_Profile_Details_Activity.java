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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Mentees.Goals_Add_Activity;
import com.example.finalyearproject.Mentees.MeetingRequest;
import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Mentor_Profile_Details_Activity extends AppCompatActivity {

    private TextView mName, mSkills1, mLocation, mLanguage, mJobTitle, mBio, mIndustry, mIsSeen, mCompany;
    private Button mSendMentorshipReqButton, mDeclineMentorshipReqButton;
    private ImageView mProfile, mMeetingProfile, mGoalsProfile, mMessageProfile;
    private DatabaseReference MentorshipRequestRef, UsersRef, MentorshipRef, RootRef, Rating, Messages, Messaging;
    private FirebaseAuth mAuth;
    private String menteeId, CURRENT_MENTORSHIP, saveCurrentDate;
    private String mentorId = "";
    private ImageView mHome;
    private  RatingBar mRatingBar;
    private String rating ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile3);


        mAuth = FirebaseAuth.getInstance();
        menteeId = mAuth.getCurrentUser().getUid();

        mName = findViewById(R.id.personname);
        mSkills1 = findViewById(R.id.personskills1);
        mCompany = findViewById(R.id.personcompany);
        mLanguage = findViewById(R.id.personlanguage);
        mLocation = findViewById(R.id.personlocation);
        mJobTitle = findViewById(R.id.personjobTitle);
        mBio = findViewById(R.id.personbio);
        mIndustry = findViewById(R.id.personindustry);
        mProfile = findViewById(R.id.profileImageProfile);
        mSendMentorshipReqButton = (Button) findViewById(R.id.sendRequest);
        mDeclineMentorshipReqButton = (Button) findViewById(R.id.cancelRequest);
        CURRENT_MENTORSHIP = "not_mentorship";

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                mentorId = (String) extras.get("mentorid");
            }
        }

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(mentorId);
        Query mQueryMF = dbRef.child("Rating");
        mQueryMF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total = 0;
                int count = 0;
                float average = 0;
                int rating;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    rating = ds.child("rating").getValue(Integer.class);
                    total = total + rating;
                    count = count + 1;
                    average = total / count;
                    final DatabaseReference newRef = dbRef;
                    newRef.child("AverageRating").setValue(average);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });



        mRatingBar = findViewById(R.id.RatingBar);
        mHome = findViewById(R.id.home);
        mGoalsProfile = findViewById(R.id.goalsProfile);
        mMeetingProfile = findViewById(R.id.meetingProfile);
        mMessageProfile = findViewById(R.id.messageProfile);
        mIsSeen = findViewById(R.id.isSeen);

        Messaging = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(mentorId).child(menteeId);
        Messaging.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String isRead = snapshot.child("isRead").getValue().toString();
                if (isRead.equals("true")) {
                    mIsSeen.setBackgroundColor(Color.GREEN);                }
                if(isRead.equals("false"))     {
                    mIsSeen.setBackgroundColor(Color.RED);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Mentor_Profile_Details_Activity.this);


                        builder.setTitle("Messages");

                        builder.setMessage("Have you read messages?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                RootRef = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(menteeId).child(mentorId);
                                HashMap messaging = new HashMap();
                                messaging.put("isRead", true);

                                RootRef.updateChildren(messaging).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        Intent i = new Intent(Mentor_Profile_Details_Activity.this, MenteeMainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            }

                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RootRef = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(menteeId).child(mentorId);
                                HashMap messaging = new HashMap();
                                messaging.put("isRead", false);

                                RootRef.updateChildren(messaging).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        Intent i = new Intent(Mentor_Profile_Details_Activity.this, MenteeMainActivity.class);
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


        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        MentorshipRequestRef = FirebaseDatabase.getInstance().getReference().child("MentorshipRequests");
        MentorshipRef = FirebaseDatabase.getInstance().getReference().child("Mentorship");
        RootRef = FirebaseDatabase.getInstance().getReference().child("users");

        UsersRef.child(mentorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String bio = dataSnapshot.child("bio").getValue().toString();
                    String jobTitle = dataSnapshot.child("jobType").getValue().toString();
                    String industry = dataSnapshot.child("industry").getValue().toString();
                    String skill1 = dataSnapshot.child("skill1").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String company = dataSnapshot.child("company").getValue().toString();
                    String photo = dataSnapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(mProfile);
                    String ratingBar = dataSnapshot.child("AverageRating").getValue().toString();
                    mRatingBar.setRating(Float.parseFloat(ratingBar));


                    mName.setText(name);
                    mBio.setText(bio);
                    mJobTitle.setText(jobTitle);
                    mIndustry.setText(industry);
                    mSkills1.setText(skill1);
                    mLocation.setText(location);
                    mCompany.setText(company);

                    MentorshipButtons();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mGoalsProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Mentor_Profile_Details_Activity.this, Goals_Add_Activity.class);
                intent.putExtra("mentorid", mentorId);
                intent.putExtra("name", mName.getText());
                startActivity(intent);

            }
        });

        mMeetingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Mentor_Profile_Details_Activity.this, MeetingRequest.class);
                intent.putExtra("mentorid", mentorId);
                intent.putExtra("name", mName.getText());
                startActivity(intent);

            }
        });

        mMessageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mentor_Profile_Details_Activity.this, Message_Mentor.class);
                intent.putExtra("mentorid", mentorId);
                intent.putExtra("name",  mName.getText());
                startActivity(intent);
            }
        });


        mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
        mDeclineMentorshipReqButton.setEnabled(false);

        if(!mentorId.equals(menteeId))
        {
            mSendMentorshipReqButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mSendMentorshipReqButton.setEnabled(false);

                    if(CURRENT_MENTORSHIP.equals("not_mentorship"))
                    {
                        SendMentorshipRequest();
                    }
                    if(CURRENT_MENTORSHIP.equals("request_sent"))
                    {
                        CancelMentorshipRequest();
                    }
                    if (CURRENT_MENTORSHIP.equals("request_received"))
                    {
                        AcceptMentorshipRequest();
                    }
                    if (CURRENT_MENTORSHIP.equals("mentorship"))
                    {
                        UnMentorshipRequest();
                    }
                }
            });
        }
        else
        {
            mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
            mSendMentorshipReqButton.setVisibility(View.INVISIBLE);
        }
    }

    private void UnMentorshipRequest() {

        MentorshipRef.child(menteeId).child(mentorId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            MentorshipRef.child(mentorId).child(menteeId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                mSendMentorshipReqButton.setEnabled(true);
                                                CURRENT_MENTORSHIP = "not_mentorship";
                                                mSendMentorshipReqButton.setText("Send Mentorship Request");

                                                mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                                mDeclineMentorshipReqButton.setEnabled(false);
                                                mRatingBar.setIsIndicator(false);
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

        MentorshipRef.child(menteeId).child(mentorId).child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            MentorshipRef.child(mentorId).child(menteeId).child("date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                MentorshipRequestRef.child(menteeId).child(mentorId)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                                        {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if(task.isSuccessful())
                                                                {
                                                                    MentorshipRequestRef.child(mentorId).child(menteeId).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>()
                                                                            {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task)
                                                                                {
                                                                                    if(task.isSuccessful())
                                                                                    {
                                                                                        mSendMentorshipReqButton.setEnabled(true);
                                                                                        CURRENT_MENTORSHIP = "mentorship";
                                                                                        mSendMentorshipReqButton.setText("End Mentorship");

                                                                                        mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                                                                        mDeclineMentorshipReqButton.setEnabled(false);

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



    private void CancelMentorshipRequest() {

        MentorshipRequestRef.child(menteeId).child(mentorId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            MentorshipRequestRef.child(mentorId).child(menteeId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                mSendMentorshipReqButton.setEnabled(true);
                                                CURRENT_MENTORSHIP = "not_mentorship";
                                                mSendMentorshipReqButton.setText("Send Mentorship Request");

                                                mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                                mDeclineMentorshipReqButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void MentorshipButtons()
    {
        MentorshipRequestRef.child(menteeId)
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.hasChild(mentorId))
                        {
                            String request_type = dataSnapshot.child(mentorId).child("request_type").getValue().toString();

                            if(request_type.equals("sent"))
                            {
                                CURRENT_MENTORSHIP = "request_sent";
                                mSendMentorshipReqButton.setText("Cancel Mentorship request");

                                mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                mDeclineMentorshipReqButton.setEnabled(false);
                            }
                            else if (request_type.equals("received"))
                            {
                                CURRENT_MENTORSHIP = "request_received";
                                mSendMentorshipReqButton.setText("Accept Mentorship Request");

                                mDeclineMentorshipReqButton.setVisibility(View.VISIBLE);
                                mDeclineMentorshipReqButton.setEnabled(true);

                                mDeclineMentorshipReqButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        CancelMentorshipRequest();
                                    }
                                });
                            }
                        }
                        else
                        {
                            MentorshipRef.child(menteeId)
                                    .addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            if(dataSnapshot.hasChild(mentorId))
                                            {
                                                CURRENT_MENTORSHIP = "mentorship";
                                                mSendMentorshipReqButton.setText("Cancel Mentorship");

                                                mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                                mDeclineMentorshipReqButton.setEnabled(false);

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

    private void SendMentorshipRequest() {
        MentorshipRequestRef.child(menteeId).child(mentorId)
                .child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            MentorshipRequestRef.child(mentorId).child(menteeId).child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                mSendMentorshipReqButton.setEnabled(true);
                                                CURRENT_MENTORSHIP = "request_sent";
                                                mSendMentorshipReqButton.setText("Cancel Mentorship Request");

                                                mDeclineMentorshipReqButton.setVisibility(View.INVISIBLE);
                                                mDeclineMentorshipReqButton.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
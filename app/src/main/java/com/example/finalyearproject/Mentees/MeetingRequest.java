package com.example.finalyearproject.Mentees;

import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
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
import java.util.HashMap;
import java.util.Map;

public class MeetingRequest extends AppCompatActivity {

    private EditText mTitle, mDescription;
    private TextView mMentor, mMentee;
    private Spinner mLocation;
    private Button mRequest, mCancel;
    private DatabaseReference MentorRef, MenteeRef, RootRef;
    private String date, menteeID, mentorID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        menteeID = mAuth.getCurrentUser().getUid();

        mTitle = findViewById(R.id.meetingTitle);
        mLocation = findViewById(R.id.meetingLocationSpinner);
        mDescription = findViewById(R.id.meetingDescription);
        mMentor = findViewById(R.id.meetingMentor);
        mMentee = findViewById(R.id.meetingMentee);
        mRequest = findViewById(R.id.meeting);
        mCancel = findViewById(R.id.cancel);

        //Gets mentors Id
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                mentorID = (String) extras.get("mentorid");
            }
        }



        MentorRef = FirebaseDatabase.getInstance().getReference().child("users");
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Gets to the mentors email
        MentorRef.child(mentorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("email").getValue().toString();
                    mMentor.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Gets mentees name
        MenteeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mMentee.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeetingRequest.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Creates the meeting request
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mTitle.getText().toString().isEmpty() && !mLocation.getSelectedItem().toString().isEmpty() && !mDescription.getText().toString().isEmpty()
                        && !mMentor.getText().toString().isEmpty()  && !mMentee.getText().toString().isEmpty()){

                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(2022, 0, 17, 7, 30);

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(2022, 12, 31, 8, 30);

                    //Sets the meeting details in calendar
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, mTitle.getText().toString())
                            .putExtra(CalendarContract.Events.DESCRIPTION, mDescription.getText().toString())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, mLocation.getSelectedItem().toString())
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                            .putExtra(Intent.EXTRA_EMAIL, mMentor.getText().toString());



                    String titles = mTitle.getText().toString();
                    String locations = mLocation.getSelectedItem().toString();
                    String descriptions = mDescription.getText().toString();
                    String mentors = mMentor.getText().toString();
                    String mentees = mMentee.getText().toString();


                    Calendar calendarDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    date = currentDate.format(calendarDate.getTime());

                    String sender = "Meetings/" + menteeID + "/" + mentorID;
                    String receiver = "Meetings/" + mentorID + "/" + menteeID;

                    //Creates the child Meeting
                    DatabaseReference Meetings = RootRef.child("Meetings").child(menteeID).child(menteeID).child(mentorID).push();
                    String meeting_id = Meetings.getKey();
                    //Puts the data into the database
                    Map meeting = new HashMap();
                    meeting.put("meetingTitle", titles);
                    meeting.put("meetingLocation", locations);
                    meeting.put("meetingDescription", descriptions);
                    meeting.put("meetingStartTime", EXTRA_EVENT_BEGIN_TIME);
                    meeting.put("meetingEndTime", EXTRA_EVENT_END_TIME);
                    meeting.put("meetingMentor", mentors);
                    meeting.put("meetingMentee", mentees);
                    meeting.put("date", date);


                    Map meetingDetails = new HashMap();
                    meetingDetails.put(sender + "/" + meeting_id , meeting);
                    meetingDetails.put(receiver + "/" + meeting_id , meeting);


                    RootRef.updateChildren(meetingDetails).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(MeetingRequest.this, "Meeting info successfully added", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                Toast.makeText(MeetingRequest.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(MeetingRequest.this, "Please fill in form", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
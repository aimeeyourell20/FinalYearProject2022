package com.example.finalyearproject;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private EditText title, location, description, mentor, mentee;
    private TextView date;
    private Button request, cancel, calendar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dr, UsersRef, MenteeRef;
    private String receiverUserId = "";
    private String saveCurrentDate;
    private DatabaseReference RootRef;
    private String messageSenderID;
    private String messageReceiverID1;
    private String menteeReceiverID1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);

        //firebaseAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        title = findViewById(R.id.meetingTitle);
        location = findViewById(R.id.meetingLocation);
        description = findViewById(R.id.meetingDescription);
        mentor = findViewById(R.id.meetingMentor);
        mentee = findViewById(R.id.meetingMentee);
        request = findViewById(R.id.meeting);
        cancel = findViewById(R.id.cancel);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                messageReceiverID1 = (String) extras.get("mentorid");
            }
        }



        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        UsersRef.child(messageReceiverID1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("email").getValue().toString();
                    mentor.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MenteeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mentee.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeetingRequest.this, MentorMainActivity.class);
                startActivity(i);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && !description.getText().toString().isEmpty()
                        && !mentor.getText().toString().isEmpty()  && !mentee.getText().toString().isEmpty()){

                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(2022, 0, 17, 7, 30);

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(2022, 12, 31, 8, 30);



                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                            .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString())
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                            .putExtra(Intent.EXTRA_EMAIL, mentor.getText().toString());



                    String titles = title.getText().toString();
                    String locations = location.getText().toString();
                    String descriptions = description.getText().toString();
                    String mentors = mentor.getText().toString();
                    String mentees = mentee.getText().toString();


                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    String message_sender_ref = "Meetings/" + messageSenderID + "/" + messageReceiverID1;
                    String message_receiver_ref = "Meetings/" + messageReceiverID1 + "/" + messageSenderID;

//                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    //dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Meeting").child(receiverUserId).push();
                    DatabaseReference user_message_key = RootRef.child("Meetings").child(messageSenderID).child(messageSenderID).child(messageReceiverID1).push();
                    String message_push_id = user_message_key.getKey();
                    Map meeting = new HashMap();
                    meeting.put("meetingTitle", titles);
                    meeting.put("meetingLocation", locations);
                    meeting.put("meetingDescription", descriptions);
                    meeting.put("meetingStartTime", EXTRA_EVENT_BEGIN_TIME);
                    meeting.put("meetingEndTime", EXTRA_EVENT_END_TIME);
                    //meeting.put("meetingDate", date);
                    meeting.put("meetingMentor", mentors);
                    meeting.put("meetingMentee", mentees);
                    meeting.put("date", saveCurrentDate);


                    Map messageBodyDetails = new HashMap();
                    messageBodyDetails.put(message_sender_ref + "/" + message_push_id , meeting);
                    messageBodyDetails.put(message_receiver_ref + "/" + message_push_id , meeting);


                    RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
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
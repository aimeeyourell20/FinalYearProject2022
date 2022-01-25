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

public class MeetingRequest extends AppCompatActivity {

    private EditText title, location, description, mentor;
    private TextView date;
    private Button request, cancel, calendar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dr, UsersRef;
    private String receiverUserId = "";
    private String saveCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);

        firebaseAuth = FirebaseAuth.getInstance();


        title = findViewById(R.id.meetingTitle);
        location = findViewById(R.id.meetingLocation);
        description = findViewById(R.id.meetingDescription);
        mentor = findViewById(R.id.meetingMentor);
        request = findViewById(R.id.meeting);
        cancel = findViewById(R.id.cancel);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                receiverUserId = (String) extras.get("mentorid");
                //messageReceiverName = (String) extras.get("name");
            }
        }

        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        //dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        UsersRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mentor.setText(name);

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
                        && !mentor.getText().toString().isEmpty()){

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

                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Meeting").push();
                    HashMap meeting = new HashMap();
                    meeting.put("meetingTitle", titles);
                    meeting.put("meetingLocation", locations);
                    meeting.put("meetingDescription", descriptions);
                    meeting.put("meetingStartTime", EXTRA_EVENT_BEGIN_TIME);
                    meeting.put("meetingEndTime", EXTRA_EVENT_END_TIME);
                    //meeting.put("meetingDate", date);
                    meeting.put("meetingMentor", mentors);


                    dr.updateChildren(meeting).addOnCompleteListener(new OnCompleteListener() {
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
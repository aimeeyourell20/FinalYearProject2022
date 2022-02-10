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

public class Goals_Add_Activity extends AppCompatActivity {

    private EditText title, date, description;
    private TextView mentor, mentee;
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
        setContentView(R.layout.activity_goals_add);

        //firebaseAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        title = findViewById(R.id.editGoalsTitle);
        date = findViewById(R.id.editGoalsDate);
        description = findViewById(R.id.editGoalsDescription);
        mentor = findViewById(R.id.mentorsName);
        mentee = findViewById(R.id.menteesName);
        //mentee = findViewById(R.id.meetingMentee);
        request = findViewById(R.id.createGoalButton);
        cancel = findViewById(R.id.cancelGoalButton);

        MenteeRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                messageReceiverID1 = (String) extras.get("mentorid");
            }
        }

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



        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        UsersRef.child(messageReceiverID1).addValueEventListener(new ValueEventListener() {
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
                Intent i = new Intent(Goals_Add_Activity.this, MentorMainActivity.class);
                startActivity(i);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    String titles = title.getText().toString();
                    String dates = date.getText().toString();
                    String descriptions = description.getText().toString();
                    String mentors = mentor.getText().toString();
                    String mentees = mentee.getText().toString();


                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    String message_sender_ref = "Goals/" + messageSenderID + "/" + messageReceiverID1;
                    String message_receiver_ref = "Goals/" + messageReceiverID1 + "/" + messageSenderID;

//                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    //dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Meeting").child(receiverUserId).push();
                    DatabaseReference user_message_key = RootRef.child("Goals").child(messageSenderID).child(messageSenderID).child(messageReceiverID1).push();
                    String message_push_id = user_message_key.getKey();
                    Map meeting = new HashMap();
                    meeting.put("goalsTitle", titles);
                    meeting.put("goalsDescription", descriptions);
                    meeting.put("goalsDate", dates);
                    meeting.put("goalsMentor", mentors);
                    meeting.put("goalsMentee", mentees);
                    meeting.put("goalsStartDate", saveCurrentDate);


                    Map messageBodyDetails = new HashMap();
                    messageBodyDetails.put(message_sender_ref + "/" + message_push_id , meeting);
                    messageBodyDetails.put(message_receiver_ref + "/" + message_push_id , meeting);


                    RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Goals_Add_Activity.this, "Meeting info successfully added", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Goals_Add_Activity.this, Goals_Activity_Mentee.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Goals_Add_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            });

    }
}
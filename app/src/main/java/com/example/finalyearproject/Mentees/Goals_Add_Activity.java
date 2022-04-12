package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Locale;
import java.util.Map;

public class Goals_Add_Activity extends AppCompatActivity {

    private EditText mTitle, mDate, mDescription;
    private Spinner mStatus;
    private TextView mMentor, mMentee;
    private Button mRequest, mCancel;
    private DatabaseReference MentorRef, MenteeRef, RootRef;
    private String menteeID, date;
    private String mentorID = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_add);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        menteeID = mAuth.getCurrentUser().getUid();

        mTitle = findViewById(R.id.editGoalsTitle);
        mDate = findViewById(R.id.editGoalsDate);
        mDescription = findViewById(R.id.editGoalsDescription);
        mMentor = findViewById(R.id.mentorsName);
        mMentee = findViewById(R.id.menteesName);
        mRequest = findViewById(R.id.createGoalButton);
        mCancel = findViewById(R.id.cancelGoalButton);
        mStatus = findViewById(R.id.goalProgressSpinner);
        MentorRef = FirebaseDatabase.getInstance().getReference().child("users");
        MenteeRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Gets mentors ID
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                mentorID = (String) extras.get("mentorid");
            }
        }

        Calendar calendar1 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar1.set(Calendar.YEAR, i);
                calendar1.set(Calendar.MONTH, i1);
                calendar1.set(Calendar.DAY_OF_MONTH, i2);

                calendar11();
            }

            private void calendar11() {

                String format = "dd/MM/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.UK);

                mDate.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        };

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(Goals_Add_Activity.this, dateSetListener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),  calendar1.get(Calendar.DAY_OF_MONTH)).show();

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

        //Get mentors name
        MentorRef.child(mentorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    mMentor.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Goals_Add_Activity.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String titles = mTitle.getText().toString();
                    String dates = mDate.getText().toString();
                    String descriptions = mDescription.getText().toString();
                    String mentors = mMentor.getText().toString();
                    String mentees = mMentee.getText().toString();
                    String statuses = mStatus.getSelectedItem().toString();
                    String id = RootRef.push().getKey();

                    Calendar calendarDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    date = currentDate.format(calendarDate.getTime());

                    String sender = "Goals/" + menteeID + "/" + mentorID;
                    String receiver = "Goals/" + mentorID + "/" + menteeID;

                    DatabaseReference Goals = RootRef.child("Goals").child(menteeID).child(mentorID).child(id).push();
                    String key = Goals.getKey();
                    String goalsId = Goals.getKey();
                    Map meeting = new HashMap();
                    meeting.put("goalsTitle", titles);
                    meeting.put("goalsDescription", descriptions);
                    meeting.put("goalsDate", dates);
                    meeting.put("goalsMentor", mentors);
                    meeting.put("goalsMentee", mentees);
                    meeting.put("status", statuses);
                    meeting.put("goalsStartDate", date);
                    meeting.put("goalsid", key);
                    meeting.put("goalsmentorid", mentorID);
                    meeting.put("goalsmenteeid", menteeID);

                    Map goalsDetails = new HashMap();
                    goalsDetails.put(sender + "/" + goalsId , meeting);
                    goalsDetails.put(receiver + "/" + goalsId , meeting);


                    RootRef.updateChildren(goalsDetails).addOnCompleteListener(new OnCompleteListener() {
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
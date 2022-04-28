package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Goal_Edit extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private DatabaseReference dr, dr1;
    private String messageReceiverID1 = "";
    private String goal_id = "";
    private EditText mTitle, mDate, mDescription;
    private Spinner mStatus;
    private TextView mMentor, mMentee;
    private Button mRequest, mCancel;
    private static final String tag = "Goals";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        mTitle = findViewById(R.id.editGoalsTitle);
        mDate = findViewById(R.id.editGoalsDate);
        mDescription = findViewById(R.id.editGoalsDescription);
        mMentor = findViewById(R.id.mentorsName);
        mMentee = findViewById(R.id.menteesName);
        mRequest = findViewById(R.id.createGoalButton);
        mCancel = findViewById(R.id.cancelGoalButton);
        mStatus = findViewById(R.id.goalProgressSpinner);



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Goal_Edit.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });





        Intent i = getIntent();
        goal_id = i.getStringExtra("goals_id");
        messageReceiverID1 = i.getStringExtra("menteeid");
        Log.d(tag, "Hello3" + messageReceiverID1);

        dr = FirebaseDatabase.getInstance().getReference().child("Goals").child(currentUser).child(messageReceiverID1).child(goal_id);
        dr1 = FirebaseDatabase.getInstance().getReference().child("Goals").child(messageReceiverID1).child(currentUser).child(goal_id);


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

                new DatePickerDialog(Goal_Edit.this, dateSetListener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),  calendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                   // for(DataSnapshot a : uniqueKeySnapshot.getChildren()) {
                //snapshot.getKey();
                        if (snapshot.exists()) {
                            String goalTitle = snapshot.child("goalsTitle").getValue().toString();
                            String goalDescription = snapshot.child("goalsDescription").getValue().toString();
                            String goalMentee = snapshot.child("goalsMentee").getValue().toString();
                            String goalMentor = snapshot.child("goalsMentor").getValue().toString();
                            String goalDate = snapshot.child("goalsDate").getValue().toString();
                            String goalStartDate = snapshot.child("goalsStartDate").getValue().toString();
                            String goalStatus = snapshot.child("status").getValue().toString();
                            goal_id = snapshot.child("goalsid").getValue().toString();



                            mTitle.setText(goalTitle);
                            mDate.setText(goalDate);
                            mDescription.setText(goalDescription);
                            mMentor.setText(goalMentor);
                            mMentee.setText(goalMentee);
                            mStatus.setSelected(Boolean.parseBoolean(goalStatus));

                        }
                    }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {
        String mtitle = mTitle.getText().toString();
        String mdate = mDate.getText().toString();
        String mdescription = mDescription.getText().toString();
        String mmentor = mMentor.getText().toString();
        String mmentee = mMentee.getText().toString();
        String mstatus = mStatus.getSelectedItem().toString();



        UpdateGoal(mtitle, mdate, mdescription,mmentor, mmentee, goal_id, mstatus);
    }

    private void UpdateGoal(String mtitle, String mdate, String mdescription, String mmentor, String mmentee, String goal_id, String mstatus) {

                    HashMap<String, Object> GoalMap = new HashMap<>();
                    GoalMap.put("goalsTitle", mtitle);
                    GoalMap.put("goalsDate", mdate);
                    GoalMap.put("goalsDescription", mdescription);
                    GoalMap.put("goalsMentor", mmentor);
                    GoalMap.put("goalsMentee", mmentee);
                    GoalMap.put("goalsid", goal_id);
                    GoalMap.put("status", mstatus);



                    dr.updateChildren(GoalMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Goal_Edit.this, "Goal details updated successfully", Toast.LENGTH_SHORT).show();
                                SendMenteeGoalActivity();
                            }else{
                                Toast.makeText(Goal_Edit.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        dr1.updateChildren(GoalMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Goal_Edit.this, "Goal details updated successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeGoalActivity();
                }else{
                    Toast.makeText(Goal_Edit.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeGoalActivity() {
        Intent i = new Intent(Goal_Edit.this, Goals_Activity_Mentee.class);
        startActivity(i);
        finish();

    }

}

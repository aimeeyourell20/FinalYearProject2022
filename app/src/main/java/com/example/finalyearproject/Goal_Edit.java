package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Goal_Edit extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private DatabaseReference dr, dr1;
    private String messageReceiverID1 = "";
    private String goal_id = "";
    private EditText title, date, description;
    private Spinner status;
    private TextView mentor, mentee;
    private Button request, cancel;
    private static final String tag = "Goals";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_edit);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        title = findViewById(R.id.editGoalsTitle);
        date = findViewById(R.id.editGoalsDate);
        description = findViewById(R.id.editGoalsDescription);
        mentor = findViewById(R.id.mentorsName);
        mentee = findViewById(R.id.menteesName);
        //mentee = findViewById(R.id.meetingMentee);
        request = findViewById(R.id.createGoalButton);
        cancel = findViewById(R.id.cancelGoalButton);
        status = findViewById(R.id.goalProgressSpinner);








        Intent i = getIntent();
        goal_id = i.getStringExtra("goals_id");
        messageReceiverID1 = i.getStringExtra("menteeid");
        Log.d(tag, "Hello3" + messageReceiverID1);

        dr = FirebaseDatabase.getInstance().getReference().child("Goals").child(currentUser).child(messageReceiverID1).child(goal_id);
        dr1 = FirebaseDatabase.getInstance().getReference().child("Goals").child(messageReceiverID1).child(currentUser).child(goal_id);




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



                            title.setText(goalTitle);
                            date.setText(goalDate);
                            description.setText(goalDescription);
                            mentor.setText(goalMentor);
                            mentee.setText(goalMentee);
                            status.setSelected(Boolean.parseBoolean(goalStatus));

                        }
                    }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {
        String mtitle = title.getText().toString();
        String mdate = date.getText().toString();
        String mdescription = description.getText().toString();
        String mmentor = mentor.getText().toString();
        String mmentee = mentee.getText().toString();
        String mStatus = status.getSelectedItem().toString();



        UpdateGoal(mtitle, mdate, mdescription,mmentor, mmentee, goal_id, mStatus);
    }

    private void UpdateGoal(String mtitle, String mdate, String mdescription, String mmentor, String mmentee, String goal_id, String mStatus) {

                    HashMap<String, Object> GoalMap = new HashMap<>();
                    GoalMap.put("goalsTitle", mtitle);
                    GoalMap.put("goalsDate", mdate);
                    GoalMap.put("goalsDescription", mdescription);
                    GoalMap.put("goalsMentor", mmentor);
                    GoalMap.put("goalsMentee", mmentee);
                    GoalMap.put("goalsid", goal_id);
                    GoalMap.put("status", mStatus);



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

package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Add_Goal extends AppCompatActivity {

    EditText addGoalTitle, addGoalDescription, addGoalDate;
    Spinner status;
    private DatabaseReference MeetingsRef,MentorRef;
    Button createGoalButton, cancelGoalButton;
    DatabaseReference reference, users;
    private String messageSenderID, saveCurrentDate, saveCurrentTime;
    private DatabaseReference RootRef, UserRef;
    private FirebaseAuth mAuth;
    Integer number = new Random().nextInt();
    String key = Integer.toString(number);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        //reference = FirebaseDatabase.getInstance().getReference().child("Goals");
        //users = FirebaseDatabase.getInstance().ge

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        MentorRef = FirebaseDatabase.getInstance().getReference().child("Goals");

        RootRef = FirebaseDatabase.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("users");

        addGoalTitle = findViewById(R.id.editGoalsTitle);
        addGoalDescription = findViewById(R.id.editGoalsDescription);
        addGoalDate = findViewById(R.id.editGoalsDate);
        status = findViewById(R.id.statusSpinner);
        createGoalButton= findViewById(R.id.createGoalButton);
        cancelGoalButton = findViewById(R.id.cancelGoalButton);

        createGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Goals").push();
                DatabaseReference user_message_key = reference.child(messageSenderID);

                //final String users = getRef().getKey();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        snapshot.getRef().child("goalsTitle").setValue(addGoalTitle.getText().toString());
                        snapshot.getRef().child("goalsDescription").setValue(addGoalDescription.getText().toString());
                        snapshot.getRef().child("goalsDate").setValue(addGoalDate.getText().toString());
                        snapshot.getRef().child("key").setValue(key);


                        Intent i = new Intent(Add_Goal.this, Goals.class);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}
package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Edit_Goal extends AppCompatActivity {

    EditText addGoalTitle, addGoalDescription, addGoalDate;
    Spinner status;
    Button createGoalButton, cancelGoalButton;
    private DatabaseReference reference;
    private String currentUser;
    private FirebaseAuth firebaseAuth;
    private String messageSenderID;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);


//        currentUser = firebaseAuth.getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();
       // messageSenderID = mAuth.getCurrentUser().getUid();


        addGoalTitle = findViewById(R.id.editGoalsTitle1);
        addGoalDescription = findViewById(R.id.editGoalsDescription1);
        addGoalDate = findViewById(R.id.editGoalsDate1);
        status = findViewById(R.id.statusSpinner1);
        createGoalButton = findViewById(R.id.createGoalButton);
        cancelGoalButton = findViewById(R.id.cancelGoalButton);

        addGoalTitle.setText(getIntent().getStringExtra("goalsTitle"));
        addGoalDescription.setText(getIntent().getStringExtra("goalsDescription"));
        addGoalDate.setText(getIntent().getStringExtra("goalsDate"));
        status.setSelected(Boolean.parseBoolean(getIntent().getStringExtra("status")));

        String key1 = getIntent().getStringExtra("key");

        firebaseAuth = FirebaseAuth.getInstance();
        messageSenderID = firebaseAuth.getCurrentUser().getUid();


            createGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                //DatabaseReference user_message_key = dr.child(messageSenderID);
                reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Goals");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        snapshot.getRef().child("goalsTitle").setValue(addGoalTitle.getText().toString());
                        snapshot.getRef().child("goalsDescription").setValue(addGoalDescription.getText().toString());
                        snapshot.getRef().child("goalsDate").setValue(addGoalDate.getText().toString());
                        snapshot.getRef().child("key").setValue(key1);

                        Intent i = new Intent(Edit_Goal.this, Goals.class);
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



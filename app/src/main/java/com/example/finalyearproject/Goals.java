package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Goals extends AppCompatActivity {

    TextView title, subTitle, endTitle;
    TextView spinnerStatus;
    TextView goalsTitle,goalsDescription,goalsDate;
    RecyclerView recyclerView;
    ArrayList<Goals_Model> goals;
    Goals_Adapter goals_adapter;
    DatabaseReference reference;
    Button goalsButton;
    Integer number = new Random().nextInt();
    String key = Integer.toString(number);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);


        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subtitle);
        endTitle = findViewById(R.id.endTitle);
        goalsTitle = findViewById(R.id.goalTitle);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        goalsDescription =findViewById(R.id.goalDescription);
        goalsDate =findViewById(R.id.goalDate);


        goalsButton = findViewById(R.id.addGoal);

        goalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Goals.this, Add_Goal.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.goalsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goals = new ArrayList<Goals_Model>();

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Goals");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){

                        Goals_Model g = uniqueKeySnapshot.getValue(Goals_Model.class);
                        goals.add(g);

                }

                goals_adapter = new Goals_Adapter(Goals.this, goals);
                recyclerView.setAdapter(goals_adapter);
                goals_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Goals.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
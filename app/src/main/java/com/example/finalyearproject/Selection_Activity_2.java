package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selection_Activity_2 extends AppCompatActivity {

    //Contains signup and login buttons

    Button mMenteeButton, mMentorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mMenteeButton = findViewById(R.id.menteeButton);
        mMentorButton = findViewById(R.id.mentorButton);

        mMenteeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Selection_Activity_2.this, Mentee_Login_Activity.class);
                startActivity(i);
            }
        });

        mMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Selection_Activity_2.this, Mentor_Login_Activity.class);
                startActivity(i);
            }
        });
    }
}
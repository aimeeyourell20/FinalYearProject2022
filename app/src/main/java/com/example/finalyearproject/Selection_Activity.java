package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalyearproject.Mentees.Mentee_Registration_Activity;
import com.example.finalyearproject.Mentor.Mentor_Registration_Activity;

public class Selection_Activity extends AppCompatActivity {

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

                Intent i = new Intent(Selection_Activity.this, Mentee_Registration_Activity.class);
                startActivity(i);
            }
        });

        mMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Selection_Activity.this, Mentor_Registration_Activity.class);
                startActivity(i);
            }
        });
    }
}
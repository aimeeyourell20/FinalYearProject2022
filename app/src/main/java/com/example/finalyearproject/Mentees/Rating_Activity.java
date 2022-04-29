package com.example.finalyearproject.Mentees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rating_Activity extends AppCompatActivity {

    private Button mRateMentorButton;
    private String mentorId = "";
    private String menteeId;
    private DatabaseReference RootRef;
    private RatingBar mRatingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        mRateMentorButton = findViewById(R.id.rateMentor);
        mRatingBar = findViewById(R.id.RatingBar);
        mAuth = FirebaseAuth.getInstance();
        menteeId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                mentorId = (String) extras.get("mentorid");
            }
        }

        mRateMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Rating_Activity.this, MenteeMainActivity.class);
                startActivity(i);

            }
        });

        RootRef = FirebaseDatabase.getInstance().getReference().child("users");
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                RootRef.child(mentorId).child("Rating").child(menteeId).child("rating").push();
                RootRef.child(mentorId).child("Rating").child(menteeId).child("rating").setValue(v);





            }
        });

    }
}
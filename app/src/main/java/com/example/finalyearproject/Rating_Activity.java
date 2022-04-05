package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Rating_Activity extends AppCompatActivity {

    private Button mRateMentorButton;
    private String mentorId = "";
    private String menteeId;
    private DatabaseReference RootRef, RootRef1;
    private RatingBar mRatingBar;
    private Float rateValue;
    private FirebaseAuth mAuth;
    private static final String tag = "average";


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
                //messageReceiverName = (String) extras.get("name");
            }
        }

        RootRef = FirebaseDatabase.getInstance().getReference().child("users");
        RootRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(mentorId);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                RootRef.child(mentorId).child("Rating").child(menteeId).child("rating").push();
                RootRef.child(mentorId).child("Rating").child(menteeId).child("rating").setValue(v);

            }
        });


        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(mentorId);

        Query mQueryMF = dbRef.child("Rating");

        mQueryMF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total = 0;
                int count = 0;
                int average = 0;
                Float rating;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // for (DataSnapshot a : ds.getChildren()) {
                    // for (DataSnapshot b : a.getChildren()) {




                    rating = ds.child("rating").getValue(Float.class);

                    Log.d(tag, "Rating" + ds.child("rating").getValue(Integer.class));

                    total = (int) (total + rating);
                    count = count + 1;
                    average = total / count;

                    final DatabaseReference newRef = dbRef;
                    newRef.child("AverageRating").setValue(average);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }
}
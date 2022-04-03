package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MentorshipReportChartPieMentee extends AppCompatActivity {

    DatabaseReference Mentorship, TotalMentorships, TotalUsers;
    private ImageView mHome;
    private TextView mMentorship, mTotalMentorships, mTotalMentors, mTotalMentees, mTotalUsers;
    int Mentor;
    int Mentee;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private static final String tag = "Mentor";
    private static final String tag1 = "Mentee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentorshipreportchartpie2);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();


        Mentorship = FirebaseDatabase.getInstance().getReference().child("Mentorship").child(currentUser);
        TotalMentorships = FirebaseDatabase.getInstance().getReference().child("Mentorship");
        TotalUsers = FirebaseDatabase.getInstance().getReference().child("users");
        mMentorship = findViewById(R.id.mentorship);
        mTotalMentorships = findViewById(R.id.totalMentorships);
        mTotalMentors = findViewById(R.id.totalMentors);
        mTotalMentees = findViewById(R.id.totalMentees);
        mTotalUsers = findViewById(R.id.totalUsers);
        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorshipReportChartPieMentee.this, Mentee_Reports.class);
                startActivity(i);
                finish();


            }
        });


        Mentorship.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int counter = (int) snapshot.getChildrenCount();
                        String userCounter = String.valueOf(counter);

                        mMentorship.setText(userCounter);

                    }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TotalMentorships.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    int counter = (int) snapshot.getChildrenCount();
                    String userCounter = String.valueOf(counter);

                    mTotalMentorships.setText(userCounter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TotalUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("type").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Mentor":
                            ++Mentor;
                            mTotalMentors.setText(String.valueOf(Mentor));

                            break;
                        case "Mentee":
                            ++Mentee;
                            mTotalMentees.setText(String.valueOf(Mentee));

                            break;


                    }


                    int totalUsers = Mentor + Mentee;
                    mTotalUsers.setText(String.valueOf(totalUsers));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
package com.example.finalyearproject.Reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Mentor.MentorMainActivity;
import com.example.finalyearproject.R;

public class Mentor_Reports extends AppCompatActivity {

    private ImageView mentor, industry, location, skills, meetings, home, goals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_reports);

        mentor = findViewById(R.id.mentor);
        industry = findViewById(R.id.industry);
        location = findViewById(R.id.location);
        skills = findViewById(R.id.skills);
        meetings = findViewById(R.id.college);
        home = findViewById(R.id.home);
        goals = findViewById(R.id.goals);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, MentorshipReportChartPieMentor.class);
                startActivity(i);
            }
        });

        industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, IndustryMenteeReportChartBar.class);
                startActivity(i);
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, LocationReportChartMentor.class);
                startActivity(i);

            }
        });

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, SkillsMenteeReportChartBar.class);
                startActivity(i);

            }
        });

        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, MeetingsReportChartBarMentor.class);
                startActivity(i);
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, GoalsReportChartBarMentor.class);
                startActivity(i);
            }
        });

    }
}
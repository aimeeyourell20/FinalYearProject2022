package com.example.finalyearproject.LocationReports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.CompanyReportChart;
import com.example.finalyearproject.MentorMainActivity;
import com.example.finalyearproject.R;

public class Mentee_Reports extends AppCompatActivity {

    private ImageView mentor, industry, location, skills, meetings, home, rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_reports);

        mentor = findViewById(R.id.mentor);
        industry = findViewById(R.id.industry);
        location = findViewById(R.id.location);
        skills = findViewById(R.id.skills);
        meetings = findViewById(R.id.college);
        home = findViewById(R.id.home);
        rating = findViewById(R.id.rating);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, MentorshipReportMenu.class);
                startActivity(i);
            }
        });

        industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, IndustryMentorReportChartBar.class);
                startActivity(i);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, LocationReportChart.class);
                startActivity(i);

            }
        });

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, SkillsMentorReportChartBar.class);
                startActivity(i);

            }
        });

        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, MeetingsReportChartBar.class);
                startActivity(i);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Reports.this, Mentor_Rating_Board.class);
                startActivity(i);
            }
        });

    }
}
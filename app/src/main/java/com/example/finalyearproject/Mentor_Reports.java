package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Mentor_Reports extends AppCompatActivity {

    private ImageView mentor, industry, company, location, skills, college;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_reports);

        mentor = findViewById(R.id.mentor);
        industry = findViewById(R.id.industry);
        company = findViewById(R.id.company);
        location = findViewById(R.id.location);
        skills = findViewById(R.id.skills);
        college = findViewById(R.id.college);

        mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, MentorshipReportChart.class);
                startActivity(i);
            }
        });

        industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, IndustryReportChart.class);
                startActivity(i);
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, CompanyReportChart.class);
                startActivity(i);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, LocationReportChart.class);
                startActivity(i);

            }
        });

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, SkillsReportChart.class);
                startActivity(i);

            }
        });

        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Reports.this, CollegeReportChart.class);
                startActivity(i);
            }
        });

    }
}
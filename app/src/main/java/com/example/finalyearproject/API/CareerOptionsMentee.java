package com.example.finalyearproject.API;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;

public class CareerOptionsMentee extends AppCompatActivity {

    private ImageView vacancies, company, home, college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_options_mentee);

        vacancies = findViewById(R.id.viewVacancies);
        company = findViewById(R.id.viewCompany);
        college = findViewById(R.id.viewCollege);
        home = findViewById(R.id.home);

        vacancies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CareerOptionsMentee.this, CareerOptions.class);
                startActivity(i);
                finish();
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CareerOptionsMentee.this, CompanyInformation.class);
                startActivity(i);
                finish();
            }
        });

        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CareerOptionsMentee.this, CollegeOptions.class);
                startActivity(i);
                finish();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CareerOptionsMentee.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
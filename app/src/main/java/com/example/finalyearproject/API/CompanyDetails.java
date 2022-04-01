package com.example.finalyearproject.API;


import static com.example.finalyearproject.API.CompanyInformation.EXTRA_DESCRIPTION;
import static com.example.finalyearproject.API.CompanyInformation.EXTRA_EMPLOYEES;
import static com.example.finalyearproject.API.CompanyInformation.EXTRA_LOCATION;
import static com.example.finalyearproject.API.CompanyInformation.EXTRA_RATING;
import static com.example.finalyearproject.API.CompanyInformation.EXTRA_TITLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;

public class CompanyDetails extends AppCompatActivity {

    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        Intent companyDetails = getIntent();
        String description = companyDetails.getStringExtra(EXTRA_DESCRIPTION);
        String employees = companyDetails.getStringExtra(EXTRA_EMPLOYEES);
        String location = companyDetails.getStringExtra(EXTRA_LOCATION);
        String rating = companyDetails.getStringExtra(EXTRA_RATING);
        String title = companyDetails.getStringExtra(EXTRA_TITLE);



        TextView mdescription = findViewById(R.id.companyDescription1);
        TextView memployees = findViewById(R.id.companyEmployee1);
        TextView mlocation = findViewById(R.id.companyLocation1);
        TextView mrating = findViewById(R.id.companyRating1);
        TextView mtitle = findViewById(R.id.companyName1);

        mdescription.setText(description);
        memployees.setText(employees);
        mlocation.setText(location);
        mrating.setText(rating);
        mtitle.setText(title);

        mHome = findViewById(R.id.home);


        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyDetails.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}
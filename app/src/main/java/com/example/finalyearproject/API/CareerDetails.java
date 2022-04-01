package com.example.finalyearproject.API;

import static com.example.finalyearproject.API.CareerOptions.EXTRA_COMPANY_NAME;
import static com.example.finalyearproject.API.CareerOptions.EXTRA_JOB_LOCATION;
import static com.example.finalyearproject.API.CareerOptions.EXTRA_JOB_TITLE;
import static com.example.finalyearproject.API.CareerOptions.EXTRA_URL;
import static com.example.finalyearproject.API.CareerOptions.EXTRA_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;

public class CareerDetails extends AppCompatActivity{

    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_details);
        
        Intent jobDetails = getIntent();
        String jobTitle = jobDetails.getStringExtra(EXTRA_JOB_TITLE);
        String companyName = jobDetails.getStringExtra(EXTRA_COMPANY_NAME);
        String jobLocation = jobDetails.getStringExtra(EXTRA_JOB_LOCATION);
        String id = jobDetails.getStringExtra(EXTRA_ID);


        TextView mUrl = findViewById(R.id.text_view_url);
        TextView mJobTitle = findViewById(R.id.text_view_creator);
        TextView mCompanyName = findViewById(R.id.text_view_company);
        TextView mJobLocation = findViewById(R.id.text_view_downloads);

        mUrl.setText(id);
        Linkify.addLinks(mUrl, Linkify.ALL);
        mJobTitle.setText(jobTitle);
        mCompanyName.setText(companyName);
        mJobLocation.setText(jobLocation);

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CareerDetails.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}
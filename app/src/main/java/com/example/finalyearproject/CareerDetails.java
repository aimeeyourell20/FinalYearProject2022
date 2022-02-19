package com.example.finalyearproject;

import static com.example.finalyearproject.CareerOptions.EXTRA_COMPANY_NAME;
import static com.example.finalyearproject.CareerOptions.EXTRA_JOB_DESCRIPTION;
import static com.example.finalyearproject.CareerOptions.EXTRA_JOB_LOCATION;
import static com.example.finalyearproject.CareerOptions.EXTRA_JOB_TITLE;
import static com.example.finalyearproject.CareerOptions.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CareerDetails extends AppCompatActivity {

    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_details);
        
        Intent jobDetails = getIntent();
        String url = jobDetails.getStringExtra(EXTRA_URL);
        String jobTitle = jobDetails.getStringExtra(EXTRA_JOB_TITLE);
        String companyName = jobDetails.getStringExtra(EXTRA_COMPANY_NAME);
        String jobLocation = jobDetails.getStringExtra(EXTRA_JOB_LOCATION);
        String jobDescription = jobDetails.getStringExtra(EXTRA_JOB_DESCRIPTION);

        TextView mUrl = findViewById(R.id.text_view_url);
        TextView mJobTitle = findViewById(R.id.text_view_creator);
        TextView mCompanyName = findViewById(R.id.text_view_company);
        TextView mJobLocation = findViewById(R.id.text_view_downloads);
        TextView mJobDescription = findViewById(R.id.text_view_description);

        mUrl.setText(url);
        Linkify.addLinks(mUrl, Linkify.ALL);
        mJobTitle.setText(jobTitle);
        mCompanyName.setText(companyName);
        mJobLocation.setText(jobLocation);
        mJobDescription.setText(jobDescription);

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
package com.example.finalyearproject.API;


import static com.example.finalyearproject.API.CollegeOptions.EXTRA_CITY;
import static com.example.finalyearproject.API.CollegeOptions.EXTRA_COUNTRY;
import static com.example.finalyearproject.API.CollegeOptions.EXTRA_RANKING;
import static com.example.finalyearproject.API.CollegeOptions.EXTRA_REGION;
import static com.example.finalyearproject.API.CollegeOptions.EXTRA_SCORE;
import static com.example.finalyearproject.API.CollegeOptions.EXTRA_TITLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;

public class CollegeDetails extends AppCompatActivity {

    private ImageView mHome;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_details);

        Intent companyDetails = getIntent();
        String country = companyDetails.getStringExtra(EXTRA_COUNTRY);
        String city = companyDetails.getStringExtra(EXTRA_CITY);
        String title = companyDetails.getStringExtra(EXTRA_TITLE);
        String score = companyDetails.getStringExtra(EXTRA_SCORE);
        String ranking = companyDetails.getStringExtra(EXTRA_RANKING);
        String region = companyDetails.getStringExtra(EXTRA_REGION);



        TextView mcountry = findViewById(R.id.collegeCountry1);
        TextView mcity = findViewById(R.id.collegeCity1);
        TextView mtitle = findViewById(R.id.collegeName1);
        TextView mscore = findViewById(R.id.collegeScore1);
        TextView mranking = findViewById(R.id.collegeRanking1);
        TextView mregion = findViewById(R.id.collegeRegion1);

        mcountry.setText(country);
        mcity.setText(city);
        mtitle.setText(title);
        mscore.setText(score);
        mranking.setText(ranking);
        mregion.setText(region);

        mHome = findViewById(R.id.home);


        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CollegeDetails.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}


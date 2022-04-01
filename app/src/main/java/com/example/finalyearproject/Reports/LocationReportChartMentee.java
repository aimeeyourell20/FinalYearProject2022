package com.example.finalyearproject.Reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.R;

public class LocationReportChartMentee extends AppCompatActivity {

    private ImageView home, asia, australia, southAmerica, middleEast, europe, northAmerica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_report_chart_mentee);

        asia = findViewById(R.id.asiaChart);
        australia = findViewById(R.id.australiaChart);
        middleEast = findViewById(R.id.middleEastChart);
        northAmerica = findViewById(R.id.northAmericaChart);
        southAmerica = findViewById(R.id.southAmericaChart);
        europe = findViewById(R.id.europeChart);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, MenteeMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        asia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, AsiaReportChartBar.class);
                startActivity(i);

            }
        });

        australia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, AustraliaReportChartPie.class);
                startActivity(i);
            }
        });

        middleEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, MiddleEastReportChartBar.class);
                startActivity(i);
            }
        });


        northAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, NorthAmericaReportChartBar.class);
                startActivity(i);
            }
        });

        southAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, SouthAmericaReportChartBar.class);
                startActivity(i);
            }
        });

        europe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentee.this, EuropeReportChartBar.class);
                startActivity(i);
            }
        });

    }
}


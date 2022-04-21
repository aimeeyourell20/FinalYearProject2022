package com.example.finalyearproject.Reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Mentor.MentorMainActivity;
import com.example.finalyearproject.R;

public class LocationReportChartMentor extends AppCompatActivity {

    private ImageView home, asia, australia, southAmerica, middleEast, europe, northAmerica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_report_chart);

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
                Intent i = new Intent(LocationReportChartMentor.this, Mentor_Reports.class);
                startActivity(i);
                finish();
            }
        });

        asia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, AsiaReportChartBarMentor.class);
                startActivity(i);

            }
        });

        australia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, AustraliaReportChartPieMentor.class);
                startActivity(i);
            }
        });

        middleEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, MiddleEastReportChartBarMentor.class);
                startActivity(i);
            }
        });


        northAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, NorthAmericanReportChartPie.class);
                startActivity(i);
            }
        });

        southAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, SouthAmericaReportChartPie.class);
                startActivity(i);
            }
        });

        europe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationReportChartMentor.this, EuropeReportChartBarMentor.class);
                startActivity(i);
            }
        });

    }
}


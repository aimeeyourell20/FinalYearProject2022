package com.example.finalyearproject.LocationReports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.MentorMainActivity;
import com.example.finalyearproject.R;

public class IndustryReportMenu extends AppCompatActivity {

    private ImageView bar, pie, home, barMentee, pieMentee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_report_menu);

        bar = findViewById(R.id.typeBarchart);
        pie = findViewById(R.id.typePiechart);
        barMentee = findViewById(R.id.menteetypeBarchart);
        pieMentee = findViewById(R.id.menteetypePiechart);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryReportMenu.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryReportMenu.this, IndustryMentorReportChartBar.class);
                startActivity(i);

            }
        });

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryReportMenu.this, IndustryMentorReportChartPie.class);
                startActivity(i);
            }
        });

        barMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryReportMenu.this, IndustryMenteeReportChartBar.class);
                startActivity(i);

            }
        });

        pieMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryReportMenu.this, IndustryMenteeReportChartPie.class);
                startActivity(i);
            }
        });
    }
}
package com.example.finalyearproject.LocationReports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.MentorMainActivity;
import com.example.finalyearproject.R;

public class MentorshipReportMenu extends AppCompatActivity {

    private ImageView bar, pie, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorship_report_menu);

        bar = findViewById(R.id.typeBarchart);
        pie = findViewById(R.id.typePiechart);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorshipReportMenu.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorshipReportMenu.this, MentorshipReportChartBar.class);
                startActivity(i);

            }
        });

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MentorshipReportMenu.this, MentorshipReportChartPie.class);
                startActivity(i);
            }
        });
    }
}
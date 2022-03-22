package com.example.finalyearproject.LocationReports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.MentorMainActivity;
import com.example.finalyearproject.R;

public class AsiaReportMenu extends AppCompatActivity {

    private ImageView bar, pie, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_report_menu);

        bar = findViewById(R.id.typeBarchart);
        pie = findViewById(R.id.typePiechart);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AsiaReportMenu.this, MentorMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AsiaReportMenu.this, AsiaReportChartBar.class);
                startActivity(i);

            }
        });

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AsiaReportMenu.this, AsiaReportChartPie.class);
                startActivity(i);
            }
        });

    }
}
package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndustryReportChartRadar extends AppCompatActivity {

    com.github.mikephil.charting.charts.RadarChart RadarChart;
    RadarData radarData;
    RadarDataSet radarDataSet;
    ArrayList radarEntries;
    DatabaseReference databaseReference;
    int Aerospace;
    int Agriculture;
    int Business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_report_chart_radar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        RadarChart = findViewById(R.id.RadarChart);
        getEntries();
        radarDataSet = new RadarDataSet(radarEntries, "Industry");
        radarData = new RadarData(radarDataSet);
        RadarChart.setData(radarData);
        radarDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        radarDataSet.setValueTextColor(Color.BLACK);
        radarDataSet.setValueTextSize(18f);
    }
    private void getEntries() {

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            switch (dataSnapshot.child("industry").getValue(String.class)) {
                                                                case "Aerospace":
                                                                    ++Aerospace;
                                                                    break;
                                                                case "Agriculture/Animals":
                                                                    ++Agriculture;
                                                                    break;
                                                                case "Business":
                                                                    ++Business;
                                                                    break;

                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
        radarEntries = new ArrayList<>();
        radarEntries.add(new RadarEntry(0, Aerospace));
        radarEntries.add(new RadarEntry(1, Agriculture));
        radarEntries.add(new RadarEntry(2, Business));
        //radarEntries.add(new RadarEntry(2, 0.52f));
        //radarEntries.add(new RadarEntry(3, 0.29f));
        //radarEntries.add(new RadarEntry(4, 0.62f));
    }
}



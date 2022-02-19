package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkillsReportChart extends AppCompatActivity {
    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    int ActiveListening;
    int Adaptability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_report_chart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.chart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("skill1").getValue(String.class)){
                        case "Active Listening":
                            ++ActiveListening;
                            break;
                        case "Adaptability":
                            ++Adaptability;
                            break;
                    }

                    barEntries.add(new BarEntry(0, 1));
                    barEntries.add(new BarEntry(1, 2));
                    barEntries.add(new BarEntry(2, 4));
                    barEntries.add(new BarEntry(3, 6));
                    barEntries.add(new BarEntry(4, 5));
                    barEntries.add(new BarEntry(5, 7));
;

                    ArrayList<String> labels = new ArrayList<String> ();

                    labels.add( "JAN");
                    labels.add( "FEB");
                    labels.add( "MAR");
                    labels.add( "APR");
                    labels.add( "MAY");
                    labels.add( "JUN");







                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

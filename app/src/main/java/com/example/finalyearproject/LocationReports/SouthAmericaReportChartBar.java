package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SouthAmericaReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;

    int Argentina;
    int Brazil;
    int Chile;
    int Columbia;
    int Peru;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_america_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("location").getValue(String.class)) {
                        case "Argentina":
                            ++Argentina;
                            break;
                        case "Brazil":
                            ++Brazil;
                            break;
                        case "Chile":
                            ++Chile;
                            break;
                        case "Columbia":
                            ++Columbia;
                            break;
                        case "Peru":
                            ++Peru;
                            break;

                    }

                    barEntries.add(new BarEntry(0, Argentina, "Argentina"));
                    barEntries.add(new BarEntry(1, Brazil, "Brazil"));
                    barEntries.add(new BarEntry(2, Chile, "Chile"));
                    barEntries.add(new BarEntry(3, Columbia, "Columbia"));
                    barEntries.add(new BarEntry(4, Peru, "Peru"));

                    BarDataSet bardataset = new BarDataSet(barEntries, "South America");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Argentina");
                    labels.add("Brazil");
                    labels.add("Chile");
                    labels.add("Columbia");
                    labels.add("Peru");


                    //BarData data = new BarData(labels, bardataset);
                    mBarChart.invalidate();//Refreshs
                    XAxis xAxis = mBarChart.getXAxis();
                    xAxis.setLabelCount(labels.size());
                    xAxis.setCenterAxisLabels(false);
                    xAxis.setGranularity(1f);
                    mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    BarData theData = new BarData(bardataset);
                    mBarChart.setFitBars(true);
                    mBarChart.setTouchEnabled(false);
                    mBarChart.setPinchZoom(false);
                    mBarChart.setDoubleTapToZoomEnabled(false);
                    theData.setBarWidth(0.5f);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    bardataset.setBarBorderWidth(1);
                    mBarChart.animateY(5000);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
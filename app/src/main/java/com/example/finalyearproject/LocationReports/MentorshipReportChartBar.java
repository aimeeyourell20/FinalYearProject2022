package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.finalyearproject.R;
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

public class MentorshipReportChartBar extends AppCompatActivity {
    private BarChart mBarChart;
    DatabaseReference databaseReference;
    int Mentor;
    int Mentee;
    private static final String tag = "Mentor";
    private static final String tag1 = "Mentee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorship_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("type").getValue(String.class)) {
                        case "Mentor":
                            ++Mentor;
                            Log.d(tag, "Mentor" + Mentor);
                            break;
                        case "Mentee":
                            ++Mentee;
                            Log.d(tag1, "Mentee" + Mentee);
                            break;

                    }
                    int totalUsers = Mentor + Mentee;
                    barEntries.add(new BarEntry(0, Mentor, "Mentor"));
                    barEntries.add(new BarEntry(1, Mentee, "Mentee"));
                    barEntries.add(new BarEntry(2, totalUsers));

                    BarDataSet bardataset = new BarDataSet(barEntries, "Cells");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Mentor");
                    labels.add("Mentee");
                    labels.add("Total users");



                    //BarData data = new BarData(labels, bardataset);
                    mBarChart.invalidate();//Refreshs
                    XAxis xAxis = mBarChart.getXAxis();
                    xAxis.setGranularity(1f);
                    mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    BarData theData = new BarData(bardataset);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    mBarChart.animateY(5000);
                    bardataset.setBarBorderWidth(1);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
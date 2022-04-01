package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MiddleEastReportChartBarMentor extends AppCompatActivity {
    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private ImageView mHome;
    int Egypt;
    int Israel;
    int Jordan;
    int Morocco;
    int Pakistan;
    int TheUnitedArabEmirates;
    int Tunisia;
    int Turkey;
    int Yemen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_east_report_chart_bar);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MiddleEastReportChartBarMentor.this, LocationReportChartMentor.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("location").getValue(String.class)) {
                        case "Egypt":
                            ++Egypt;
                            break;
                        case "Israel":
                            ++Israel;
                            break;
                        case "Jordan":
                            ++Jordan;
                            break;
                        case "Morocco":
                            ++Morocco;
                            break;
                        case "Pakistan":
                            ++Pakistan;
                            break;
                        case "The United Arab Emirates":
                            ++TheUnitedArabEmirates;
                            break;
                        case "Tunisia":
                            ++Tunisia;
                            break;
                        case "Turkey":
                            ++Turkey;
                            break;
                        case "Yemen":
                            ++Yemen;
                            break;

                    }

                    barEntries.add(new BarEntry(0, Egypt, "Egypt"));
                    barEntries.add(new BarEntry(1, Israel, "Israel"));
                    barEntries.add(new BarEntry(2, Jordan, "Jordan"));
                    barEntries.add(new BarEntry(3, Morocco, "Morocco"));
                    barEntries.add(new BarEntry(4, Pakistan, "Pakistan"));
                    barEntries.add(new BarEntry(5, TheUnitedArabEmirates, "The United Arab Emirates"));
                    barEntries.add(new BarEntry(6, Tunisia, "Tunisia"));
                    barEntries.add(new BarEntry(7, Turkey, "Turkey"));
                    barEntries.add(new BarEntry(8, Yemen, "Yemen"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "Middle East");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Egypt");
                    labels.add("Israel");
                    labels.add("Jordan");
                    labels.add("Morocco");
                    labels.add("Pakistan");
                    labels.add("The United Arab Emirates");
                    labels.add("Tunisia");
                    labels.add("Turkey");
                    labels.add("Yemen");

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


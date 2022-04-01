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

public class AsiaReportChartBarMentor extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    private ImageView mHome;
    DatabaseReference databaseReference;
    int China;
    int India;
    int Indonesia;
    int Japan;
    int Malaysia;
    int Philippines;
    int Singapore;
    int Thailand;
    int Vietnam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        
        mHome = findViewById(R.id.home);
        
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AsiaReportChartBarMentor.this, LocationReportChartMentor.class);
                startActivity(i);
                finish();
                
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("location").getValue(String.class)) {
                        case "China":
                            ++China;
                            break;
                        case "India":
                            ++India;
                            break;
                        case "Indonesia":
                            ++Indonesia;
                            break;
                        case "Japan":
                            ++Japan;
                            break;
                        case "Malaysia":
                            ++Malaysia;
                            break;
                        case "Philippines":
                            ++Philippines;
                            break;
                        case "Singapore":
                            ++Singapore;
                            break;
                        case "Thailand":
                            ++Thailand;
                            break;
                        case "Vietnam":
                            ++Vietnam;
                            break;

                    }

                    barEntries.add(new BarEntry(0, China, "China"));
                    barEntries.add(new BarEntry(1, India, "India"));
                    barEntries.add(new BarEntry(2, Indonesia, "Indonesia"));
                    barEntries.add(new BarEntry(3, Japan, "Japan"));
                    barEntries.add(new BarEntry(4, Malaysia, "Malaysia"));
                    barEntries.add(new BarEntry(5, Philippines, "Philippines"));
                    barEntries.add(new BarEntry(6, Singapore, "Singapore"));
                    barEntries.add(new BarEntry(7, Thailand, "Thailand"));
                    barEntries.add(new BarEntry(8, Vietnam, "Vietnam"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "Asia");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("China");
                    labels.add("India");
                    labels.add("Indonesia");
                    labels.add("Japan");
                    labels.add("Malaysia");
                    labels.add("Philippines");
                    labels.add("Singapore");
                    labels.add("Thailand");
                    labels.add("Vietnam");

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
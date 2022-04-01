package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Location_Fragments.Australia_Fragment;
import com.example.finalyearproject.Location_Fragments.Canada_Fragment;
import com.example.finalyearproject.Location_Fragments.Fiji_Fragment;
import com.example.finalyearproject.Location_Fragments.Greenland_Fragment;
import com.example.finalyearproject.Location_Fragments.Mexico_Fragment;
import com.example.finalyearproject.Location_Fragments.NewZealand_Fragment;
import com.example.finalyearproject.Location_Fragments.PapauNewGuinea_Fragment;
import com.example.finalyearproject.Location_Fragments.Tonga_Fragment;
import com.example.finalyearproject.Location_Fragments.United_States_Fragment;
import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NorthAmericaReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private ImageView mHome;
    int Canada;
    int Greenland;
    int Mexico;
    int UnitedStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north_america_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NorthAmericaReportChartBar.this, LocationReportChartMentee.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("location").getValue(String.class)) {
                        case "Canada":
                            ++Canada;
                            break;
                        case "Greenland":
                            ++Greenland;
                            break;
                        case "Mexico":
                            ++Mexico;
                            break;
                        case "United States":
                            ++UnitedStates;
                            break;

                    }

                    barEntries.add(new BarEntry(0, Canada, "Canada"));
                    barEntries.add(new BarEntry(1, Greenland, "Greenland"));
                    barEntries.add(new BarEntry(2, Mexico, "Mexico"));
                    barEntries.add(new BarEntry(3, UnitedStates, "United States"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "North America");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Canada");
                    labels.add("Greenland");
                    labels.add("Mexico");
                    labels.add("United States");


                    //BarData data = new BarData(labels, bardataset);
                    mBarChart.invalidate();//Refreshs
                    XAxis xAxis = mBarChart.getXAxis();
                    xAxis.setLabelCount(labels.size());
                    xAxis.setCenterAxisLabels(false);
                    xAxis.setGranularity(1f);
                    mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    BarData theData = new BarData(bardataset);
                    mBarChart.setFitBars(true);
                    mBarChart.setTouchEnabled(true);
                    mBarChart.setPinchZoom(false);
                    mBarChart.setDoubleTapToZoomEnabled(false);
                    theData.setBarWidth(0.5f);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    bardataset.setBarBorderWidth(1);
                    mBarChart.animateY(5000);
                    
                    mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            float x = e.getX();

                            if (x == 0) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Canada_Fragment fragment = new Canada_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Greenland_Fragment fragment = new Greenland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Mexico_Fragment fragment = new Mexico_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                United_States_Fragment fragment = new United_States_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
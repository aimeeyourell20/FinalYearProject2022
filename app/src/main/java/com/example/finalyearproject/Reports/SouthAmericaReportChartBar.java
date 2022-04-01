package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Industry_Fragments.Aerospace_Fragment;
import com.example.finalyearproject.Industry_Fragments.AgricultureAnimals_Fragment;
import com.example.finalyearproject.Industry_Fragments.Business_Fragment;
import com.example.finalyearproject.Industry_Fragments.ComputerTechnology_Fragment;
import com.example.finalyearproject.Industry_Fragments.Construction_Fragment;
import com.example.finalyearproject.Industry_Fragments.Education_Fragment;
import com.example.finalyearproject.Industry_Fragments.Entertainment_Fragment;
import com.example.finalyearproject.Industry_Fragments.Fashion_Fragment;
import com.example.finalyearproject.Industry_Fragments.FoodBeverage_Fragment;
import com.example.finalyearproject.Industry_Fragments.Healthcare_Fragment;
import com.example.finalyearproject.Industry_Fragments.Hospitality_Fragment;
import com.example.finalyearproject.Industry_Fragments.Media_Fragment;
import com.example.finalyearproject.Industry_Fragments.STEM_Fragment;
import com.example.finalyearproject.Industry_Fragments.Telecommunication_Fragment;
import com.example.finalyearproject.Location_Fragments.Argentina_Fragment;
import com.example.finalyearproject.Location_Fragments.Brazil_Fragment;
import com.example.finalyearproject.Location_Fragments.Chile_Fragment;
import com.example.finalyearproject.Location_Fragments.Columbia_Fragment;
import com.example.finalyearproject.Location_Fragments.Peru_Fragment;
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

public class SouthAmericaReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    private ImageView mHome;
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

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SouthAmericaReportChartBar.this, LocationReportChartMentee.class);
                startActivity(i);
                finish();

            }
        });

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
                                Argentina_Fragment fragment = new Argentina_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Brazil_Fragment fragment = new Brazil_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Chile_Fragment fragment = new Chile_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Columbia_Fragment fragment = new Columbia_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 4) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Peru_Fragment fragment = new Peru_Fragment();
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
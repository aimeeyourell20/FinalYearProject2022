package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Location_Fragments.China_Fragment;
import com.example.finalyearproject.Location_Fragments.India_Fragment;
import com.example.finalyearproject.Location_Fragments.Indonesia_Fragment;
import com.example.finalyearproject.Location_Fragments.Japan_Fragment;
import com.example.finalyearproject.Location_Fragments.Malaysia_Fragment;
import com.example.finalyearproject.Location_Fragments.Philippines_Fragment;
import com.example.finalyearproject.Location_Fragments.Singapore_Fragment;
import com.example.finalyearproject.Location_Fragments.Thailand_Fragment;
import com.example.finalyearproject.Location_Fragments.Vietnam_Fragment;
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

public class RatingReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    private ImageView mHome;
    DatabaseReference databaseReference;
    int Zero;
    int One;
    int Two;
    int Three;
    int Four;
    int Five;
    private static final String tag = "average";



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
                Intent i = new Intent(RatingReportChartBar.this, Mentee_Reports.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int averageRating = dataSnapshot.child("AverageRating").getValue(Integer.class);
                    Log.d(tag, "Rating" + averageRating);
                    if(averageRating == 0){
                        ++Zero;
                       // Log.d(tag, "Rating" + Zero);
                    }
                   else if (averageRating == 1) {
                        ++One;
                       // Log.d(tag, "Rating" + One);
                    } else if (averageRating == 2) {
                        ++Two;
                    } else if (averageRating == 3) {
                        ++Three;
                    } else if (averageRating == 4) {
                        ++Four;
                    } else if (averageRating == 5) {
                        ++Five;

                    }

                    barEntries.add(new BarEntry(0, One, "1"));
                    barEntries.add(new BarEntry(1, Two, "2"));
                    barEntries.add(new BarEntry(2, Three, "3"));
                    barEntries.add(new BarEntry(3, Four, "4"));
                    barEntries.add(new BarEntry(4, Five, "5"));



                    BarDataSet bardataset = new BarDataSet(barEntries, "Rating");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("1 Stars");
                    labels.add("2 Stars");
                    labels.add("3 Stars");
                    labels.add("4 Stars");
                    labels.add("5 Stars");

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

                    mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            float x = e.getX();

                            if (x == 0) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                China_Fragment fragment = new China_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                India_Fragment fragment = new India_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Indonesia_Fragment fragment = new Indonesia_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Japan_Fragment fragment = new Japan_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 4) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Malaysia_Fragment fragment = new Malaysia_Fragment();
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
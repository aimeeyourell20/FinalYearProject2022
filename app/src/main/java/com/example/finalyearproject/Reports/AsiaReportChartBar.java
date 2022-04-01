package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Location_Fragments.Argentina_Fragment;
import com.example.finalyearproject.Location_Fragments.Brazil_Fragment;
import com.example.finalyearproject.Location_Fragments.Chile_Fragment;
import com.example.finalyearproject.Location_Fragments.China_Fragment;
import com.example.finalyearproject.Location_Fragments.Columbia_Fragment;
import com.example.finalyearproject.Location_Fragments.India_Fragment;
import com.example.finalyearproject.Location_Fragments.Indonesia_Fragment;
import com.example.finalyearproject.Location_Fragments.Japan_Fragment;
import com.example.finalyearproject.Location_Fragments.Malaysia_Fragment;
import com.example.finalyearproject.Location_Fragments.Peru_Fragment;
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

public class AsiaReportChartBar extends AppCompatActivity {

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
                Intent i = new Intent(AsiaReportChartBar.this, LocationReportChartMentee.class);
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
                            if (x == 5) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Philippines_Fragment fragment = new Philippines_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 6) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Singapore_Fragment fragment = new Singapore_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 7) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Thailand_Fragment fragment = new Thailand_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 8) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Vietnam_Fragment fragment = new Vietnam_Fragment();
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
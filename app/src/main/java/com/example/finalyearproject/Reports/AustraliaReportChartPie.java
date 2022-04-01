package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Location_Fragments.Australia_Fragment;
import com.example.finalyearproject.Location_Fragments.China_Fragment;
import com.example.finalyearproject.Location_Fragments.Fiji_Fragment;
import com.example.finalyearproject.Location_Fragments.India_Fragment;
import com.example.finalyearproject.Location_Fragments.Indonesia_Fragment;
import com.example.finalyearproject.Location_Fragments.Japan_Fragment;
import com.example.finalyearproject.Location_Fragments.Malaysia_Fragment;
import com.example.finalyearproject.Location_Fragments.NewZealand_Fragment;
import com.example.finalyearproject.Location_Fragments.PapauNewGuinea_Fragment;
import com.example.finalyearproject.Location_Fragments.Philippines_Fragment;
import com.example.finalyearproject.Location_Fragments.Singapore_Fragment;
import com.example.finalyearproject.Location_Fragments.Thailand_Fragment;
import com.example.finalyearproject.Location_Fragments.Tonga_Fragment;
import com.example.finalyearproject.Location_Fragments.Vietnam_Fragment;
import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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

public class AustraliaReportChartPie extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    private ImageView mHome;
    DatabaseReference databaseReference;
    int Australia;
    int Fiji;
    int PapuaNewGuinea;
    int NewZealand;
    int Tonga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_australia_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AustraliaReportChartPie.this, LocationReportChartMentee.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Australia":
                            ++Australia;
                            break;
                        case "Fiji":
                            ++Fiji;
                            break;
                        case "Papua New Guinea":
                            ++PapuaNewGuinea;
                            break;
                        case "New Zealand":
                            ++NewZealand;
                            break;
                        case "Tonga":
                            ++Tonga;
                            break;


                    }

                    barEntries.add(new BarEntry(0, Australia, "Australia"));
                    barEntries.add(new BarEntry(1, Fiji, "Fiji"));
                    barEntries.add(new BarEntry(2, PapuaNewGuinea, "Papua New Guinea"));
                    barEntries.add(new BarEntry(3, NewZealand, "New Zealand"));
                    barEntries.add(new BarEntry(4, Tonga, "Tonga"));



                    BarDataSet bardataset = new BarDataSet(barEntries, "Australia/Oceania");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Australia");
                    labels.add("Fiji");
                    labels.add("Papua New Guinea");
                    labels.add("New Zealand");
                    labels.add("Tonga");


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
                                Australia_Fragment fragment = new Australia_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Fiji_Fragment fragment = new Fiji_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                PapauNewGuinea_Fragment fragment = new PapauNewGuinea_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                NewZealand_Fragment fragment = new NewZealand_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 4) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Tonga_Fragment fragment = new Tonga_Fragment();
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
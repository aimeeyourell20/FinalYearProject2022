package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.finalyearproject.Location_Fragments.China_Fragment;
import com.example.finalyearproject.Location_Fragments.India_Fragment;
import com.example.finalyearproject.Location_Fragments.Indonesia_Fragment;
import com.example.finalyearproject.Location_Fragments.Japan_Fragment;
import com.example.finalyearproject.Location_Fragments.Malaysia_Fragment;
import com.example.finalyearproject.Location_Fragments.Philippines_Fragment;
import com.example.finalyearproject.Location_Fragments.Singapore_Fragment;
import com.example.finalyearproject.Location_Fragments.Thailand_Fragment;
import com.example.finalyearproject.Location_Fragments.Vietnam_Fragment;
import com.example.finalyearproject.Mentor.Goals_Activity_Mentor;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GoalsReportChartBarMentor extends AppCompatActivity {

    DatabaseReference databaseReference;
    private HorizontalBarChart mBarChart;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    int OnlyStarted;
    int InProgress;
    int Completed;
    private ImageView mHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_report_chart_bar);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GoalsReportChartBarMentor.this, Mentor_Reports.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Goals").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        //for(DataSnapshot b : a.getChildren()) {
                        switch (a.child("status").getValue(String.class)) { //This statement is seeing what "category" is.
                            case "Only started":
                                ++OnlyStarted;
                                break;
                            case "In Progress":
                                ++InProgress;
                                break;
                            case "Completed":
                                ++Completed;
                                break;
                        }

                        int totalGoals = OnlyStarted + InProgress + Completed;
                        barEntries.add(new BarEntry(0, OnlyStarted, "Only started"));
                        barEntries.add(new BarEntry(1, InProgress, "In Progress"));
                        barEntries.add(new BarEntry(2, Completed, "Completed"));
                        barEntries.add(new BarEntry(3, totalGoals, "Total Goals"));

                        BarDataSet bardataset = new BarDataSet(barEntries, "Goal Progress");

                        ArrayList<String> labels = new ArrayList<String>();

                        labels.add("Only Started");
                        labels.add("In Progress");
                        labels.add("Completed");
                        labels.add("Total Goals");


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
                                    Intent j = new Intent(GoalsReportChartBarMentor.this, Goals_Only_Started_Mentor.class);
                                    startActivity(j);
                                }
                                if (x == 1) {
                                    mBarChart.setVisibility(GONE);
                                    Intent j = new Intent(GoalsReportChartBarMentor.this, Goals_In_Progress_Mentor.class);
                                    startActivity(j);
                                }
                                if (x == 2) {
                                    mBarChart.setVisibility(GONE);
                                    Intent j = new Intent(GoalsReportChartBarMentor.this, Goals_Completed_Mentor.class);
                                    startActivity(j);
                                }
                                if (x == 3) {
                                    mBarChart.setVisibility(GONE);
                                    Intent j = new Intent(GoalsReportChartBarMentor.this, Goals_Activity_Mentor.class);
                                    startActivity(j);
                                }
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
package com.example.finalyearproject.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GoalsReportChartBar extends AppCompatActivity {

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
                Intent i = new Intent(GoalsReportChartBar.this, Mentee_Reports.class);
                startActivity(i);
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Goals").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot a : dataSnapshot.getChildren()){
                        //for(DataSnapshot b : a.getChildren()) {
                        switch (a.child("status").getValue(String.class)) { //This statement is seeing what "category" is.
                            case "Only started":
                                ++OnlyStarted;
                                break;
                            case "In progress":
                                ++InProgress;
                                break;
                            case "Completed":
                                ++Completed;
                                break;
                        }

                        int totalGoals = OnlyStarted + InProgress + Completed;
                        barEntries.add(new BarEntry(0, OnlyStarted, "Only started"));
                        barEntries.add(new BarEntry(1, InProgress, "In progress"));
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



            }
            // }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



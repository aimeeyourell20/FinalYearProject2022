package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class MeetingsReportChartBar extends AppCompatActivity {

    DatabaseReference databaseReference;
    private HorizontalBarChart mBarChart;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    int GoogleMeets;
    int Zoom;
    int Skype;
    int Facetime;

    private static final String tag = "Google Meets";
    private static final String tag1 = "Facetime";
    private static final String tag2 = "Skype";
    private static final String tag3 = "Zoom";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_report_chart_bar);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Meetings").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot a : dataSnapshot.getChildren()){
                        //for(DataSnapshot b : a.getChildren()) {
                            switch (a.child("meetingLocation").getValue(String.class)) { //This statement is seeing what "category" is.
                                case "Google Meets":
                                    ++GoogleMeets;
                                    Log.d(tag, "Google Meets" + GoogleMeets);
                                    break;
                                case "Facetime":
                                    ++Facetime;
                                    Log.d(tag1, "Facetime" + Facetime);
                                    break;
                                case "Skype":
                                    ++Skype;
                                    Log.d(tag2, "Skype" + Skype);
                                    break;
                                case "Zoom":
                                    ++Zoom;
                                    Log.d(tag3, "Zoom" + Zoom);
                                    break;
                            }

                            int totalMeetings = GoogleMeets + Facetime + Skype + Zoom;
                            barEntries.add(new BarEntry(0, GoogleMeets, "Google Meets"));
                            barEntries.add(new BarEntry(1, Facetime, "Facetime"));
                            barEntries.add(new BarEntry(2, Skype, "Skype"));
                            barEntries.add(new BarEntry(3, Zoom, "Zoom"));
                            barEntries.add(new BarEntry(4, totalMeetings, "totalMeetings"));

                            BarDataSet bardataset = new BarDataSet(barEntries, "Meeting Preferences");

                            ArrayList<String> labels = new ArrayList<String>();

                            labels.add("Google Meets");
                            labels.add("Facetime");
                            labels.add("Skype");
                            labels.add("Zoom");
                            labels.add("Total Meetings");


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

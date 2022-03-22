package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MeetingsReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
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
        setContentView(R.layout.activity_meetings_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Meetings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        for (DataSnapshot b : a.getChildren()) {
                            switch (b.child("meetingLocation").getValue(String.class)) { //This statement is seeing what "category" is.
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

                            PieChart pieChart = findViewById(R.id.pieChart);
                            ArrayList<PieEntry> garage = new ArrayList<>();

                            garage.add(new PieEntry(GoogleMeets, "Google Meets"));
                            garage.add(new PieEntry(Facetime, "Facetime"));
                            garage.add(new PieEntry(Skype, "Skype"));
                            garage.add(new PieEntry(Zoom, "Zoom"));
                            garage.add(new PieEntry(totalMeetings, "Total Meetings"));


                            PieDataSet pieDataSet = new PieDataSet(garage, "Meeting Preference");
                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieDataSet.setValueTextColor(Color.BLACK);
                            pieDataSet.setValueTextSize(16f);

                            PieData pieData = new PieData(pieDataSet);

                            pieChart.setData(pieData);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.setCenterText("Meeting Preference");
                            pieChart.animate();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
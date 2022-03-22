package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.finalyearproject.R;
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

public class NorthAmericanReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
    int Canada;
    int Greenland;
    int Mexico;
    int UnitedStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north_american_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
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

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Canada, "Canada"));
                    garage.add(new PieEntry(Greenland, "Greenland"));
                    garage.add(new PieEntry(Mexico, "Mexico"));
                    garage.add(new PieEntry(UnitedStates, "United States"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "North America");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("North America");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}


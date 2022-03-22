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

public class MiddleEastReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
    int Egypt;
    int Israel;
    int Jordan;
    int Morocco;
    int Pakistan;
    int TheUnitedArabEmirates;
    int Tunisia;
    int Turkey;
    int Yemen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_east_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Egypt":
                            ++Egypt;
                            break;
                        case "Israel":
                            ++Israel;
                            break;
                        case "Jordan":
                            ++Jordan;
                            break;
                        case "Morocco":
                            ++Morocco;
                            break;
                        case "Pakistan":
                            ++Pakistan;
                            break;
                        case "The United Arab Emirates":
                            ++TheUnitedArabEmirates;
                            break;
                        case "Tunisia":
                            ++Tunisia;
                            break;
                        case "Turkey":
                            ++Turkey;
                            break;
                        case "Yemen":
                            ++Yemen;
                            break;


                    }

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Egypt, "Egypt"));
                    garage.add(new PieEntry(Israel, "Israel"));
                    garage.add(new PieEntry(Jordan, "Jordan"));
                    garage.add(new PieEntry(Morocco, "Morocco"));
                    garage.add(new PieEntry(Pakistan, "Pakistan"));
                    garage.add(new PieEntry(TheUnitedArabEmirates, "The United Arab Emirates"));
                    garage.add(new PieEntry(Tunisia, "Tunisia"));
                    garage.add(new PieEntry(Turkey, "Turkey"));
                    garage.add(new PieEntry(Yemen, "Yemen"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "Middle East");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Middle East");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}


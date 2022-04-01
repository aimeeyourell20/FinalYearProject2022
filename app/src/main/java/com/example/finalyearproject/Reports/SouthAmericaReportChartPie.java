package com.example.finalyearproject.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class SouthAmericaReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
    private ImageView mHome;
    int Argentina;
    int Brazil;
    int Chile;
    int Columbia;
    int Peru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_america_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SouthAmericaReportChartPie.this, LocationReportChartMentor.class);
                startActivity(i);
                finish();
               

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
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

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Argentina, "Argentina"));
                    garage.add(new PieEntry(Brazil, "Brazil"));
                    garage.add(new PieEntry(Chile, "Chile"));
                    garage.add(new PieEntry(Columbia, "Columbia"));
                    garage.add(new PieEntry(Peru, "Peru"));



                    PieDataSet pieDataSet = new PieDataSet(garage, "South America");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("South America");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
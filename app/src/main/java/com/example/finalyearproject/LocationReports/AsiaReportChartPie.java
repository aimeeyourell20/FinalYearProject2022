package com.example.finalyearproject.LocationReports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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

public class AsiaReportChartPie extends AppCompatActivity {
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
        setContentView(R.layout.activity_asia_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
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

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(China, "China"));
                    garage.add(new PieEntry(India, "India"));
                    garage.add(new PieEntry(Indonesia, "Indonesia"));
                    garage.add(new PieEntry(Japan, "Japan"));
                    garage.add(new PieEntry(Malaysia, "Malaysia"));
                    garage.add(new PieEntry(Philippines, "Philippines"));
                    garage.add(new PieEntry(Singapore, "Singapore"));
                    garage.add(new PieEntry(Thailand, "Thailand"));
                    garage.add(new PieEntry(Vietnam, "Vietnam"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "Asia");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Asia");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
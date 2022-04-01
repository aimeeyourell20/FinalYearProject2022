package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AustraliaReportChartPieMentor extends AppCompatActivity {

    DatabaseReference databaseReference;
    private ImageView mHome;
    int Australia;
    int Fiji;
    int PapauNewGuinea;
    int NewZealand;
    int Tonga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_australia_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AustraliaReportChartPieMentor.this, LocationReportChartMentor.class);
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
                            ++PapauNewGuinea;
                            break;
                        case "New Zealand":
                            ++NewZealand;
                            break;
                        case "Tonga":
                            ++Tonga;
                            break;


                    }

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Australia, "Australia"));
                    garage.add(new PieEntry(Fiji, "Fiji"));
                    garage.add(new PieEntry(PapauNewGuinea, "Papua New Guinea"));
                    garage.add(new PieEntry(NewZealand, "New Zealand"));
                    garage.add(new PieEntry(Tonga, "Tonga"));


                    PieDataSet pieDataSet = new PieDataSet(garage, "Australia/Oceania");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Australia/Oceania");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
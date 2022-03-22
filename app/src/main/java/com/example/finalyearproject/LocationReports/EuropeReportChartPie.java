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

public class EuropeReportChartPie extends AppCompatActivity {

    DatabaseReference databaseReference;
    int Austria;
    int Belgium;
    int Bulgaria;
    int Croatia;
    int Denmark;
    int Finland;
    int France;
    int Germany;
    int Greece;
    int Hungry;
    int Iceland;
    int Ireland;
    int Italy;
    int Lithuania;
    int Netherlands;
    int Norway;
    int Poland;
    int Portugal;
    int Romania;
    int Russia;
    int Spain;
    int Sweden;
    int Switzerland;
    int UnitedKingdom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_europe_report_chart_pie);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("location").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Austria":
                            ++Austria;
                            break;
                        case "Belgium":
                            ++Belgium;
                            break;
                        case "Bulgaria":
                            ++Bulgaria;
                            break;
                        case "Croatia":
                            ++Croatia;
                            break;
                        case "Denmark":
                            ++Denmark;
                            break;
                        case "Finland":
                            ++Finland;
                            break;
                        case "France":
                            ++France;
                            break;
                        case "Germany":
                            ++Germany;
                            break;
                        case "Greece":
                            ++Greece;
                            break;
                        case "Hungry":
                            ++Hungry;
                            break;
                        case "Iceland":
                            ++Iceland;
                            break;
                        case "Ireland":
                            ++Ireland;
                            break;
                        case "Italy":
                            ++Italy;
                            break;
                        case "Lithuania":
                            ++Lithuania;
                            break;
                        case "Netherlands":
                            ++Netherlands;
                            break;
                        case "Norway":
                            ++Norway;
                            break;
                        case "Poland":
                            ++Poland;
                            break;
                        case "Portugal":
                            ++Portugal;
                            break;
                        case "Romania":
                            ++Romania;
                            break;
                        case "Russia":
                            ++Russia;
                            break;
                        case "Spain":
                            ++Spain;
                            break;
                        case "Sweden":
                            ++Sweden;
                            break;
                        case "Switzerland":
                            ++Switzerland;
                            break;
                        case "United Kingdom":
                            ++UnitedKingdom;
                            break;




                    }

                    PieChart pieChart = findViewById(R.id.pieChart);
                    ArrayList<PieEntry> garage = new ArrayList<>();

                    garage.add(new PieEntry(Austria, "Austria"));
                    garage.add(new PieEntry(Belgium, "Belgium"));
                    garage.add(new PieEntry(Bulgaria, "Bulgaria"));
                    garage.add(new PieEntry(Croatia, "Croatia"));
                    garage.add(new PieEntry(Denmark, "Denmark"));
                    garage.add(new PieEntry(Finland, "Finland"));
                    garage.add(new PieEntry(France, "France"));
                    garage.add(new PieEntry(Germany, "Germany"));
                    garage.add(new PieEntry(Greece, "Greece"));
                    garage.add(new PieEntry(Hungry, "Hungry"));
                    garage.add(new PieEntry(Iceland, "Iceland"));
                    garage.add(new PieEntry(Ireland, "Ireland"));
                    garage.add(new PieEntry(Italy, "Italy"));
                    garage.add(new PieEntry(Lithuania, "Lithuania"));
                    garage.add(new PieEntry(Netherlands, "Netherlands"));
                    garage.add(new PieEntry(Norway, "Norway"));
                    garage.add(new PieEntry(Poland, "Poland"));
                    garage.add(new PieEntry(Portugal, "Portugal"));
                    garage.add(new PieEntry(Romania, "Romania"));
                    garage.add(new PieEntry(Russia, "Russia"));
                    garage.add(new PieEntry(Spain, "Spain"));
                    garage.add(new PieEntry(Sweden, "Sweden"));
                    garage.add(new PieEntry(Switzerland, "Switzerland"));
                    garage.add(new PieEntry(UnitedKingdom, "United Kingdom"));



                    PieDataSet pieDataSet = new PieDataSet(garage, "Europe");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Europe");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
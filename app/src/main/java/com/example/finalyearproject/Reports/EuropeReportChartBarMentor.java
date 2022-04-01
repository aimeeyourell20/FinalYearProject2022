package com.example.finalyearproject.Reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EuropeReportChartBarMentor extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private ImageView mHome;
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
        setContentView(R.layout.activity_europe_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EuropeReportChartBarMentor.this, LocationReportChartMentor.class);
                startActivity(i);
                finish();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("location").getValue(String.class)) {
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

                    barEntries.add(new BarEntry(0, Austria, "Austria"));
                    barEntries.add(new BarEntry(1, Belgium, "Belgium"));
                    barEntries.add(new BarEntry(2, Bulgaria, "Bulgaria"));
                    barEntries.add(new BarEntry(3, Croatia, "Croatia"));
                    barEntries.add(new BarEntry(4, Denmark, "Denmark"));
                    barEntries.add(new BarEntry(5, Finland, "Finland"));
                    barEntries.add(new BarEntry(6, France, "France"));
                    barEntries.add(new BarEntry(7, Germany, "Germany"));
                    barEntries.add(new BarEntry(8, Greece, "Greece"));
                    barEntries.add(new BarEntry(9, Hungry, "Hungry"));
                    barEntries.add(new BarEntry(10, Iceland, "Iceland"));
                    barEntries.add(new BarEntry(11, Ireland, "Ireland"));
                    barEntries.add(new BarEntry(12, Italy, "Italy"));
                    barEntries.add(new BarEntry(13, Lithuania, "Lithuania"));
                    barEntries.add(new BarEntry(14, Netherlands, "Netherlands"));
                    barEntries.add(new BarEntry(15, Norway, "Norway"));
                    barEntries.add(new BarEntry(16, Poland, "Poland"));
                    barEntries.add(new BarEntry(17, Portugal, "Portugal"));
                    barEntries.add(new BarEntry(18, Romania, "Romania"));
                    barEntries.add(new BarEntry(19, Russia, "Russia"));
                    barEntries.add(new BarEntry(20, Spain, "Spain"));
                    barEntries.add(new BarEntry(21, Sweden, "Sweden"));
                    barEntries.add(new BarEntry(22, Switzerland, "Switzerland"));
                    barEntries.add(new BarEntry(23, UnitedKingdom, "United Kingdom"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "Europe");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Austria");
                    labels.add("Belgium");
                    labels.add("Bulgaria");
                    labels.add("Croatia");
                    labels.add("Denmark");
                    labels.add("Finland");
                    labels.add("France");
                    labels.add("Germany");
                    labels.add("Greece");
                    labels.add("Hungry");
                    labels.add("Iceland");
                    labels.add("Ireland");
                    labels.add("Italy");
                    labels.add("Lithuania");
                    labels.add("Netherlands");
                    labels.add("Norway");
                    labels.add("Poland");
                    labels.add("Portugal");
                    labels.add("Romania");
                    labels.add("Russia");
                    labels.add("Spain");
                    labels.add("Sweden");
                    labels.add("Switzerland");
                    labels.add("United Kingdom");



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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.example.finalyearproject.Reports;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalyearproject.Location_Fragments.Austria_Fragment;
import com.example.finalyearproject.Location_Fragments.Belgium_Fragment;
import com.example.finalyearproject.Location_Fragments.Bulgaria_Fragment;
import com.example.finalyearproject.Location_Fragments.China_Fragment;
import com.example.finalyearproject.Location_Fragments.Croatia_Fragment;
import com.example.finalyearproject.Location_Fragments.Denmark_Fragment;
import com.example.finalyearproject.Location_Fragments.Finland_Fragment;
import com.example.finalyearproject.Location_Fragments.France_Fragment;
import com.example.finalyearproject.Location_Fragments.Germany_Fragment;
import com.example.finalyearproject.Location_Fragments.Greece_Fragment;
import com.example.finalyearproject.Location_Fragments.Hungry_Fragment;
import com.example.finalyearproject.Location_Fragments.Iceland_Fragment;
import com.example.finalyearproject.Location_Fragments.India_Fragment;
import com.example.finalyearproject.Location_Fragments.Indonesia_Fragment;
import com.example.finalyearproject.Location_Fragments.Ireland_Fragment;
import com.example.finalyearproject.Location_Fragments.Italy_Fragment;
import com.example.finalyearproject.Location_Fragments.Japan_Fragment;
import com.example.finalyearproject.Location_Fragments.Lithuania_Fragment;
import com.example.finalyearproject.Location_Fragments.Malaysia_Fragment;
import com.example.finalyearproject.Location_Fragments.Netherlands_Fragment;
import com.example.finalyearproject.Location_Fragments.Norway_Fragment;
import com.example.finalyearproject.Location_Fragments.Philippines_Fragment;
import com.example.finalyearproject.Location_Fragments.Poland_Fragment;
import com.example.finalyearproject.Location_Fragments.Portugal_Fragment;
import com.example.finalyearproject.Location_Fragments.Romania_Fragment;
import com.example.finalyearproject.Location_Fragments.Russia_Fragment;
import com.example.finalyearproject.Location_Fragments.Singapore_Fragment;
import com.example.finalyearproject.Location_Fragments.Spain_Fragment;
import com.example.finalyearproject.Location_Fragments.Sweden_Fragment;
import com.example.finalyearproject.Location_Fragments.Switzerland_Fragment;
import com.example.finalyearproject.Location_Fragments.Thailand_Fragment;
import com.example.finalyearproject.Location_Fragments.UnitedKingdom_Fragment;
import com.example.finalyearproject.Location_Fragments.Vietnam_Fragment;
import com.example.finalyearproject.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EuropeReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    private ImageView mHome;
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
        setContentView(R.layout.activity_europe_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EuropeReportChartBar.this, LocationReportChartMentee.class);
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
                    mBarChart.setTouchEnabled(true);
                    mBarChart.setPinchZoom(false);
                    mBarChart.setDoubleTapToZoomEnabled(false);
                    theData.setBarWidth(0.5f);
                    mBarChart.setData(theData); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    bardataset.setBarBorderWidth(1);
                    mBarChart.animateY(5000);

                    mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            float x = e.getX();

                            if (x == 0) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Austria_Fragment fragment = new Austria_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 1) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Belgium_Fragment fragment = new Belgium_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 2) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Bulgaria_Fragment fragment = new Bulgaria_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 3) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Croatia_Fragment fragment = new Croatia_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 4) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Denmark_Fragment fragment = new Denmark_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 5) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Finland_Fragment fragment = new Finland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 6) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                France_Fragment fragment = new France_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 7) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Germany_Fragment fragment = new Germany_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 8) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Greece_Fragment fragment = new Greece_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 9) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Hungry_Fragment fragment = new Hungry_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 10) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Iceland_Fragment fragment = new Iceland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 11) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Ireland_Fragment fragment = new Ireland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 12) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Italy_Fragment fragment = new Italy_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 13) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Lithuania_Fragment fragment = new Lithuania_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 14) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Netherlands_Fragment fragment = new Netherlands_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 15) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Norway_Fragment fragment = new Norway_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 16) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Poland_Fragment fragment = new Poland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 17) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Portugal_Fragment fragment = new Portugal_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 18) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Romania_Fragment fragment = new Romania_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 19) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Russia_Fragment fragment = new Russia_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 20) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Spain_Fragment fragment = new Spain_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 21) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Sweden_Fragment fragment = new Sweden_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 22) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                Switzerland_Fragment fragment = new Switzerland_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }
                            if (x == 23) {
                                mBarChart.setVisibility(GONE);
                                FragmentManager fm = getSupportFragmentManager();
                                UnitedKingdom_Fragment fragment = new UnitedKingdom_Fragment();
                                fm.beginTransaction().replace(R.id.container,fragment).commit();
                            }

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
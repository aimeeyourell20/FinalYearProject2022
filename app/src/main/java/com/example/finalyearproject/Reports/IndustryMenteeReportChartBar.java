package com.example.finalyearproject.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class IndustryMenteeReportChartBar extends AppCompatActivity {

    private HorizontalBarChart mBarChart;
    DatabaseReference databaseReference;
    private ImageView mHome;
    int Aerospace;
    int Agriculture;
    int Business;
    int Computer;
    int Construction;
    int Education;
    int Entertainment;
    int Fashion;
    int Food;
    int Healthcare;
    int Hospitality;
    int Media;
    int Telecommunications;
    int STEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_mentee_report_chart_bar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mBarChart = findViewById(R.id.id_horizontal_barchart);
        // PREPARING THE ARRAY LIST OF BAR ENTRIES
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        mHome = findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndustryMenteeReportChartBar.this, Mentor_Reports.class);
                startActivity(i);
                finish();


            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (dataSnapshot.child("industry2").getValue(String.class)) {
                        case "Aerospace":
                            ++Aerospace;
                            break;
                        case "Agriculture":
                            ++Agriculture;
                            break;
                        case "Business":
                            ++Business;
                            break;
                        case "Computer and Technology":
                            ++Computer;
                            break;
                        case "Construction":
                            ++Construction;
                            break;
                        case "Education":
                            ++Education;
                            break;
                        case "Entertainment":
                            ++Entertainment;
                            break;
                        case "Fashion":
                            ++Fashion;
                            break;
                        case "Food":
                            ++Food;
                            break;
                        case "Hospitality":
                            ++Hospitality;
                            break;
                        case "Healthcare":
                            ++Healthcare;
                            break;
                        case "Media":
                            ++Media;
                            break;
                        case "Telecommunications":
                            ++Telecommunications;
                            break;
                        case "STEM":
                            ++STEM;
                            break;

                    }

                    barEntries.add(new BarEntry(0, Aerospace, "Aerospace"));
                    barEntries.add(new BarEntry(1, Agriculture, "Agriculture"));
                    barEntries.add(new BarEntry(2, Business, "Business"));
                    barEntries.add(new BarEntry(3, Computer, "Computer and Technology"));
                    barEntries.add(new BarEntry(4, Construction, "Construction"));
                    barEntries.add(new BarEntry(5, Education, "Education"));
                    barEntries.add(new BarEntry(6, Entertainment, "Entertainment"));
                    barEntries.add(new BarEntry(7, Fashion, "Fashion"));
                    barEntries.add(new BarEntry(8, Food, "Food"));
                    barEntries.add(new BarEntry(9, Healthcare, "Healthcare"));
                    barEntries.add(new BarEntry(10, Hospitality, "Hospitality"));
                    barEntries.add(new BarEntry(11, Media, "Media"));
                    barEntries.add(new BarEntry(12, Telecommunications, "Telecommunications"));
                    barEntries.add(new BarEntry(13, STEM, "STEM"));


                    BarDataSet bardataset = new BarDataSet(barEntries, "Mentees Industry of Interest");

                    ArrayList<String> labels = new ArrayList<String>();

                    labels.add("Aerospace");
                    labels.add("Agriculture");
                    labels.add("Business");
                    labels.add("Computer and Technology");
                    labels.add("Construction");
                    labels.add("Education");
                    labels.add("Entertainment");
                    labels.add("Fashion");
                    labels.add("Food");
                    labels.add("Healthcare");
                    labels.add("Hospitality");
                    labels.add("Media");
                    labels.add("Telecommunications");
                    labels.add("STEM");

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